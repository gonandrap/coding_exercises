

/*
 * Fixed size list, beyond N, no more elements can be added.
 * Why circular? Allows to rotate elements in place (O(1) memory)
 */

import java.util.ArrayList;
import java.util.List;

public class CircularLinkedList<T> {
    
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
    
    int capacity;
    int size;

    Node<T> root;

    public CircularLinkedList(int capacity) {
        this.capacity = capacity;
        root = null;
        size = 0;
    }

    public void rotate(int offset) {
        int effectiveOffset = offset % capacity;     // doesn't need to rotate entire "laps", just the reminder of the last one

        /*
         *  rotate(2, [1,5,2,4,6]) -> [4,6,1,5,2], root=4 (5-(2%5)=4), tail=2 (2%5)
         *  after rotating:
         *      root = original[size-(capacity - reminder)+1]      // +1 because of the 0 index
         *      tail = original[reminder]
         */

        Node<T> originalTail = findLastElem();
        Node<T> currentIteration = root;
        while(effectiveOffset>0) {
            currentIteration = currentIteration.next;
            effectiveOffset--;
        }

        /*
         * currentIteration becomes the tail
         *      currentIteration.next points to the starting position that was shifter -> the new root!
         * I'm at the place of "interchange"
         */
        originalTail.next = root;
        root = currentIteration.next; 
        currentIteration.next = null;
    }

    public void append(T val) {
        if (size == capacity) {
            System.out.println("At max capacity : " + capacity);
            return;
        }
        
        size++;
        if (root == null) {
            root = new Node<>(val, null);
            return;
        }
        
        Node<T> currentIteration = root;
        while (currentIteration.next != null) {
            currentIteration = currentIteration.next;
        }

        currentIteration.next = new Node<>(val, null);
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
        CircularLinkedList<Integer> circularList = new CircularLinkedList<>(5);
        circularList.append(1);
        circularList.append(5);
        circularList.append(2);
        circularList.append(4);
        circularList.append(6);
        circularList.append(7);
        circularList.print();

        circularList.rotate(2);
        circularList.print();
    }
}
