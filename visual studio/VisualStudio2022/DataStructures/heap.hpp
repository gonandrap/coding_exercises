#ifndef __HEAP_HPP__
#define __HEAP_HPP__

#include <list>
#include <queue>
#include <cassert>
#include <functional>

template <class T>
class Heap
{
	struct heap_node
	{
		T* value;
		heap_node* parent;								// all childs from the same bucket belong to the same parent
		heap_node* next_sibling;						// needed to move to the next node in the father when current one has both childs filled up
		heap_node* left_node;
		heap_node* right_node;
	};

	size_t childs_per_bucket;
	heap_node* root_node;
	heap_node* last_parent_node;								// keep track of the last bucket where childs were added
	heap_node* most_left_leaf;									// nedeed because this becomes the parent of new nodes for a new level of the Heap
	std::function<bool(T*, T*)> comparator;

	Heap(T root_value, std::function<bool(T*,T*)> comparator, size_t childs_per_bucket = 2) : 
		childs_per_bucket(childs_per_bucket), 
		root_node(new heap_node{new T(root_value), nullptr, nullptr, nullptr, nullptr}),
		last_parent_node(root_node),
		most_left_leaf(root_node),
		comparator(comparator)
	{}

	heap_node* add_heap_node(heap_node** where_to_add, heap_node* parent, T& value);
	heap_node* add_new_level(T& value);
	void heapify(heap_node* last_inserted_node);

public:
	static Heap* create(std::list<T>& elems, std::function<bool(T*, T*)> comparator);
	void add_node(T& value);
};


template <class T>
void Heap<T>::add_node(T& value)
{
	// check the Heap is well-formed
	assert(this->root_node != nullptr);
	assert(this->last_parent_node != nullptr);
	assert(this->most_left_leaf != nullptr);

	typename Heap<T>::heap_node* last_inserted_elem;

	if (this->last_parent_node->left_node == nullptr)
	{
		// since we have a new most left leaf, update that value too
		last_inserted_elem = this->most_left_leaf = this->add_heap_node(&this->last_parent_node->left_node, this->last_parent_node, value);
	}
	else if (this->last_parent_node->right_node == nullptr)
	{
		last_inserted_elem = this->last_parent_node->left_node->next_sibling = this->add_heap_node(&this->last_parent_node->right_node, this->last_parent_node, value);
	}
	else if (this->last_parent_node == this->root_node)					// QUESTION : can include last else clause in here too?
	{
		// since there are no more siblings in the level, make that explicit
		this->last_parent_node->right_node->next_sibling = nullptr;
		
		// only for the root node, once both childs are cover, we need to create a new level
		last_inserted_elem = this->add_new_level(value);
	}
	else if (this->last_parent_node->parent->right_node == nullptr)		// don't need to check parent->left_node because for sure has a child, and that must be left_node
	{
		// this insertion will complete the current level
		last_inserted_elem = this->add_heap_node(&this->last_parent_node->parent->right_node, this->last_parent_node->parent->right_node, value);
		
		// since we move to the parent node in the right, we have to update "last_parent_node"
		this->last_parent_node = this->last_parent_node->parent->right_node;
	}
	else if (this->last_parent_node->next_sibling != nullptr)
	{
		// since are adding a node to the same level, need to update "next_sibling" of the current latest node of the level
		last_inserted_elem = this->last_parent_node->right_node->next_sibling = this->add_heap_node(&this->last_parent_node->next_sibling->left_node, this->last_parent_node->next_sibling, value);
		
		// move to the next parent node (sibling of current one) within the same level
		this->last_parent_node = this->last_parent_node->next_sibling;
	}
	else
	{
		// since there are no more siblings in the level, make that explicit
		this->last_parent_node->right_node->next_sibling = nullptr;

		// if all before is false (including the fact that there are no more siblings), then add a new level
		last_inserted_elem = this->add_new_level(value);
	}

	// once the new node was inserted at the proper position at the end of the heap, let's heapify!
	this->heapify(last_inserted_elem);
}

template <class T>
typename Heap<T>::heap_node* Heap<T>::add_new_level(T& value)
{
	// "most_left_leaf" becomes the "last_parent_node" and parent of the node being created
	this->last_parent_node = this->most_left_leaf;

	// The new node will become the "most_left_leaf"
	this->most_left_leaf = this->add_heap_node(&this->most_left_leaf, this->last_parent_node, value);

	this->last_parent_node->left_node = this->most_left_leaf;

	return this->most_left_leaf;
}

template <class T>
typename Heap<T>::heap_node* Heap<T>::add_heap_node(typename Heap<T>::heap_node** where_to_add, typename Heap<T>::heap_node* parent, T& value)
{
	*where_to_add = new typename Heap<T>::heap_node(new T(value), parent, nullptr, nullptr, nullptr);
	return *where_to_add;
}

template <class T>
void Heap<T>::heapify(typename Heap<T>::heap_node* last_inserted_node)
{
	typename Heap<T>::heap_node* iteration_node = last_inserted_node;
	while (this->comparator(iteration_node->value, iteration_node->parent->value))
	{
		T* tmp = iteration_node->parent->value;
		iteration_node->parent->value = iteration_node->value;
		iteration_node->value = tmp;

		iteration_node = iteration_node->parent;
	}
}

template <class T>
Heap<T>* Heap<T>::create(std::list<T>& elems, std::function<bool(T*, T*)> comparator)
{
	Heap<T>* heap = nullptr;

	if (elems.size() > 0)
	{
		T current_elem = elems.front();
		elems.pop_front();
		heap = new Heap(current_elem, comparator);

		for(auto elem : elems)
		{
			heap->add_node(elem);
		}
	}

	return heap;
}




#endif	//__HEAP_HPP__