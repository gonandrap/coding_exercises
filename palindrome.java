// https://leetcode.com/problems/palindrome-number/description/

class Solution {
    public boolean isPalindrome(int x) {
        String number = String.valueOf(x);
        int i = 0;
        int j = number.length() - 1;
        while(i<j) {
            if (number.charAt(i) != number.charAt(j)) {
                return false;
            }
            i += 1;
            j -= 1;
        }
        return true;
    }
}