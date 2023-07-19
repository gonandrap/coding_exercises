
#ifndef __COMPLETE_TREE_HPP__
#define __COMPLETE_TREE_HPP__




#include "tree.hpp"

template <class T>
class CompleteBinaryTree
{
	BinaryTree<T>* tree;

	CompleteBinaryTree() : tree(nullptr)
	{}

	CompleteBinaryTree(BinaryTree<T>* tree) : tree(tree)
	{}

public:
	static CompleteBinaryTree<T>* create(std::list<T>& elems);
	std::list<T> nodes(void);
};

template <class T>
CompleteBinaryTree<T>* CompleteBinaryTree<T>::create(std::list<T>& elems)
{
	return new CompleteBinaryTree(BinaryTree<T>::createBFS(elems));
}

template <class T>
std::list<T> CompleteBinaryTree<T>::nodes(void)
{
	return this->tree->bfs_iteration();
}


#endif // __COMPLETE_TREE_HPP__