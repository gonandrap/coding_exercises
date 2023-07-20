#ifndef __STACK_H__
#define __STACK_H__

#include "double_linked_list.hpp"

template <class T>
class Stack
{
	DoubleLinkedList<T>* internal_list;

public:
	Stack() : internal_list(new DoubleLinkedList<T>())
	{}

	void push(const T& elem);
	T& top(void);
	void pop(void);
	size_t size(void);
};

template <class T>
void Stack<T>::push(const T& elem)
{
	this->internal_list->add_front(elem);
}

template <class T>
T& Stack<T>::top(void)
{
	return this->internal_list->front_elem();
}

template <class T>
void Stack<T>::pop(void)
{
	this->internal_list->remove_front();
}

template <class T>
size_t Stack<T>::size(void)
{
	return this->internal_list->length();
}

#endif		# __STACK_H__