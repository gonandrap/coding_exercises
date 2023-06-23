import java.util.ArrayList;
import java.util.List;

public class MyLinkedList<T> {
    
    public class Node<TT> {
        T val;
        Node<TT> next;

        public Node(T val, Node<TT> next) {
            this.val = val;
            this.next = next;
        }

        public T getVal() {
            return val;
        }
    }

    private Node<T> root;

    public void LinkedList() {
        root = null;
    }

    public void add(T val) {
        
        if (root == null) {
            root = new Node<T>(val, null);
            return;
        }
        
        Node<T> iterationNode = root;
        while (iterationNode.next != null) {
            iterationNode = iterationNode.next;
        }

        iterationNode.next = new Node<T>(val, null);
    }

    public void reverseInPlace() {

        /*
         * pre  :  8 -> 1 -> 3 -> 6 -> 4
         * it 1 :  originalLast=4, previousIteration=8, currentIteration=1     1 -> 3 -> 6 -> 4 -> 8 -> null
         * it 2 :  originalLast=4, previousIteration=1, currentIteration=3     3 -> 6 -> 4 -> 1 -> 8 -> null
         * it 3 :  originalLast=4, previousIteration=3, currentIteration=6     6 -> 4 -> 3 -> 1 -> 8 -> null
         * it 4 :  originalLast=4, previousIteration=6, currentIteration=4     4 -> 6 -> 3 -> 1 -> 8 -> null
         * post :  4 -> 6 -> 3 -> 1 -> 8
         * 
         * 
         * ideas : 
         *  1. only one pass and interchanging pointers
         *      - how to propagate 8 to last position? same for the 4 to the front
         *  2. do one pass, position pointer to the end, and pass one more time, in both directions
         *      - how the "pointerToLast" knows its predecessor?
         *  3. can be done in O(n)?
         *  4. simple solution : for ith elem, go to the size-jth visited, interchange, repeat
         *      - how do I know which is the jth elem to the end?
         *  5. all I know in each iteration is : current elem and next one.
         */

        

        // base case
        if (root == null || root.next == null) {
            return;
        }

        // there are two elems at least
        Node<T> originalLast = findLastElem();
        Node<T> previousIteration = root;
        Node<T> currentIteration = root.next;

        while (currentIteration != originalLast) {
            previousIteration.next = originalLast.next;
            originalLast.next = previousIteration;
            previousIteration = currentIteration;
            currentIteration = currentIteration.next;
        }

        previousIteration.next = originalLast.next;
        originalLast.next = previousIteration;

        root.next = null;       // former head now tail has to point to null
        root = originalLast;    // new root is the former tail
    }

    private Node<T> findLastElem() {
        if (root == null) {
            return new Node(0, null);
        } 

        Node<T> iterationNode = root;
        while (iterationNode.next != null) {
            iterationNode = iterationNode.next;
        }

        return iterationNode;
    }

    public void print() {
        List<T> toPrint = new ArrayList<T>();
        Node<T> iterationNode = root;
        while (iterationNode != null) {
            toPrint.add(iterationNode.val);
            iterationNode = iterationNode.next;
        }
        System.out.println(String.format("Elements of the list : %s", toPrint.toString()));
    }

    static public void main(String[] args) {
        MyLinkedList<Integer> list = new MyLinkedList<Integer>();       
        list.add(8);
        list.add(1);
        list.add(3);
        list.add(6);
        list.add(4);
        list.print();
        
        list.reverseInPlace();
        list.print();
    }
}
