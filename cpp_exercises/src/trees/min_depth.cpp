
#include <iostream>

// https://leetcode.com/problems/minimum-depth-of-binary-tree/description/

using namespace std;

struct TreeNode {
    int val;
    TreeNode *left;
    TreeNode *right;
    TreeNode() : val(0), left(nullptr), right(nullptr) {}
    TreeNode(int x) : val(x), left(nullptr), right(nullptr) {}
    TreeNode(int x, TreeNode *left, TreeNode *right) : val(x), left(left), right(right) {}
};

class MinDepth {
   const long MAX_DEPTH_LIMIT = 10000;
public:
    int minDepth(TreeNode* root) {
        //std::cout << "root=" << root << std::endl;
        if (root == NULL) {
            return 0;
        } else {
            return rec(root, 0, MAX_DEPTH_LIMIT);       // TODO : revisit
        }
    }

private:
    int rec(TreeNode* node, long currentDepth, long minDepthAccum) {
        if (isLeaf(node)) {
            return (minDepthAccum < currentDepth + 1) ? minDepthAccum : currentDepth + 1;
        } else {
            long depthLeft, depthRight;
            depthLeft = depthRight = MAX_DEPTH_LIMIT;
            if (node->left != NULL) {
                depthLeft = rec(node->left, currentDepth + 1, minDepthAccum);
            }
            if (node->right != NULL) {
                depthRight = rec(node->right, currentDepth + 1, minDepthAccum);
            }
            return min(depthLeft, depthRight);
        }
    }

    bool isLeaf(TreeNode* node) {
        return node->left == NULL && node->right == NULL;
    }
};