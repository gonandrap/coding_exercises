package java;
import java.util.HashMap;

// https://leetcode.com/problems/climbing-stairs/

class Solution {
    private HashMap<Integer, Integer> partial_results;

    public Solution() {
        partial_results = new HashMap<>();
    }

    public int climbStairs(int n) {
        /*
            Comments:
                - can i use divide and conquer? meaning, manually resolve the case for small n (3 for example) and then multiple by n/3.
                    - I don't see why not, to make a "local decision" (n=3) I don't need global decision (n)
                    - the only thing is the limit. On each "cut" that is not the end, I have to add an extra 1, which relates to the
                      option of climbing the last of the previous segment with the first one of the next segment.
                - another approach : can I think of it as a complete binary tree? for each new level I have two childs : climb 2 steps or 1 step.
                    - the result is to count leafs
                    - correction : is not a complete binary tree, because the branch that climbs 2 steps is like is moving 2 tree levels ahead, 
                      compared with the one climbing just one.
                    - in each iteration, i have two options:
                        - if remaining steps >= 2:
                            I end up with 3 leafs : 
                                1. climb 2 and get to tree level (step) i+2
                                2. climb 1 and get to tree level i+1
                                    2.1 from here, climb 1 to get to i+2
                                    2.2 from here, climb 2 to get to i+3 (if possible)

                - CONCLUSION : I believe it is better to just do recursion, with i index in the ladder and n the remaining steps. 
                    There would be as many recursions calls as potential leafs, which is not that bad because n<4=5 and doesn't add
                    two childs per step 
                - REALIZATION : is this fibonacci?? very similar! (and very slow implementing that way!)
        */
            if (n <= 3) {
                return n;
            } else {
                int result = 0;
                Integer key_1 = Integer.valueOf(n-1);
                Integer key_2 = Integer.valueOf(n-2);

                if (partial_results.containsKey(key_1)) {
                    result += partial_results.get(key_1);
                } else {
                    int partial_result = climbStairs(n-1);
                    partial_results.put(key_1, Integer.valueOf(partial_result));
                    result += partial_result;
                }

                if (partial_results.containsKey(key_2)) {
                    result += partial_results.get(key_2);
                } else {
                    int partial_result = climbStairs(n-2);
                    partial_results.put(key_2, Integer.valueOf(partial_result));
                    result += partial_result;
                }
                return result;
            }
    }
}