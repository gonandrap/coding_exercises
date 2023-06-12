package trees;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import javafx.util.Pair;


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
class PathSum {

    /*
        Comments:
            - have to iterate top down, so not recursion
            - on each level iteration, I could have an int with the current sum up to the path from root to the root of the current sub-tree
                - currentLevel then should be smt like Map<int, List<TreeNode>> where first component is the sum up to the sub-tree
            - if currentSum is bigger than targetSum, dont keep iterating further down that sub-tree(s)
    */

    public List<List<Integer>> pathSum(TreeNode root, int targetSum) {
        List<List<Integer>> result = new ArrayList<List<Integer>>();
        if (root == null) {
            return result;
        }

        List<TreeNode> initialNodes = new ArrayList<TreeNode>();        // nodes of the first level (only the root)
        initialNodes.add(root);

        List<Integer> nodesPerPath = new ArrayList<Integer>();        // nodes of the firt branch (only the root)

        Pair<Pair<Integer, List<Integer>>, List<TreeNode>> rootNodeLevel = new Pair<>(new Pair<>(0, nodesPerPath), initialNodes);
        Queue<Pair<Pair<Integer, List<Integer>>, List<TreeNode>>> nodesPerLevel = new LinkedList<Pair<Pair<Integer, List<Integer>>, List<TreeNode>>>();     // main collection to iterate the tree in BFS
        
        nodesPerLevel.add(rootNodeLevel);                               // ready to start iterating per level

        while (nodesPerLevel.isEmpty() == false) {
            Pair<Pair<Integer, List<Integer>>, List<TreeNode>> currentIteration = nodesPerLevel.remove();
            /*
                - currentIteration.getKey() : info related to the path up to the current iteration. Composed by a pair:
                    - getKey() : sum of all values of nodes part of the path
                    - getValue() : all nodes values of the path, which is accumulated to be returned if applies
                - currentIteration.getValue() : list of nodes in the current level being iterated
            */
            for (TreeNode currentTreeNode : currentIteration.getValue()) {
                Pair<Integer, List<Integer>> currentPath = currentIteration.getKey();
                List<TreeNode> currentNodeChilds = getChilds(currentTreeNode);

                if (currentNodeChilds.isEmpty()) {
                    if (currentPath.getKey() + currentTreeNode.val == targetSum) {
                        // keep path nodes as part of the result, which Im not accumulating!
                        List<Integer> toAdd = new ArrayList<>(currentPath.getValue());      // need to make a copy to not change the one common to all childs
                        toAdd.add(currentTreeNode.val);
                        result.add(toAdd);
                    }
                } else {
                    // is not a leaf, keep iterating down the  tree
                    List<Integer> pathWithCurrentNode = new ArrayList<Integer>(currentPath.getValue());       // has to extend current path with the current node being evaluated
                    pathWithCurrentNode.add(currentTreeNode.val);
                    
                    Pair<Pair<Integer, List<Integer>>, List<TreeNode>> nextLevelNodeChild = new Pair<>(new Pair<>(currentPath.getKey()+currentTreeNode.val, pathWithCurrentNode), currentNodeChilds);
                    nodesPerLevel.add(nextLevelNodeChild);
                }
            }
        }      
        return result;
    }

    List<TreeNode> getChilds(TreeNode currentNode) {
        List<TreeNode> result = new ArrayList<TreeNode>();

        if (currentNode.left != null) {
            result.add(currentNode.left);
        }
        if (currentNode.right != null) {
            result.add(currentNode.right);
        }
        return result;
    }

    public static void main(String[] args) {
        PathSum sol = new PathSum();
        List<List<Integer>> result = sol.pathSum(createTree(), 16);
        System.out.println(result.toString());
    }

    public static TreeNode createTree() {
        TreeNode result = new TreeNode();
        result.val = 5;
        result.left = new TreeNode(4, new TreeNode(7, null, null), new TreeNode(3, null, null));
        result.right = new TreeNode(8, null, null);

        return result;
    }
}