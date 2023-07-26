#ifndef __QUEUE_BY_STACKS__HPP
#define __QUEUE_BY_STACKS__HPP

#include <stack>

namespace datastructure
{
	template <class T>
	class QueueByStack
	{
		std::stack<T> s1;
		std::stack<T> s2;
	
	public:
		void push(const T& elem);
		T& front(void);
		void pop(void);
		bool empty(void);

	private:
		void move_to_queue(std::stack<T> & a, std::stack<T> & b);

	};

	template <class T>
	void QueueByStack<T>::push(const T& elem)
	{
		this->move_to_queue(this->s1, this->s2);
		this->s1.push(elem);
		this->move_to_queue(this->s2, this->s1);
	}

	template <class T>
	T& QueueByStack<T>::front(void)
	{
		return this->s1.top();
	}

	template <class T>
	void QueueByStack<T>::pop(void)
	{
		this->s1.pop();
	}

	template <class T>
	void QueueByStack<T>::move_to_queue(std::stack<T> & a, std::stack<T> & b)
	{
		while (!a.empty())
		{
			b.push(a.top());
			a.pop(); 
		}
	}

	template <class T>
	bool QueueByStack<T>::empty(void)
	{
		return this->s1.empty();
	}
}



#endif