#ifndef __DOUBLE_LINKED_LIST_H__
#define __DOUBLE_LINKED_LIST_H__

#include <cassert>
#include <stack>

template <class T>
class DoubleLinkedList
{
	struct node
	{
		T* value;
		node* before;
		node* next;
	};

	node* root;
	node* last;
	size_t size;

	bool is_empty(void);

public:
	DoubleLinkedList() : root(nullptr), last(nullptr), size(0)
	{}

	DoubleLinkedList(const DoubleLinkedList& instance);

	void add_front(const T& elem);
	T& front_elem(void);
	void remove_front(void);

	void add_back(const T& elem);
	T& back_elem(void);
	void remove_back(void);
	void remove_back(size_t k);				// The caveat is that I'm going to remove assuming it's just a singled-linked list. Optimization: have two iterators i and j. j starts at i+k.

	bool is_palindromic(void);				// Caveat : assume is single-linked

	size_t length(void) const;

};

template <class T>
DoubleLinkedList<T>::DoubleLinkedList(const DoubleLinkedList& instance) : root(nullptr), last(nullptr), size(0)
{
	typename DoubleLinkedList<T>::node* iteration_node = instance.root;
	while (iteration_node != nullptr)
	{
		this->add_back(*iteration_node->value);
		iteration_node = iteration_node->next;
	}
}

template <class T>
void DoubleLinkedList<T>::add_front(const T& elem)
{
	typename DoubleLinkedList<T>::node* new_node = new typename DoubleLinkedList<T>::node(new T(elem), nullptr, nullptr);
	if (this->is_empty())
	{
		this->root = this->last = new_node;
	}
	else
	{
		this->root->before = new_node;
		new_node->next = this->root;
		this->root = new_node;
	}
	this->size++;
}


template <class T>
void DoubleLinkedList<T>::remove_front(void)
{
	assert(this->size > 0);

	typename DoubleLinkedList<T>::node* to_remove = this->root;

	if (this->size == 1)
	{
		this->root = this->last = nullptr;
	}
	else
	{
		to_remove->next->before = nullptr;
		this->root = to_remove->next;
	}
	
	delete(to_remove);
	this->size--;
}

template <class T>
void DoubleLinkedList<T>::add_back(const T& elem)
{
	typename DoubleLinkedList<T>::node* new_node = new typename DoubleLinkedList<T>::node(new T(elem), nullptr, nullptr);
	if (this->is_empty())
	{
		this->root = this->last = new_node;
	}
	else
	{
		this->last->next = new_node;
		new_node->before = this->last;
		this->last = new_node;
	}
	this->size++;
}

template <class T>
void DoubleLinkedList<T>::remove_back(void)
{
	typename DoubleLinkedList<T>::node* to_remove = this->last;
	
	if (this->size == 1)
	{
		this->root = this->last = nullptr;
	}
	else
	{
		to_remove->before->next = nullptr;
		this->last = to_remove->before;
	}

	delete(to_remove);
	this->size--;
}

template <class T>
void DoubleLinkedList<T>::remove_back(size_t k)
{
	if (k == 0) return;

	assert(this->root != nullptr);

	// calculate the size	
	typename DoubleLinkedList<T>::node* current_node = this->root;
	size_t size = 1;
	while (current_node->next != nullptr)
	{
		current_node = current_node->next;
		++size;
	}

	// find the the previous node to the new last one
	int nodes_to_advance = size - k;

	current_node = this->root;
	while (nodes_to_advance > 1)
	{
		current_node = current_node->next;
		--nodes_to_advance;
	}
	
	// list[size - k - 1] becomes the new last element;
	this->last = current_node;
	
	// move to the first element to be deleted
	current_node = current_node->next;

	// delete the remaining nodes (size - (size - k) -> k)
	typename DoubleLinkedList<T>::node* tmp;
	while (current_node != nullptr)
	{
		tmp = current_node->next;
		delete(current_node);
		current_node = tmp;
	}

	// finally, update the last element
	this->last->next = nullptr;
}

template <class T>
bool DoubleLinkedList<T>::is_palindromic(void)
{
	// calculate the size	
	typename DoubleLinkedList<T>::node* current_node = this->root;
	size_t size = 1;
	while (current_node->next != nullptr)
	{
		current_node = current_node->next;
		++size;
	}

	if (size % 2 == 0) return false;

	int mid_list = size / 2 + 1;

	std::stack<T> stack;
	current_node = this->root;
	while (mid_list > 0)
	{
		stack.push(*current_node->value);
		current_node = current_node->next;
		--mid_list;
	}

	// remove element in the middle
	stack.pop();

	// compare second half of elements using the accumulated stack
	while (current_node != nullptr && *current_node->value == stack.top())
	{
		current_node = current_node->next;
		stack.pop();
	}

	return current_node == nullptr;			// if I reached the end, then we have a palyndrome
}


template <class T>
size_t DoubleLinkedList<T>::length(void) const
{
	return this->size;
}

template <class T>
T& DoubleLinkedList<T>::front_elem(void)
{
	return *this->root->value;
}

template <class T>
T& DoubleLinkedList<T>::back_elem(void)
{
	return *this->last->value;
}


template <class T>
bool DoubleLinkedList<T>::is_empty(void)
{
	return this->size == 0;
}


#endif		//__DOUBLE_LINKED_LIST_H__