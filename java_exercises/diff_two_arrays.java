package java_exercises;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;

// https://leetcode.com/problems/find-the-difference-of-two-arrays/description/

class SolutionDiffTwoArrays {
    public List<List<Integer>> findDifference(int[] nums1, int[] nums2) {
        Arrays.sort(nums1);
        Arrays.sort(nums2);

        List<Integer> nums1_result = new ArrayList<Integer>();
        List<Integer> nums2_result = new ArrayList<Integer>();

        int i = 0;
        int j = 0;
        while(i<nums1.length && j<nums2.length) {
            if (nums1[i] < nums2[j]) {
                nums1_result.add(nums1[i]);
                i = advance_with_rep(nums1, i);
            } else if (nums2[j] < nums1[i]) {
                nums2_result.add(nums2[j]);
                j = advance_with_rep(nums2, j);
            } else {
                // they are equal, have to move on
                i = advance_with_rep(nums1, i);
                j = advance_with_rep(nums2, j);
            }
        }

        while(i<nums1.length) {
            nums1_result.add(nums1[i]);
            i = advance_with_rep(nums1, i);
        }

        while(j<nums2.length) {
            nums2_result.add(nums2[j]);
            j = advance_with_rep(nums2, j);
        }

        List<List<Integer>> result = new ArrayList<List<Integer>>(2);
        result.add(nums1_result);
        result.add(nums2_result);
        return result;
    }

    // instead of doing this, could I just use a set?
    private int advance_with_rep(int[] nums, int i) {
        int i_res = i+1;
        while(i_res<nums.length && nums[i] == nums[i_res]) {
            i_res += 1;
        }
        return i_res;
    }

}
    