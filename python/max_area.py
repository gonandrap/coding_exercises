
from typing import List, Set, Tuple

# https://leetcode.com/problems/container-with-most-water/
class Solution_not_mem_efficient:
    def maxArea(self, height: List[int]) -> int:
        return self.max_area_aux(set(), height, 0, 1)

    def max_area_aux(self, visited : Set[Tuple[int,int]], height : List[int], i: int , j : int) -> int:
        
        if j >= len(height) or (i,j) in visited:
            return 0

        visited.add((i,j))        # mark it to not revisit this node again
        
        area_i_j = self.area(height, i, j)
        area_i_inc_j = self.max_area_aux(visited, height, i, j+1)
        area_inc_i_inc_j = self.max_area_aux(visited, height, i+1, j+1)

        return max(area_i_j, area_i_inc_j, area_inc_i_inc_j)

    def area(self, height : List[int], i: int , j : int) -> int:
        return (j - i) * min(height[i], height[j])


    # This one is correct and efficient
    class Solution:
        def maxArea(self, height: List[int]) -> int:
            i = 0
            j = len(height) - 1
            current_max_area = self.area(height, i, j)
        

            while i<j:
                current_area = self.area(height, i, j)
                current_max_area = current_area if current_area > current_max_area else current_max_area

                if height[i] < height[j]:
                    i += 1
                else:
                    j -= 1

            return current_max_area

        def area(self, height : List[int], i: int , j : int) -> int:
            return (j - i) * min(height[i], height[j])