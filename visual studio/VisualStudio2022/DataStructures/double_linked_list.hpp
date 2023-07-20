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

	DoubleLinkedList(const  DoubleLinkedList& instance) : size(instance.size)
	{
		//deep copy
	}

	void add_front(const T& elem);
	void remove_front(void);
	
	void add_back(const T& elem);
	void remove_back(void);

};

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

	to_remove->next->before = nullptr;
	this->root = to_remove->next;

	delete(to_remove);
	this->size--;
}

template <class T>
void DoubleLinkedList<T>::add_back(const T& elem)
{

}

template <class T>
void DoubleLinkedList<T>::remove_back(void)
{

}


template <class T>
bool DoubleLinkedList<T>::is_empty(void)
{
	return this->size == 0;
}


#endif		//__DOUBLE_LINKED_LIST_H__