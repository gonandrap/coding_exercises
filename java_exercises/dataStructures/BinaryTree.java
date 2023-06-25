import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
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
    
    enum Edge {
        LEFT, ROOT, RIGHT;
    }

    enum Intention {
        LEFT, ROOT, RIGHT;
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

    Integer LEAF_INDICATOR = -2;
    Integer NOT_CHILD = -1;
    Integer LEFT = -1;
    Integer RIGHT = 1;
    Integer ROOT = 0;

    public BinaryTree() {
        root = null;
    }

    public BinaryTree(List<T> vals) {
        Queue<Edge> trailEdges = new LinkedList<Edge>();
        Stack<TreeNode<T>> trailNodes = new Stack<TreeNode<T>>();
        Stack<Intention> nextIntention = new Stack<Intention>();
        int i = 0;
        TreeNode<T> currentNode = null;

        nextIntention.push(Intention.ROOT);
        trailNodes.push(root);
        while (i < vals.size() - 1) {                               // ignore the last one because for sure has to have a negative value
            Intention currentIntention = nextIntention.pop();       // always remove the head of the queue since we are processing it in this iteration
            currentNode = trailNodes.peek();                        // not sure if I have to remove it yet
            
            if (currentIntention == Intention.ROOT) {
                assert(i==0);                                       // only use the ROOT case in the very first iteration, otherwise there is an error
                processRoot(i, vals, trailNodes, trailEdges, nextIntention);
                //nextRootIntentions(i, vals, nextIntention);
            } else if (currentIntention == Intention.LEFT) {
                processLeft(i, vals, currentNode, trailNodes, trailEdges, nextIntention);
                //nextLeftIntentions(i, vals, currentNode, trailNodes, trailEdges, nextIntention);
            } else if (currentIntention == Intention.RIGHT) {
                processRight(i, vals, currentNode, trailNodes, trailEdges, nextIntention);
                //nextRightIntentions(i, vals, currentNode, trailNodes, trailEdges, nextIntention);
            } else {
                throw new UnsupportedOperationException(String.format("Not support action [%s]", currentIntention.toString()));
            }
            
            i++;
        }
    }

    private void nextRootIntentions(int i, List<T> vals, Queue<Intention> nextIntentions) {
        if (i == vals.size() - 1) {
            // last elem before completing whole iteration, not need to do anything
        } else if (vals.get(i+1) == NOT_CHILD) {        // means that there is no left child but there is a right one (if there is no right child, it should have been be LEAF_INDICATOR)
            nextIntentions.offer(Intention.RIGHT);
        } else if (vals.get(i+1) == LEAF_INDICATOR) {
            return;
        } else {
            nextIntentions.offer(Intention.LEFT);
            nextIntentions.offer(Intention.RIGHT);

        }
    }

    private void nextLeftIntentions(int i, List<T> vals, TreeNode<T> currentNode, Queue<TreeNode<T>> trailNodes, Queue<Edge> trailEdges, Queue<Intention> nextIntentions) {
        if (i == vals.size() - 1) {
            // last elem before completing whole iteration, not need to do anything
        } else if (vals.get(i+1) == NOT_CHILD) {        // means that there is no left child but there is a right one (if there is no right child, it should have been be LEAF_INDICATOR)
            trailEdges.poll();
            nextIntentions.offer(Intention.RIGHT);
            i++;            // skip NOT_CHILD position
        } else if (vals.get(i+1) == LEAF_INDICATOR) {
            trailEdges.poll();      // remove current node and go to the sibling righ child
            trailNodes.poll();
            i++;        // skip LEAF_INDICATOR position
            // TODO : has to figure out what is the next intention
            if (vals.get(i+1) == NOT_CHILD) {
                // no right child
                trailEdges.poll();      // current level is gone, go up
                trailNodes.poll();
                i++;            // skip NOT_CHILD position
            } else {
                // there is right child
                nextIntentions.offer(Intention.RIGHT);
                i++;
            }
        } else {
            nextIntentions.offer(Intention.LEFT);
            i++;
        }
    }

    private void nextRightIntentions(int i, List<T> vals, TreeNode<T> currentNode, Queue<TreeNode<T>> trailNodes, Queue<Edge> trailEdges, Queue<Intention> nextActions) {
        if (i == vals.size() - 1) {
            // last elem before completing whole iteration, not need to do anything
        } else if (vals.get(i+1) == NOT_CHILD) {        // means that there is no left child but there is a right one (if there is no right child, it should have been be LEAF_INDICATOR)
            trailEdges.poll();
            nextActions.offer(Intention.RIGHT);
        } else if (vals.get(i+1) == LEAF_INDICATOR) {
            trailEdges.poll();      // one level up
            trailNodes.poll();
            // has to instruct a superior level on what to do next. For that, I would use the trail edge from the upper level
            Intention lastQueuedAction = nextActions.poll();
            if (lastQueuedAction == Intention.LEFT) {
                // we have to signal that the inmediate superior left tree is done and we have to process his sibling
                trailEdges.poll();
                trailNodes.poll();
                nextActions.offer(Intention.RIGHT);
            }
        } else {
            nextActions.offer(Intention.LEFT);
        }
    }
   

    private boolean processRoot(int i, List<T> vals, Stack<TreeNode<T>> trailNodes, Queue<Edge> trailEdges, Stack<Intention> nextIntentions) {
        trailNodes.pop();      // remove the initial root that is null
        this.root = new TreeNode<T>(vals.get(i));
        numberNodes++;
        // no need to add root to trailNodes since it was added before starting the while iteration
        trailNodes.push(this.root);
        trailEdges.offer(Edge.ROOT);
        nextIntentions.push(Intention.RIGHT);
        nextIntentions.push(Intention.LEFT);
        return true;
    }

    private boolean processLeft(int i, List<T> vals, TreeNode<T> currentNode, Stack<TreeNode<T>> trailNodes, Queue<Edge> trailEdges, Stack<Intention> nextIntentions) {
        if (vals.get(i) == NOT_CHILD || vals.get(i) == LEAF_INDICATOR) {
            return false;
        } else {
            currentNode.left = new TreeNode<T>(vals.get(i));
            numberNodes++;
            trailNodes.push(currentNode.left);
            trailEdges.offer(Edge.LEFT);            // mark to say that I processed the left child
            nextIntentions.push(Intention.RIGHT);
            nextIntentions.push(Intention.LEFT);
            return true;
        }
    }

    private boolean processRight(int i, List<T> vals, TreeNode<T> currentNode, Stack<TreeNode<T>> trailNodes, Queue<Edge> trailEdges, Stack<Intention> nextIntentions) {
        if (vals.get(i) == NOT_CHILD || vals.get(i) == LEAF_INDICATOR) {
            trailEdges.poll();      // done with intentions from current node, go back one level
            trailNodes.pop();
            return false;
        } else {
            currentNode.right = new TreeNode<T>(vals.get(i));
            numberNodes++;
            trailNodes.push(currentNode.right);
            trailEdges.offer(Edge.RIGHT);            // mark to say that I processed the right child
            nextIntentions.push(Intention.RIGHT);
            nextIntentions.push(Intention.LEFT);
            return true;
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
        //BinaryTree<Integer> tree = new BinaryTree<Integer>(List.of(1,2,3,-1,-1,4,-1,5,-1,-1,6,7,-1,-1,8,9,-1,-1,10,-1,-1));
        BinaryTree<Integer> tree = new BinaryTree<Integer>(List.of(1,2,-1,4,-1,5,-1,-1,6,7,-1,-1,8,9,-1,-1,10,-1,-1));
        System.out.println(String.format("BinaryTree -> %s", tree.toString()));
    }
}
