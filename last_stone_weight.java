import java.util.PriorityQueue;
import java.util.Collections;

// https://leetcode.com/problems/last-stone-weight/description/

class SolutionLastStoneWeight {
    public int lastStoneWeight(int[] stones) {
        /*
            Comments
                - I need to sort the array in descending order
                - I need to iterate over the sorted array:
                    - if remaining elements == 0:
                        return 0
                    - if remaining elements == 1:
                        return stones[i]
                    - else:
                        // assume y=stones[i], x=stones[i+1] is the greater
                        if x==y:
                            // discard both
                            i+=2
                        else:    
                            new_weight = y - x
                            insert_ordered(new_weight, stones)      // since new_weight < stones[i] and new_weight < stones[i+1], it will be positioned somewhere after i+2 
                            i+=2
        */
        PriorityQueue<Integer> ordered_stones = new PriorityQueue<Integer>(Collections.reverseOrder());
        for(int i = 0; i<stones.length; i++) {
            ordered_stones.offer(stones[i]);
        }

        while(ordered_stones.size() > 0) {
            if (ordered_stones.size() == 1) {
                return ordered_stones.poll();
            } else {
                // there is at least 2 more stones
                Integer y = ordered_stones.poll();
                Integer x = ordered_stones.poll();
                
                if (y == x) {
                    continue;
                } else {
                    ordered_stones.offer(y - x);
                }
            }
        }

        return 0;       // there are no stones
    }
}
