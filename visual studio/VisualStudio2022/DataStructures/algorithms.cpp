
#include "algorithms.h"
#include <queue>
#include <cassert>
#include <sstream>

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
}