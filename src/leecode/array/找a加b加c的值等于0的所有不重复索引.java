package leecode.array;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @Description
 * @Author xuexue
 * @Date 2020/2/820:23
 */
public class 找a加b加c的值等于0的所有不重复索引 {
    public List<List<Integer>> threeSum(int[] nums) {
        List<List<Integer>> list = new ArrayList<>();
        if (nums == null || nums.length < 3) {
            return list;
        }
        Arrays.sort(nums);
        //双指针
        int i = 0, j = nums.length - 1;
        //索引
        int index = 0;
        while (index < nums.length - 2) {
            //1、第一个数都大于0，直接返回
            if (nums[index] > 0) {
                return list;
            }
            //2、去重复 和前面一个数相同，则跳过
            if(index > 0 && nums[index] == nums[index - 1]) {
                index++;
                continue;
            }
            //3、否则，求a+b+c=0
            i = index + 1;
            while (i < j) {
                //a+b+c=0
                if (nums[index] + nums[i] == -nums[j]) {
                    list.add(Arrays.asList(nums[index], nums[i], nums[j]));
                    //去重
                    while (i < j && nums[i] == nums[i + 1]) {
                        i++;
                    }
                    while (i < j && nums[j] == nums[j - 1]) {
                        j--;
                    }
                    i++;
                    j--;
                    continue;
                }
                //如果a+b>-c 说明a+b+c>0，数字太大，需要j--；否则数字太小需要增大i++
                if (nums[index] + nums[i] > -nums[j]) {
                    j--;
                } else {
                    i++;
                }
            }
            index++;
            j = nums.length - 1;
        }
        return list;
    }

}
