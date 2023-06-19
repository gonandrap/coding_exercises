# https://leetcode.com/problems/binary-search/

class Solution:
    def search(self, nums: List[int], target: int) -> int:
        """
        comments:
            - array may be empty
        """

        if len(nums) == 0:
            return -1

        """
        approach:
            have to discard (by half) where the number is not located, for sure
            how i do that?
                pick the elem in the middle
                if the middle elem is equal to target
                    done
                if target is bigger, discard left half
                if target is smaller, discard right half
            when should I stop?
                option 1 : could do it recursively (passing half of list each time) until list is empty
                    could be slow
                option 2 : have two indices within the list, that acts as the first and last index of the list to compare elems with target
                    when the indices crosses, then the list is empty, therefore, the number is not there
        """

        start = 0
        finish = len(nums) -1
        while start <= finish:
            middle_index = int((start + finish) / 2)
            middle_elem = nums[middle_index]
            if middle_elem == target:
                return middle_index
            elif middle_elem > target:
                finish = middle_index - 1       # I know for sure that nums[middle_index] != target, so I can discard middle_index
            else:
                start = middle_index + 1

        return -1