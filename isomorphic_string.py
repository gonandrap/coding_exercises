class Solution:
    # https://leetcode.com/problems/isomorphic-strings/
    
    def isIsomorphic(self, s: str, t: str) -> bool:
        """
            first impression: i'th character must have the same number of reps on each sentence
                in addition to that property, we have to (somehow) guarantee the order of those reps

            proposal?
                iterate over s and:
                    create a dict = {char, [occurrences indexes]}
                    create an array with i_th = char
                iterate over t 
                if for each character, they share the same ocurrences (reps and location of each)
                    then we have a success
        """
        ocurrs_s = {}
        ocurrs_t = {}
        indexes_s = [None for i in range(len(s))]
        indexes_t = [None for i in range(len(t))]
        for i in range(len(s)):
            char_s = s[i]
            char_t = t[i]
            ocurrs_s.setdefault(char_s, []).append(i)
            ocurrs_t.setdefault(char_t, []).append(i)
            indexes_s[i] = char_s
            indexes_t[i] = char_t

        for i in range(len(indexes_s)):
            char_s = indexes_s[i]
            char_t = indexes_t[i]

            if not ocurrs_s[char_s] == ocurrs_t[char_t]:
                return False

        return True