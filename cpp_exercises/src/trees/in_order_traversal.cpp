
#include <iostream>
#include <vector>

// https://leetcode.com/problems/binary-tree-inorder-traversal/description/

using namespace std;

struct TreeNode {
    int val;
    TreeNode *left;
    TreeNode *right;
    TreeNode() : val(0), left(nullptr), right(nullptr) {}
    TreeNode(int x) : val(x), left(nullptr), right(nullptr) {}
    TreeNode(int x, TreeNode *left, TreeNode *right) : val(x), left(left), right(right) {}
};

class InOrderTraversal {
public:
    vector<int> inorderTraversal(TreeNode* root) {
        vector<int> result;
        if (root != NULL) {
            rec(root, result);
        }
        return result;
    }
private:
    void rec(TreeNode* node, vector<int> & result) {
        if (isLeaf(node)) {
            result.push_back(node->val);
        } else {
            if (node->left != NULL) {               // insert nodes from left sub-tree
                rec(node->left, result);
            }                
            result.push_back(node->val);            // insert root of current sub-tree

            if (node->right != NULL) {              // insert nodes from right sub-tree
                rec(node->right, result);
            }
        }
    }

    bool isLeaf(TreeNode* node) {
        return node->left == NULL && node->right == NULL;
    }
};


int main(int argc, char* argv[]) {

}