package java_exercises.trees;
// https://leetcode.com/problems/find-largest-value-in-each-tree-row/submissions/

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

class TreeNode {
    int val;
    TreeNode left;
    TreeNode right;
    TreeNode() {}
    TreeNode(int val) { this.val = val; }
    
    TreeNode(int val, TreeNode left, TreeNode right) {
        this.val = val;
        this.left = left;
        this.right = right;
    }
}


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
class MaxPerLevel {
       
    public List<Integer> largestValues(TreeNode root) {
        List<Integer> result = new ArrayList<Integer>();
        if (root == null) {
            return result;
        }

        Queue<List<TreeNode>> nodesPerLevel = new LinkedList<List<TreeNode>>();
        ArrayList<TreeNode> rootLevel = new ArrayList<TreeNode>();
        rootLevel.add(root);
        nodesPerLevel.offer(rootLevel);
        while(nodesPerLevel.isEmpty() == false) {
            List<TreeNode> currentLevel = nodesPerLevel.remove();       // get the head
            List<TreeNode> nextLevel = new ArrayList<TreeNode>();
            for (TreeNode currentNode : currentLevel) {
                addChilds(currentNode, nextLevel);
            }

            TreeNodeComparator treeNodeComparator = new TreeNodeComparator();
            TreeNode maxOfLevel = Collections.max(currentLevel, treeNodeComparator);
            result.add(maxOfLevel.val);
            if (nextLevel.size() > 0) {
                nodesPerLevel.offer(nextLevel);
            }
        }
        return result;
    }

    void addChilds(TreeNode currentNode, List<TreeNode> nextLevel) {
        if (currentNode.left != null) {
            nextLevel.add(currentNode.left);
        }
        if (currentNode.right != null) {
            nextLevel.add(currentNode.right);
        }   
    }
}

class TreeNodeComparator implements Comparator<TreeNode> {
    @Override
    public int compare(TreeNode firstTreeNode, TreeNode secondTreeNode) {
        return Integer.compare(firstTreeNode.val, secondTreeNode.val);
    }
}