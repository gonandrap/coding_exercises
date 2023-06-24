

/*
 * Not sure if I understand the definition of the problem. Let's assume that for the push/pop method you have to specify which stack you want to insert the info
 * Idea
 *      my first idea was to have three sets of indexes (start and end), one for each stack. But then I realized their can be inferred from the size of each stack:
 *          stack_1 : [0,stack_1_size)
 *          stack_2 : [stack_1_size, array.size()-stack_3_size)
 *          stack_3 : [array.size()-stack_3_size, array.size())
 */

import java.util.ArrayList;
import java.util.List;

public class ThreeStacksArray<T> {
    
    List<T> elems;
    int sizeStack1, sizeStack3;

    public ThreeStacksArray() {
        elems = new ArrayList<T>();
        sizeStack1 = sizeStack3 = 0;
    }

    public void push(int stackNumber, T val) {
        if (stackNumber == 1) {
            elems.add(0, val);
            sizeStack1++;
        } else if (stackNumber == 2) {
            elems.add(sizeStack1, val);
        } else if (stackNumber == 3) {
            elems.add(val);
            sizeStack3++;
        } else {
            System.out.println("stack must be 1,2 or 3");
        }
    }

    public T pop(int stackNumber) {
        T result; 
        if (stackNumber == 1) {
            result = elems.get(0); 
            elems.remove(0);
            sizeStack1--;
            return result;
        } else if (stackNumber == 2) {
            result = elems.get(sizeStack1);
            elems.remove(sizeStack1);
            return result;
        } else if (stackNumber == 3) {
            result = elems.get(elems.size()-1);
            elems.remove(elems.size()-1);
            sizeStack3--;
            return result;
        } else {
            throw new NullPointerException("stack must be 1,2 or 3");
        }
    }

    public void printStacks() {
        print("Stack 1", 0, sizeStack1);
        print("Stack 2", sizeStack1, elems.size() - sizeStack3);
        printStack3("Stack 3");
    }

    private void print(String msg, int fromIndex, int toIndex) {
        StringBuilder builder = new StringBuilder(toIndex-fromIndex);


        for (int i = fromIndex; i<toIndex; i++) {
            builder.append(elems.get(i));
        }

        System.out.println(String.format("%s -> %s",msg, builder.toString()));
    }

    private void printStack3(String msg) {
        StringBuilder builder = new StringBuilder(sizeStack3);

        for (int i = 0; i<sizeStack3; i++) {
            builder.append(elems.get(elems.size()-i-1));
        }
        System.out.println(String.format("%s -> %s",msg, builder.toString()));
    }

    static public void main(String[] args) {
        ThreeStacksArray<Integer> threeStacks = new ThreeStacksArray<Integer>();

        threeStacks.push(1, 1);
        threeStacks.push(1, 2);
        threeStacks.push(2, 1);
        threeStacks.push(2, 6);
        threeStacks.push(2, 9);
        threeStacks.push(3, 2);
        threeStacks.push(3, 6);
        threeStacks.printStacks();
        System.out.println("");

        threeStacks.pop(1);
        threeStacks.pop(2);
        threeStacks.printStacks();
        System.out.println("");

        threeStacks.pop(3);
        threeStacks.printStacks();
    }
}
