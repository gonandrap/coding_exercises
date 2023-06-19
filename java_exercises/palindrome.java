package java_exercises;
// https://leetcode.com/problems/palindrome-number/description/

class SolutionPalindrome {
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

    public static void main(String[] args) {
        int n = 1221;
        SolutionPalindrome s = new SolutionPalindrome();
        System.out.println("is palindrome : " + s.isPalindrome(n));
    }
}