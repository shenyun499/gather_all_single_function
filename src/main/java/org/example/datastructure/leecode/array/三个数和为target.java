package org.example.datastructure.leecode.array;

import util.Arrays;
import util.LinkedHashSet;

/**
 * @Description
 * @Author xuexue
 * @Date 2020/2/820:18
 */
public class 三个数和为target {
    public static void main(String[] args) {
        threeSumClosest(new int[]{2,3}, 2);
    }
    public static int threeSumClosest(int[] nums, int target) {
        LinkedHashSet<Integer> list = new LinkedHashSet<>();
        list.add(2);
        Arrays.sort(nums);
        int result = nums[0] + nums[1] + nums[2];
        int l = 0, r = nums.length - 1;
        for (int i = 0; i < nums.length - 2; i++) {
            if (i > 0 && nums[i] == nums[i - 1]) {
                continue;
            }
            l = i + 1;
            while (l < r) {
                int temp = nums[i] + nums[l] + nums[r];
                if (Math.abs(temp - target) < Math.abs(result - target)) {
                    result = temp;
                    //去重
                    while (l < r && nums[l] == nums[l + 1]) {
                        l++;
                    }
                    while (l < r && nums[r] == nums[r - 1]) {
                        r--;
                    }
                }
                if(temp - target > 0) {
                    r--;
                } else {
                    l++;
                }
            }
            r = nums.length - 1;
        }
        return result;
    }
}
