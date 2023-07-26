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
#include <list>
#include <iostream>
#include <cassert>



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

int main()
{   
    test_path_normalizer();
}
