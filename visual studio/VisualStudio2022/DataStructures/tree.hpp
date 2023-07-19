
#ifndef __TREE_HPP__
#define __TREE_HPP__


#include <list>
#include <queue>

template <class T>
class BinaryTree
{
	T* value;
	BinaryTree* left_child;
	BinaryTree* right_child;

public:

	

	BinaryTree(T root_value, BinaryTree* left_child = nullptr, BinaryTree* right_child = nullptr) : value(new T(root_value)), left_child(left_child), right_child(right_child)
	{}

	/*
	* list has the elements to create the tree, in in-order
	*/
	static BinaryTree* createDFS(std::list<T>& elems, const T& NOT_VALUE_ID);

	/*
	* create the tree using the elemsns from the list, in BFS	
	*/
	static BinaryTree* createBFS(std::list<T>& elems);

	bool is_empty(void) const;
	bool has_left_child(void) const;
	bool has_right_child(void) const;

	std::list<T> bfs_iteration(void) const;

private:
	BinaryTree() : value(nullptr), left_child(nullptr), right_child(nullptr)
	{}

	static BinaryTree<T>* process_tree(std::list<T>& elems, const T& NOT_VALUE_ID);
	static BinaryTree<T>* process_level(std::list<T>& elems, BinaryTree<T>* tree, const T& NOT_VALUE_ID);
	static void process_level_bfs(std::list<T> & elems, size_t level_size, std::queue<BinaryTree<T>*> & current_level);
	static void process_right_child(std::list<T>& elems, BinaryTree<T>* parent_tree, const T& NOT_VALUE_ID);
};


template <class T>
BinaryTree<T>* BinaryTree<T>::createDFS(std::list<T>& elems, const T& NOT_VALUE_ID)
{
	BinaryTree<T>* tree = nullptr;

	if (elems.size() > 0 && elems.front() != NOT_VALUE_ID)
	{
		tree = BinaryTree<T>::process_tree(elems, NOT_VALUE_ID);
	}

	return tree;
}

template <class T>
BinaryTree<T>* BinaryTree<T>::createBFS(std::list<T>& elems)
{
	BinaryTree<T>* tree = nullptr;

	if (elems.size() > 0)
	{
		T current_elem = elems.front();
		elems.pop_front();
		tree = new BinaryTree(current_elem);
		std::queue<BinaryTree<T>*> current_level({ tree });

		while (!elems.empty())
		{
			BinaryTree<T>::process_level_bfs(elems, current_level.size(), current_level);
		}
		
	}

	return tree;
}

template <class T>
bool BinaryTree<T>::is_empty(void) const
{
	return this->value == nullptr;
}

template <class T>
bool BinaryTree<T>::has_left_child(void) const
{
	return this->left_child != nullptr;
}

template <class T>
bool BinaryTree<T>::has_right_child(void) const
{
	return this->right_child != nullptr;
}

template <class T>
std::list<T> BinaryTree<T>::bfs_iteration(void) const
{
	std::list<T> result;
	if (this->is_empty())
	{
		return result;
	}

	std::queue<const BinaryTree<T>*> to_iterate;
	to_iterate.push(this);
	const BinaryTree<T>* current_tree = nullptr;

	while (!to_iterate.empty())
	{
		current_tree = to_iterate.front();
		to_iterate.pop();
		result.push_back(*current_tree->value);

		if (current_tree->has_left_child())
		{
			to_iterate.push(current_tree->left_child);
		}
		if (current_tree->has_right_child())
		{
			to_iterate.push(current_tree->right_child);
		}
	}

	return result;
}





// AUXILIAR FUNCTIONS

template <class T>
BinaryTree<T>* BinaryTree<T>::process_tree(std::list<T>& elems, const T& NOT_VALUE_ID)
{
	BinaryTree<T>* root_tree = new BinaryTree<T>(elems.front());
	elems.pop_front();				// remove root elem because I already used it

	BinaryTree<T>::process_level(elems, root_tree, NOT_VALUE_ID);			// if there are childs, will be added to "root_tree"
	return root_tree;
}


template <class T>
BinaryTree<T>* BinaryTree<T>::process_level(std::list<T>& elems, BinaryTree<T>* tree, const T& NOT_VALUE_ID)
{
	if (elems.size() == 0)
	{
		return tree;
	}


	const T & left_elem = elems.front();
	if (left_elem != NOT_VALUE_ID)
	{
		tree->left_child = BinaryTree<T>::createDFS(elems, NOT_VALUE_ID);

		// left child procesed, proceed with right child
		BinaryTree<T>::process_right_child(elems, tree, NOT_VALUE_ID);
	}
	else
	{
		elems.pop_front();					// remove NOT_VALUE_ID corresponding to left node, so we can process right node.
		
		// proceed with right child
		BinaryTree<T>::process_right_child(elems, tree, NOT_VALUE_ID);
	}
	
	return tree;
}

template <class T>
void BinaryTree<T>::process_level_bfs(std::list<T> & elems, size_t level_size, std::queue<BinaryTree<T>*> & tree_level)
{
	/*
	* Will iterate the queue, create trees from it and place it as child of the corresponding parents. Those created
	* trees will be inserted in the queue, to keep the iteration going.
	*/
	BinaryTree<T>* current_tree = nullptr;
	for (int i = 0; i < level_size; ++i)
	{
		current_tree = tree_level.front();
		tree_level.pop();
		if (elems.size() > 0)
		{
			current_tree->left_child = new BinaryTree<T>(elems.front());		// always create left to right in the same level
			elems.pop_front();
			tree_level.push(current_tree->left_child);

			if (elems.size() > 0)
			{
				current_tree->right_child = new BinaryTree<T>(elems.front());
				elems.pop_front();
				tree_level.push(current_tree->right_child);
			}
		}
	}
}


template <class T>
void BinaryTree<T>::process_right_child(std::list<T>& elems, BinaryTree<T>* parent_tree, const T& NOT_VALUE_ID)
{
	if (elems.size() == 0)
	{
		return;
	}

	const T& right_elem = elems.front();
	if (right_elem != NOT_VALUE_ID)
	{
		parent_tree->right_child = BinaryTree<T>::createDFS(elems, NOT_VALUE_ID);
	}
	else
	{
		elems.pop_front();				// remove NOT_VALUE_ID to move to the next node in a superior level
	}
}

#endif // !__TREE_HPP__