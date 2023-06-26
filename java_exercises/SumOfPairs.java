import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SumOfPairs {
    public List<Integer[]> sumOfPairs(Integer[] numbers, int k) {
        /*
         * naive solution : for each index i, scan every j (j>i) looking for numbers[i] + numbers[j] == k
         *      O(n~2)
         * optimization : sorting the pairs? may prevent iterating the whole remaining thing for each i (although is not guaranteed)
         *      I can use a hash structure: for each i, look in the hash for the elem (k-i).
         */

        Set<Integer> hashedElems = new HashSet<Integer>(Arrays.asList(numbers));

        List<Integer[]> result = new ArrayList<Integer[]>();
        for (int i=0; i<numbers.length; i++) {
            hashedElems.add(numbers[i]);
        }

        for (int i=0; i<numbers.length; i++) {
            if (hashedElems.contains((k-numbers[i]))) {
                Integer[] pair = {numbers[i], k-numbers[i]};
                result.add(pair);
                hashedElems.remove(numbers[i]);
            }
        }

        return result;
    }

    public static void main(String[] args) {
        SumOfPairs sumOrPairs = new SumOfPairs();
        Integer[] elems = {3,6,1,8,4,9,13,54};
        int k = 9;
        List<Integer[]> result = sumOrPairs.sumOfPairs(elems, k);
        System.out.println(String.format("Pairs who sum [%d] are :", k));
        for(var pair : result) {
            System.out.println(String.format("\t%s", Arrays.asList(pair)));
        }
    }
}
