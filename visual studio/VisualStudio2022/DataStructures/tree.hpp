
#include <list>
#include <queue>

template <class T>
class Tree
{
	T* value;
	Tree* left_child;
	Tree* right_child;

public:

	

	Tree(T root_value, Tree* left_child = nullptr, Tree* right_child = nullptr) : value(new T(root_value)), left_child(left_child), right_child(right_child)
	{}

	/*
	* list has the elements to create the tree, in in-order
	*/
	static Tree* create(std::list<T>& elems, const T& NOT_VALUE_ID);

	bool is_empty(void) const;
	bool has_left_child(void) const;
	bool has_right_child(void) const;

	std::list<T> bfs_iteration(void) const;

private:
	Tree() : value(nullptr), left_child(nullptr), right_child(nullptr)
	{}

	static Tree<T>* process_tree(std::list<T>& elems, const T& NOT_VALUE_ID);
	static Tree* process_level(std::list<T>& elems, Tree* tree, const T& NOT_VALUE_ID);
	static void process_right_child(std::list<T>& elems, Tree<T>* parent_tree, const T& NOT_VALUE_ID);
};


template <class T>
Tree<T>* Tree<T>::create(std::list<T>& elems, const T& NOT_VALUE_ID)
{
	Tree<T>* tree = nullptr;

	if (elems.size() > 0 && elems.front() != NOT_VALUE_ID)
	{
		tree = Tree<T>::process_tree(elems, NOT_VALUE_ID);
	}

	return tree;
}

template <class T>
bool Tree<T>::is_empty(void) const
{
	return this->value == nullptr;
}

template <class T>
bool Tree<T>::has_left_child(void) const
{
	return this->left_child != nullptr;
}

template <class T>
bool Tree<T>::has_right_child(void) const
{
	return this->right_child != nullptr;
}

template <class T>
std::list<T> Tree<T>::bfs_iteration(void) const
{
	std::list<T> result;
	if (this->is_empty())
	{
		return result;
	}

	std::queue<const Tree<T>*> to_iterate;
	to_iterate.push(this);
	const Tree<T>* current_tree = nullptr;

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
Tree<T>* Tree<T>::process_tree(std::list<T>& elems, const T& NOT_VALUE_ID)
{
	Tree<T>* root_tree = new Tree<T>(elems.front());
	elems.pop_front();				// remove root elem because I already used it

	Tree<T>::process_level(elems, root_tree, NOT_VALUE_ID);			// if there are childs, will be added to "root_tree"
	return root_tree;
}

template <class T>
Tree<T>* Tree<T>::process_level(std::list<T>& elems, Tree<T>* tree, const T& NOT_VALUE_ID)
{
	if (elems.size() == 0)
	{
		return tree;
	}


	const T & left_elem = elems.front();
	if (left_elem != NOT_VALUE_ID)
	{
		tree->left_child = Tree<T>::create(elems, NOT_VALUE_ID);	

		// left child procesed, proceed with right child
		Tree<T>::process_right_child(elems, tree, NOT_VALUE_ID);
	}
	else
	{
		elems.pop_front();					// remove NOT_VALUE_ID corresponding to left node, so we can process right node.
		
		// proceed with right child
		Tree<T>::process_right_child(elems, tree, NOT_VALUE_ID);
	}
	
	return tree;
}

template <class T>
void Tree<T>::process_right_child(std::list<T>& elems, Tree<T>* parent_tree, const T& NOT_VALUE_ID)
{
	if (elems.size() == 0)
	{
		return;
	}

	const T& right_elem = elems.front();
	if (right_elem != NOT_VALUE_ID)
	{
		parent_tree->right_child = Tree<T>::create(elems, NOT_VALUE_ID);
	}
	else
	{
		elems.pop_front();				// remove NOT_VALUE_ID to move to the next node in a superior level
	}
}