// DataStructures.cpp : This file contains the 'main' function. Program execution begins and ends there.
//

#include <iostream>
#include "tree.hpp"
#include <list>
#include <iostream>

using namespace std;

int main()
{
    std::list<int> elems({1, 2, 3, -1, 4, -1, -1, 5, 6, -1, -1, -1, 7});
    Tree<int>* tree = Tree<int>::create(elems, -1);
    std::cout << "Hello World from my new project for my name solution!\n";

    std::list<int> bfs = tree->bfs_iteration();
    copy(bfs.cbegin(), bfs.cend(), ostream_iterator<int>(cout, " "));
}
