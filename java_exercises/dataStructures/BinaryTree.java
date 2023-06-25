import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

/*
 * The big issue when trying to create the tree recursive accepting a node-list in DFS mode is that, from the list, I don't never
 * really know if I have a right sibling for the current level, because the i+1 info goes deep in the left sub-tree. 
 * That is why I have to introduce the concept of "intention".
 * Intention, as the name goes, refers to the intention to process certain child for a given node. It may end up not processing that
 * child (because there was a -1 in that part of the array), but at least we will have the contextual information to know how to proceed,
 * thanks to the intentions. 
 */

public class BinaryTree<T> {
    
    class Intention {
        static public final int LEFT = 0;
        static public final int ROOT = 1;
        static public final int RIGHT = 2;

        int type;
        public TreeNode<T> parent;

        public Intention(int type, TreeNode<T> parent) {
            this.type = type;
            this.parent = parent;
        }
    }
    
    class TreeNode<TT> {
        TT val;
        TreeNode<TT> left;
        TreeNode<TT> right;

        public TreeNode(TT val) {
            this.val = val;
            left = null;
            right = null;
        }


        public boolean isLeaf() {
            return left == null && right == null;
        }
    }

    TreeNode<T> root;
    int numberNodes;

    final Integer NOT_CHILD = -1;

    public BinaryTree() {
        root = null;
    }

    /*
     * Coding challenge : create the tree from a list in pre-order, not using recursion.
     */
    public BinaryTree(List<T> vals) {
        Stack<Intention> nextIntentions = new Stack<Intention>();
        int i = 0;
        TreeNode<T> currentParentNode = null;

        nextIntentions.push(new Intention(Intention.ROOT, root));
        while (i < vals.size() - 1) {                                   // ignore the last one because for sure has to have a negative value
            Intention currentIntention = nextIntentions.pop();          // always remove the head of the queue since we are processing it in this iteration
            currentParentNode = currentIntention.parent;
            
            if (currentIntention.type == Intention.ROOT) {
                assert(i==0);                                       // only use the ROOT case in the very first iteration, otherwise there is an error
                processRoot(i, vals, currentParentNode, nextIntentions);
            } else if (currentIntention.type == Intention.LEFT) {
                processLeft(i, vals, currentParentNode, nextIntentions);
            } else if (currentIntention.type == Intention.RIGHT) {
                processRight(i, vals, currentParentNode, nextIntentions);
            } else {
                throw new UnsupportedOperationException(String.format("Not support action [%s]", currentIntention.toString()));
            }
            
            i++;
        }
    }


    private void processRoot(int i, List<T> vals, TreeNode<T> currentParentNode, Stack<Intention> nextIntentions) {
        this.root = new TreeNode<T>(vals.get(i));
        numberNodes++;
        // no need to add root to trailNodes since it was added before starting the while iteration
        nextIntentions.push(new Intention(Intention.RIGHT, this.root));
        nextIntentions.push(new Intention(Intention.LEFT, this.root));
    }

    private void processLeft(int i, List<T> vals, TreeNode<T> currentParentNode, Stack<Intention> nextIntentions) {
        if (vals.get(i) == NOT_CHILD) {
            return;
        } else {
            currentParentNode.left = new TreeNode<T>(vals.get(i));
            numberNodes++;
            nextIntentions.push(new Intention(Intention.RIGHT, currentParentNode.left));
            nextIntentions.push(new Intention(Intention.LEFT, currentParentNode.left));
        }
    }

    private void processRight(int i, List<T> vals, TreeNode<T> currentParentNode, Stack<Intention> nextIntentions) {
        if (vals.get(i) == NOT_CHILD) {
            return;
        } else {
            currentParentNode.right = new TreeNode<T>(vals.get(i));
            numberNodes++;
            nextIntentions.push(new Intention(Intention.RIGHT, currentParentNode.right));
            nextIntentions.push(new Intention(Intention.LEFT, currentParentNode.right));
        }
    }


    public void addLeft(T val) {
        assert(root != null);
        root.left = new TreeNode<T>(val);
    }

    public void addRight(T val) {
        assert(root != null);
        root.right = new TreeNode<T>(val);
    }

    public boolean isEmpty() {
        return root == null;
    }

    public String toString() {
        return iterateNodes(new StringBuilder(3*numberNodes), root).append("[]").toString();
    }

    private StringBuilder iterateNodes(StringBuilder builder, TreeNode<T> node) {
        if (node == null) {
            return builder.append(String.format("-1,"));
        } 

        builder.append(String.format("%s,",node.val));
        iterateNodes(builder, node.left);
        iterateNodes(builder, node.right);
        return builder;
    }

    public static void main(String[] args) {
        BinaryTree<Integer> tree = new BinaryTree<Integer>(List.of(1,2,3,-1,-1,4,-1,5,-1,-1,6,7,-1,-1,8,9,-1,-1,10,-1,-1));
        System.out.println(String.format("BinaryTree -> %s", tree.toString()));
    }
}