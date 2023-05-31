# https://leetcode.com/problems/valid-parentheses/

class Solution:
    opening_elems = ['(', '[', '{']
    closing_elems = [')', ']', '}']

    def isValid(self, s: str) -> bool:
        stack = []

        for elem in s:
            if self.is_opening(elem):
                stack.insert(0, elem)
            elif self.is_closing(elem):
                if len(stack) > 0 and self.is_matching_opening(stack.pop(0), elem):
                    continue
                else:
                    return False
            else:
                return False

        return len(stack) == 0

    def is_opening(self, elem):
        return elem in self.opening_elems

    def is_closing(self, elem):
        return elem in self.closing_elems

    def is_matching_opening(self, elem_to_compare, current_closing_elem):
        return self.opening_elems[self.closing_elems.index(current_closing_elem)] == elem_to_compare