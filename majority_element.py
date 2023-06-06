# https://leetcode.com/problems/majority-element/

class Solution:
    def majorityElement(self, nums: List[int]) -> int:
        """
            iteration and counting reps?
            priority queue to sort the one with most of reps? 
                key of queue is a duple : [number_reps, num]
                it should be unique in the second item of the duple

            naive solution
                sort the array
                iterate one time and bubble up the number with higher reps
                slow but efficient in terms of memory

            iterating multiple times? 
            first iteration: identify nums and reps, store in a dict [num, reps]
            second iteration: iterate the dict and bubble up the one with max reps and its associated number
        """

        reps = {}
        for num in nums:
            reps[num] = reps.setdefault(num, 0) + 1

        max_num_rep = 0     # we know for sure there will be at least one number that will be higher than this
        majority_elem = nums[0]
        for (num, num_reps) in reps.items():
            if num_reps > max_num_rep:
                max_num_rep = num_reps
                majority_elem = num

        return majority_elem

