package java_exercises.trees;


/**
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode() {}
 *     TreeNode(int val) { this.val = val; }
 *     TreeNode(int val, TreeNode left, TreeNode right) {
 *         this.val = val;
 *         this.left = left;
 *         this.right = right;
 *     }
 * }
 */

 // https://leetcode.com/problems/maximum-depth-of-binary-tree/description/


class Solution {
    public int maxDepth(TreeNode root) {
        if (root == null) {
            return 0;
        }
        
        if (root.left == null && root.right == null) {
            return 1;
        } else {
            int maxLeft = 0, maxRight = 0;
            if (root.left != null) {
                maxLeft = maxDepth(root.left);
            }
            if (root.right != null) {
                maxRight = maxDepth(root.right);
            }
            return 1 + Math.max(maxLeft, maxRight);
        }
    }
}