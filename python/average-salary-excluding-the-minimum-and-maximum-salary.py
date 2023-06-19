# https://leetcode.com/problems/average-salary-excluding-the-minimum-and-maximum-salary/

class Solution:
    def average(self, salary: List[int]) -> float:
        """
            I need to iterate over all the elements to know what is the max and minimum. 
            Naive solution : sort the array and calculate avg between [1:-1]

            Can we do it in lineal time?
            Only one iteration may not be possible, because we don't know if the current element should be accumulated in the sum or not (depending if the elem
            is a global max or min)

            Can we iterate twice? 
            First iteration : find the max and min (dual bubble sort)
            Second iteration : considering elems are unique, we just accumulate every element different than max and min, and divide by len-2
        """

        max_elem = salary[0]
        min_elem = salary[1] 
        for elem in salary:
            max_elem = max_elem if max_elem > elem else elem
            min_elem = min_elem if min_elem < elem else elem

        accumulated = 0.0
        for elem in salary:
            if not elem == max_elem and not elem == min_elem:
                accumulated += elem

        return accumulated / (len(salary) - 2)