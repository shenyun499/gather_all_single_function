package leecode.array;

import org.junit.Test;

/**
 * @Description
 * 给定一个按照升序排列的整数数组 nums，和一个目标值 target。找出给定目标值在数组中的开始位置和结束位置。
 *
 * 你的算法时间复杂度必须是 O(log n) 级别。
 *
 * 如果数组中不存在目标值，返回 [-1, -1]。
 *
 * 示例 1:
 *
 * 输入: nums = [5,7,7,8,8,10], target = 8
 * 输出: [3,4]
 * 示例 2:
 *
 * 输入: nums = [5,7,7,8,8,10], target = 6
 * 输出: [-1,-1]
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/find-first-and-last-position-of-element-in-sorted-array
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 * @Author xuexue
 * @Date 2020/2/1016:32
 */
public class 在排序数组中查找第一个和最后一个位置 {

    @Test
    public void seTest() {
        int[] arr = {5,7,7,8,8,10};
        int[] ints = searchRange(arr, 8);
        System.out.println(ints[0] + " " + ints[1]);
    }

    public int[] searchRange(int[] nums, int target) {
        //特判
        if (nums.length == 0) {
            return new int[] {-1, -1};
        } else if (nums.length == 1) {
            int i = nums[0] == target ? 0 : -1;
            return new int[]{i, i};
        }
        //查找第一个位置 sign == 0
        int lowIndex = binarySerachFirst(nums, 0, nums.length -1, target, 0);
        //查找最后一个位置 sign == 1
        int highIndex = binarySerachFirst(nums, 0, nums.length -1, target, 1);
        return new int[] {lowIndex, highIndex};
    }
    //查找第一个位置&找到最后一个元素
    public static int binarySerachFirst(int[] nums,int left, int right, int target, int sign) {
        int lowIndex = -1;
        while (left <= right) {
            int inV = (left + right) / 2;
            //如果找到值，继续往左边找，直到找到最小索引&如果找到值，继续往右边找，直到找到最大索引
            if (nums[inV] == target) {
                lowIndex = inV;
                if (sign == 0) {
                    right = inV - 1;
                } else if (sign == 1) {
                    left = inV + 1;
                }
            } else if (nums[inV] < target) {
                left = inV + 1;
            } else {
                right = inV - 1;
            }
        }
        return lowIndex;
    }
}
