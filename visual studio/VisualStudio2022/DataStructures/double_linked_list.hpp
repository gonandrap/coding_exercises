#ifndef __DOUBLE_LINKED_LIST_H__
#define __DOUBLE_LINKED_LIST_H__

#include <cassert>

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