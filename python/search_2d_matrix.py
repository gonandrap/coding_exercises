import math\
from typing import List

# https://leetcode.com/problems/search-a-2d-matrix/
class Solution:
    def searchMatrix(self, matrix: List[List[int]], target: int) -> bool:

        # use binary search to find the row where supposely target should be
        # lineal search to find the target in the current row

        if len(matrix) == 1:
            return target in matrix[0]

        max_number_iterations = len(matrix)

        i = math.floor(len(matrix) / 2)

        left_lim = 0
        right_lim = len(matrix) - 1
        while max_number_iterations > 0 and left_lim <= right_lim:
            print(f'{i} of {len(matrix)}')
            if self.should_be_in_current_row(matrix, i, target):
                return target in matrix[i]
            elif matrix[i][0] > target:
                right_lim = i
            else:
                left_lim = i

            i = math.floor((left_lim + right_lim) / 2)    # left_lim and right_lim are 0-based index, need to conver them to 1-based index to avoid div 0
            max_number_iterations -= 1
        
        return target in matrix[left_lim] or target in matrix[right_lim]
    
    def should_be_in_current_row(self, matrix: List[List[int]], i : int, target: int) -> bool:
        return target >= matrix[i][0] and target <= matrix[i][-1] 