// DataStructures.cpp : This file contains the 'main' function. Program execution begins and ends there.
//

#include <iostream>
#include "tree.hpp"
#include "complete_tree.hpp"
#include "heap.hpp"
#include "double_linked_list.hpp"
#include <list>
#include <iostream>

using namespace std;

bool less_than(int* a, int* b)
{
    return *a < *b;
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

int main()
{
    test_double_link_list();
}
