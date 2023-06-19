
#include <iostream>

using namespace std;

template <class T>
class MyBinaryTree
{
    struct node
    {
        T elem;
        node* left;
        node* right;
    };

    node * root;

    private:
        void insertNode(node* & base, T value)
        {
            base = new node();
            base->elem = value;
            base->left = nullptr;
            base->right = nullptr;
        }

    public:
        MyBinaryTree() : root(nullptr)
        {
            cout << "creating tree" << endl;
        }

        node* rootNode()
        {
            return root;
        }

        bool hasLeft(node * n)
        {
            return (n != nullptr) && (n->left != nullptr);
        }

        bool hasRight(node * n)
        {
            return n != nullptr && n->right != nullptr;
        }

        node* insertRoot(T value)
        {
            if (root == nullptr)
            {
                insertNode(root, value);
            } else
            {
                root->elem = value;
                return root;
            }
        }

        /*
            Semantic : if there is a left  child already under "base", then update its node's value.
            If there is no node, create one and make "base" point to it
        */
        node* insertLeft(node* base, T value)
        {
            if (base != nullptr)
            {
                if (hasLeft(base))
                {
                    base->left->elem = value;
                } else
                {
                    insertNode(base->left, value);
                }
            }
            cout << "inserted [" << value << "] as left child of [" << base->elem << "]" << endl;
            return base->left;
        }

        node* insertRight(node* base, T value)
        {
            if (hasRight(base))
            {
                base->right->elem = value;
            } else
            {
                insertNode(base->right, value);
            }
            cout << "inserted [" << value << "] as right child of [" << base->elem << "]" << endl;
            return base->right;
        }
};






int main(int argc, char* argv[])
{
    MyBinaryTree<int> myBTree;
    auto root = myBTree.insertRoot(1);
    auto left = myBTree.insertLeft(root, 2);
    myBTree.insertLeft(left, 3);
    myBTree.insertRight(left, 4);
    auto right = myBTree.insertRight(root, 5);
    myBTree.insertLeft(right, 6);
    return 0;
}