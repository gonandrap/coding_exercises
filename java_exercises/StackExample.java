package java_exercises;
import java.util.Stack;

public class StackExample {
    
    public static void main(String[] args) {
        Stack<Integer> stack = new Stack<>();

        stack.push(3);
        stack.push(4);
        System.out.println("peek="+stack.peek());
        stack.pop();
        System.out.println("peek="+stack.peek());
        stack.push(10);
        for (Integer i : stack) {
            System.out.println("stack elemen : " + i);
        }
    }

}
