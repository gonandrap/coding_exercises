

class Solution {
    public int arraySign(int[] nums) {
        /*
            multiplying all numbers could lead to an overflow, which will change the sign
            I don't actually need the number on each iteration, just the sign
        */
        int sign = 1;
        for(int i=0; i < nums.length; i++) {
            sign = sign * signFunc(nums[i]);
        }
        return sign;
    }

    public int signFunc(int number) {
        if (number > 0) {
            return 1;
        } else if (number < 0) {
            return -1;
        } else {
            return 0;
        }
    }
}