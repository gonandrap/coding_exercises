// DataStructures.cpp : This file contains the 'main' function. Program execution begins and ends there.
//

#include <iostream>
#include "tree.hpp"
#include "complete_tree.hpp"
#include "heap.hpp"
#include "double_linked_list.hpp"
#include "stack.hpp"
#include "multithreading.h"
#include "bitwise.h"
#include "algorithms.h"
#include "queue_by_stacks.hpp"
#include "big_file.h"
#include "geometry.h"
#include <list>
#include <iostream>
#include <cassert>

#include <unordered_map>
#include <set>
#include <math.h>
#include <vector>



using namespace std;

bool less_than(int* a, int* b)
{
    return *a < *b;
}

void test_stack(void)
{
    Stack<int>* stack = new Stack<int>();
    stack->push(1);
    stack->push(5);
    stack->push(2);
    assert(stack->top() == 2);
    stack->pop();
    stack->pop();
    assert(stack->top() == 1);
    stack->pop();
    assert(stack->size() == 0);
}

void test_double_link_list(void)
{
    DoubleLinkedList<int>* l = new DoubleLinkedList<int>();
    l->add_front(1);
    l->add_front(6);
    l->add_front(7);
    l->add_front(0);
    l->add_front(8);

    l->remove_front();
    l->remove_back();
    l->remove_back();

    DoubleLinkedList<int>* l2 = new DoubleLinkedList<int>(*l);

    std::cout << "finished" << std::endl;
}

void test_heap()
{
    std::list<int> elems{1, 2, 3, 9, 4, 2, 5, 5, 6, 7, 4};
    Heap<int>* heap = Heap<int>::create(elems, less_than);
    std::cout << "finished" << std::endl;
}

void test_tree()
{
    std::list<int> elems{1, 2, 3, -1, 4, -1, -1, 5, 6, -1, -1, -1, 7};
    //BinaryTree<int>* tree = BinaryTree<int>::createBFS(elems, -1);
    CompleteBinaryTree<int>* tree = CompleteBinaryTree<int>::create(elems);
    std::cout << "Hello World from my new project for my name solution!\n";

    std::list<int> bfs = tree->nodes();
    copy(bfs.cbegin(), bfs.cend(), ostream_iterator<int>(cout, " "));
}


void test_bitwise_mask()
{
    std::cout << bitwise::solution(1073741727, 1073741631, 1073741679);
}

void test_remove_k_from_list()
{
    DoubleLinkedList<int>* l = new DoubleLinkedList<int>();
    l->add_front(1);
    l->add_front(6);
    l->add_front(7);
    l->add_front(0);
    l->add_front(8);

    l->remove_back(2);
}

void test_palindromic_list()
{
    DoubleLinkedList<int>* l = new DoubleLinkedList<int>();
    l->add_back(1);
    l->add_back(6);
    l->add_back(7);
    l->add_back(0);
    l->add_back(7);
    l->add_back(6);
    l->add_back(1);
    bool result = l->is_palindromic();

    std::cout << result << endl;
}

void test_path_normalizer()
{
    std::string s = "/s/g/.///../../hola/test/prueba";
    std::string path = algorithms::path_normalizer(s);
    std::cout << path << std::endl;

    s = "./s/g/.///../../hola/test/prueba";
    path = algorithms::path_normalizer(s);
    std::cout << path << std::endl;
}

void test_queue_by_stack()
{
    datastructure::QueueByStack<int> queue;
    queue.push(1);
    queue.push(2);
    queue.push(3);
    queue.push(4);
    queue.push(5);
    queue.push(6);

    int elem;
    while (!queue.empty())
    {
        std::cout << queue.front() << std::endl;
        queue.pop();
    } 
}

void test_exterior_nodes()
{
    std::list<int> elems{1, 2, 3, 9, -1, -1, 4, -1, -1, 5, 6, -1, -1, -1, 7, -1, 6, 9, -1, -1, 5, -1, -1};
    BinaryTree<int>* tree = BinaryTree<int>::createDFS(elems, -1);
    std::list<int> results = tree->exterior_nodes();
    std::copy(results.begin(), results.end(), std::ostream_iterator<int>(std::cout, " "));
}

void test_big_file()
{
    datastructure::BigFile bigFile("example_file.txt", 10);
    bigFile.load_in_memory(10, 20);
    std::cout << bigFile.in_memory() << std::endl;

    bigFile.load_in_memory(15, 20);
    std::cout << bigFile.in_memory() << std::endl;
}

void test_sort_big_file()
{
    //algorithms::sort_big_file("many_numbers.txt");
    std::string chunk = "19048509438059";
    std::sort(chunk.begin(), chunk.end());
    std::cout << chunk << std::endl;
}

int solution(int inner, int outer, vector<double>& points_x, vector<double>& points_y) {
    /*
    * hypotenuse = sqrt(x^2+y^2)
    * if inner < hypotenuse < outer -> count it!
    */
    int count = 0;
    for (size_t i = 0; i < points_x.size(); ++i)
    {
        double x = points_x[i];
        double y = points_y[i];
        double hypotenuse = sqrt(pow(x, 2) + pow(y, 2));
        //std::cout << inner << " " << hypotenuse << " " << outer << "->" << ((inner < hypotenuse) && (hypotenuse < outer)) << std::endl;
        if ((inner < hypotenuse) && (hypotenuse < outer))
        {
            //std::cout << x << " " << y << std::endl;
            count += 1;
        }
    }
    return count;
}

std::pair<std::vector<double>, std::vector<double>> gen_points(size_t how_many, int inner_circle, int outer_circle)
{
    std::vector<double> points_x, points_y;
    while (how_many > 0)
    {
        double  ang = (double) rand() / RAND_MAX * 2 * M_PI;
        double hyp = sqrt((double) rand() / RAND_MAX) * outer_circle;
        double adj = cos(ang) * hyp;
        double opp = sin(ang) * hyp;

        points_x.push_back(adj);
        points_y.push_back(opp);

        how_many -= 1;
    }
    return std::make_pair(points_x, points_y);
}



bool number_found_already(int number, const std::set<int>& numbers_found)
{
    return numbers_found.find(number) != numbers_found.end();
}

bool not_adyacent_neighbor(const vector< vector<int> >& A, const int& i, const int& j, const int& N, const int& M, vector<vector<int>> A_new, int sequence)
{
    // returns true when A[i,j] is different than every possible neighbor

    /* implementation notes
    *   1. the multiple ifs could be written in one single condition. Even though would be more efficient, would be way less clear
    *   2. don't need to check rest of the neighbors if one is equal already
    */

    bool result = true;
    if (i > 0)
    {
        result = result && A[i - 1][j] != A[i][j];
        if (A[i - 1][j] == A[i][j])
            A_new[i][j] = A[i - 1][j];
    }
        

    if (result && i < N - 1)
    {
        result = result && A[i + 1][j] != A[i][j];
        if (A[i + 1][j] == A[i][j])
            A_new[i][j] = A[i + 1][j];
    }
        

    if (result && j > 0)
    {
        result = result && A[i][j - 1] != A[i][j];
        if (A[i][j - 1] == A[i][j])
            A_new[i][j] = A[i][j - 1];
    }
        

    if (result && j < M - 1)
    {
        result = result && A[i][j + 1] != A[i][j];
        if (A[i][j + 1] == A[i][j])
            A_new[i][j] = A[i][j + 1];
    }
        

    return result;
}

int solution(vector< vector<int> >& A) {
    /*
    * count a number for current A[i,j] if:
    *   * A[i,j] hasn't been considered yet or
    *   * neighbors (north, south, west, east) don't have it
    */

    std::set<int> numbers_found;
    int number_countries = 0;
    int N = A.size();
    int M = A.front().size();
    vector<vector<int>> A_new(N, vector<int>(M, -1));
    int sequence = 0;
    for (int i = 0; i < N; ++i)
    {
        for (int j = 0; j < M; ++j)
        {
            if (not_adyacent_neighbor(A, i, j, N, M, A_new, sequence))
            {
                sequence += 1;
                A_new[i][j] = sequence;
                number_countries += 1;
            }
            else
            {
                
            }
        }
    }

    number_countries = 0;
    // iterate again an just count different numbers
    for (int i = 0; i < N; ++i)
    {
        for (int j = 0; j < M; ++j)
        {
            std::cout << i << " " << j << " " << A_new[i][j] << "->" << !number_found_already(A_new[i][j], numbers_found) << "           ";
            if (!number_found_already(A_new[i][j], numbers_found))
            {
                numbers_found.emplace(A_new[i][j]);
                number_countries += 1;
            }
        }
        std::cout << endl;
    }
    return number_countries;
}

void test_country_count()
{
    vector< vector<int> > matrix = { {5, 4, 4},{4, 3, 4},{3, 2, 4},{2, 2, 2},{3, 3, 4},{1, 4, 4},{4, 1, 1} };       // 11 countries
    int result = algorithms::country_count(matrix);
    std::cout << "expected [11], got [" << result << "]" << std::endl;
}

int main()
{   
    std::string S = "ACCAABBC";
    std::cout << algorithms::reduce_string(S) << std::endl;

    std::string s;


    vector<int> v(10);

}
