
#include <iostream>
#include <vector>
#include <string>

template <class T>
class MyLinkedList
{
    public:
        struct node
        {
            T value;
            node * next;
        };

        MyLinkedList() : root(nullptr)
        { }

        MyLinkedList(std::vector<T> elems) : root(nullptr)
        {
            for(auto elem : elems)
            {
                add(elem);
            }
        }

        void add(T elem)
        {
            if (empty())
            {
                root = new node();
                root->value = elem;
                root->next = nullptr;
            } else
            {
                auto iteratingNode = root;
                while (iteratingNode->next != nullptr)
                {
                    iteratingNode = iteratingNode->next;
                }

                auto newNode = new node();
                newNode->value = elem;
                newNode->next = nullptr;
                iteratingNode->next = newNode;
            }
        }

        bool empty()
        {
            return root == nullptr;
        }

    private:
        node * root;
};




int main(int argc, char* argv[])
{
    std::vector<int> elems = {1,2,3,4};
    MyLinkedList<int> myList(elems);

    myList.add(5);

    return 0;
}