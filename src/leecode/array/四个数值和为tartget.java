package leecode.array;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @Description
 * @Author xuexue
 * @Date 2020/2/820:17
 */
public class 四个数值和为tartget {
    public List<List<Integer>> fourSum(int[] nums, int target) {
        List<List<Integer>> list = new ArrayList<>();
        if (nums == null || nums.length < 4) {
            return list;
        }
        Arrays.sort(nums);
        int index = 0, i = 0;
        int l = 0, r = nums.length - 1;
        for (; index < nums.length -3; index++) {
            if (index > 0 && nums[index] == nums[index - 1]) {
                continue;
            }
            i = index + 1;
            for (; i < nums.length - 2; i++) {
                if (i > index + 1 && nums[i] == nums[i - 1]) {
                    continue;
                }
                l = i + 1;
                while (l < r) {
                    int num = nums[index] + nums[i] + nums[l] + nums[r];
                    if (num == target) {
                        list.add(Arrays.asList(nums[index], nums[i], nums[l], nums[r]));
                        //去重
                        while(l < r && nums[l] == nums[l + 1]) {
                            l++;
                        }
                        while(l < r && nums[r] == nums[r - 1]) {
                            r--;
                        }
                    }

                    //左指针右移
                    if (num < target) {
                        l++;
                    } else {
                        r--;
                    }
                }
                r = nums.length - 1;
            }
        }
        return list;
    }
}
