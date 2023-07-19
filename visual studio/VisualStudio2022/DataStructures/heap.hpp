#ifndef __HEAP_HPP__
#define __HEAP_HPP__

#include <list>
#include <queue>
#include <cassert>

template <class T>
class Heap
{
	struct heap_node
	{
		T* value;
		heap_node* parent;								// all childs from the same bucket belong to the same parent
		heap_node* left_node;
		heap_node* right_node;
	};

	size_t childs_per_bucket;
	heap_node* root_node;
	heap_node* last_parent_node;								// keep track of the last bucket where childs were added
	heap_node* most_left_leaf;							// PENDING this will become the parent of new nodes for a new level of the Heap

	Heap(T root_value, size_t childs_per_bucket = 2) : childs_per_bucket(childs_per_bucket), root_node(new heap_node{ new T(root_value), nullptr, nullptr, nullptr}), last_parent_node(root_node)
	{}

	heap_node* add_heap_node(heap_node** where_to_add, heap_node* parent, T& value);
	void add_new_level(T& value);
	void heapify(void);

public:
	static Heap* create(std::list<T>& elems);
	void add_node(T& value);
};


template <class T>
void Heap<T>::add_node(T& value)
{
	// check the Heap is well-formed
	assert(this->root_node != nullptr && this->last_parent_node != nullptr);

	if (this->last_parent_node->left_node == nullptr)
	{
		this->add_heap_node(&this->last_parent_node->left_node, this->last_parent_node, value);
	}
	else if (this->last_parent_node->right_node == nullptr)
	{
		this->add_heap_node(&this->last_parent_node->right_node, this->last_parent_node, value);
	}
	else if (this->last_parent_node == this->root_node)					// QUESTION : can include last else clause in here too?
	{
		// only for the root node, once both childs are cover, we need to create a new level
		this->add_new_level(value);
	}
	else if (this->last_parent_node->parent->right_node == nullptr)		// don't need to check parent->left_node because for sure has a child, and that must be left_node
	{
		// this insertion will complete the current level
		this->add_heap_node(&this->last_parent_node->parent->right_node, this->last_parent_node->parent->right_node, value);
		
		// since we move to the parent node in the right, we have to update "last_parent_node"
		this->last_parent_node = this->last_parent_node->parent->right_node;
	}
	else
	{
		this->add_new_level(value);
	}

	// once the new node was inserted at the proper position at the end of the heap, let's heapify!
	this->heapify();
}

template <class T>
void Heap<T>::add_new_level(T& value)
{
	// "most_left_leaf" becomes the "last_parent_node" and parent of the node being created
	this->last_parent_node = this->most_left_leaf;

	// The new node will become the "most_left_leaf"
	this->most_left_leaf = this->add_heap_node(&this->most_left_leaf, this->last_parent_node, value);
}

template <class T>
typename Heap<T>::heap_node* Heap<T>::add_heap_node(typename Heap<T>::heap_node** where_to_add, typename Heap<T>::heap_node* parent, T& value)
{
	*where_to_add = new typename Heap<T>::heap_node(new T(value), parent, nullptr, nullptr);
	return *where_to_add;
}

template <class T>
void Heap<T>::heapify(void)
{
	// TODO
}

template <class T>
Heap<T>* Heap<T>::create(std::list<T>& elems)
{
	Heap<T>* heap = nullptr;

	if (elems.size() > 0)
	{
		T current_elem = elems.front();
		elems.pop_front();
		heap = new Heap(current_elem);

		for(auto elem : elems)
		{
			heap->add_node(elem);
		}
	}

	return heap;
}




#endif	//__HEAP_HPP__