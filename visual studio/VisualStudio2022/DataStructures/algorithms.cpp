
#include "algorithms.h"
#include "big_file.h"
#include <queue>
#include <cassert>
#include <sstream>
#include <algorithm>
#include <iostream>

#define NOT_INIT  -1
#define MARKED 1
#define NOT_MARKED 0

void process_token(const std::string & token, std::queue<std::string> & entire_stack)
{
	if (token == "..")
	{
		entire_stack.pop();
	}
	else if (token == "" || token == "." || token.empty())
	{
		// nothing to do
	}
	else
	{
		entire_stack.push(token);
	}
}

namespace algorithms
{
	std::string path_normalizer(const std::string& path)
	{
		std::string token;
		std::queue<std::string> entire_path;
		size_t i = 0;
		size_t j = path.find("/");
		if (j > 0)
		{
			token = path.substr(0, j - 1);
			process_token(token, entire_path);			// process the starting token
		}
		
		i = j + 1;
		j = path.find("/", i);

		while (j != std::string::npos)
		{
			token = path.substr(i, j - i);				// use "i+1" to avoid referir to the "/"
			process_token(token, entire_path);
			
			i = j + 1;
			j = path.find("/", i);
		}

		token = path.substr(i, j);
		process_token(token, entire_path);			// process the finishing token

		std::ostringstream result;

		if (path.at(0) == '/')
		{
			result << "/";
		}

		while (!entire_path.empty())
		{
			result << entire_path.front();
			entire_path.pop();

			if (!entire_path.empty())
			{
				result << "/";
			}
		}

		return result.str();
	}

	void sort_big_file(const std::string& path)
	{
		size_t chunk_size = 30;
		datastructure::BigFile big_file(path, chunk_size);
		size_t f_size = big_file.file_size();

		size_t number_chunks = f_size / chunk_size + 1;
		for (size_t i = 0; i < number_chunks; ++i)
		{
			// load a chunk into memory
			big_file.load_in_memory(i * number_chunks, chunk_size);
			
			// sort the chunk
			std::string chunk(big_file.in_memory());
			std::sort(chunk.begin(), chunk.end());

			// persist the chunk
			big_file.set_memory(chunk);
			big_file.save_to_disk();
		}
	}

	bool _can_move(const int& i, const int& j, std::vector<std::vector<int>>& A, std::vector<std::vector<int>>& graph, const int& N, const int& M, const int& current_i, const int& current_j)
	{
		return  (i >= 0 && j >= 0 && i < N && j < M &&              // first, check limits
			A[current_i][current_j] == A[i][j] &&
			graph[i][j] == NOT_INIT);                           // don't move to an already visited node
	}

	void _paint_neighboors(std::vector<std::vector<int>>& A, std::vector<std::vector<int>>& graph, const int& counter, const int& i, const int& j, const int& N, const int& M)
	{
		if (_can_move(i - 1, j, A, graph, N, M, i, j))
		{
			graph[i - 1][j] = counter;
			_paint_neighboors(A, graph, counter, i - 1, j, N, M);
		}
		else if (_can_move(i + 1, j, A, graph, N, M, i, j))
		{
			graph[i + 1][j] = counter;
			_paint_neighboors(A, graph, counter, i + 1, j, N, M);
		}
		else if (_can_move(i, j - 1, A, graph, N, M, i, j))
		{
			graph[i][j - 1] = counter;
			_paint_neighboors(A, graph, counter, i, j - 1, N, M);
		}
		else if (_can_move(i, j + 1, A, graph, N, M, i, j))
		{
			graph[i][j + 1] = counter;
			_paint_neighboors(A, graph, counter, i, j + 1, N, M);
		}
		else
		{
			// no not painted neighboors to color, ending this branch of the recursion
		}
	}

	int country_count(std::vector< std::vector<int> >& A)
	{
		int N = A.size();
		int M = A.front().size();        // assuming at least one row 

		std::vector<std::vector<int>> graph(N, std::vector<int>(M, NOT_INIT));
		int counter = 0;

		for (int i = 0; i < N; ++i)
		{
			for (int j = 0; j < M; ++j)
			{
				if (graph[i][j] == NOT_INIT)       // not yet visited?
				{
					std::cout << "painting counter [" << counter + 1 << "] -> [i,j]=" << "[" << i << "," << j << "]" << std::endl;
					graph[i][j] = counter;  // assign country
					_paint_neighboors(A, graph, counter, i, j, N, M);      // color all possible neightbors for the new country
					++counter;              // found a new "not yet conected" node, meaning, a new country
				}
			}
		}
		return counter;
	}

	int highest_power_2(int N)
	{
		int i = 0;
		while ((N & 1) == 0 && i < 64)
		{
			++i;
			N = N >> 1;
		}
		return i;
	}

	bool iterate(std::string& S, std::vector<int>& marks)
	{
		bool found_transformation = false;

		std::string::iterator it_i, it_j;
		it_i = it_j = S.begin();

		size_t i, j;
		// i : pivot
		// j : advances to the next not yet marked char
		for (i = 0; i < S.length();)
		{
			// ignore the reductions we already did
			while (marks[i] == MARKED)
			{
				++i;
				++it_i;
			}
			// found the pivot, now we have to find a matching transformation
			j = i + 1;
			it_j = it_i + 1;          // is this correct? will it change it_i? it shouldn't

			while (j < S.length() && marks[j] == MARKED)
			{
				++j;
				++it_j;
			}

			if (it_i < S.end() && it_j < S.end() && *it_i == *it_j)
			{
				marks[i] = marks[j] = MARKED;
				i = j + 1;
				it_i = it_j + 1;
				found_transformation = true;
			}
			else
			{
				// since the "contiguous" elems are not equal, advance the pivot to the next not marked elem
				i = j;
				it_i = it_j;
			}
		}
		return found_transformation;
	}

	std::string reduce_string(std::string& S)
	{
		std::vector<int> marks(S.size(), NOT_MARKED);

		if (S.length() < 2)
		{
			return S;
		}

		while (iterate(S, marks))
		{ }

		std::string::iterator it = S.begin();
		std::ostringstream result;
		for (size_t i = 0; i < marks.size(); ++i)
		{
			if (marks[i] == NOT_MARKED)
			{
				result << *it;
			}
			++it;
		}

		return result.str();
	}
}