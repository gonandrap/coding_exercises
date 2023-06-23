
/*
 * The goal is to implement from the scratch
 */

import java.util.ArrayList;
import java.util.List;

public class MyStack<T> {
    
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

    Node<T> root;

    public MyStack() {
        root = null;
    }

    public void push(T val) {
        root = new Node<T>(val, root);
    }

    public T front() {
        if (root != null) {
            return root.getVal();
        }
        throw new NullPointerException("cant call front on an empty stack");
    }

    public void pop() {
        if (root != null) {
            root = root.next;
        }
        // nothing to do if root is null
    }

    public void print() {
        if (root == null) {
            System.out.println("empty stack");
        }

        List<T> toPrint = new ArrayList<T>();
        Node<T> iterationNode = root;       // not empty
        while (iterationNode != null) {
            toPrint.add(iterationNode.val);
            iterationNode = iterationNode.next;
        }
        System.out.println(String.format("Elems of stack : %s", toPrint.toString()));
    }

    static public void main(String[] args) {
        MyStack<Integer> stack = new MyStack<Integer>();
        stack.push(1);
        stack.push(2);
        stack.push(3);
        stack.push(4);
        stack.print();
    }
}
