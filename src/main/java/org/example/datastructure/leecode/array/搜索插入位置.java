package org.example.datastructure.leecode.array;

/**
 * @Description
 * 给定一个排序数组和一个目标值，在数组中找到目标值，并返回其索引。如果目标值不存在于数组中，返回它将会被按顺序插入的位置。
 *
 * 你可以假设数组中无重复元素。
 *
 * 示例 1:
 *
 * 输入: [1,3,5,6], 5
 * 输出: 2
 * 示例 2:
 *
 * 输入: [1,3,5,6], 2
 * 输出: 1
 * 示例 3:
 *
 * 输入: [1,3,5,6], 7
 * 输出: 4
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/search-insert-position
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 * @Author xuexue
 * @Date 2020/2/11 21:11
 */
public class 搜索插入位置 {
    public int searchInsert(int[] nums, int target) {
        //特判
        if (nums.length == 0) {
            throw new RuntimeException("数组为0");
        }
        return binarySearch(nums, target);
    }

    public int binarySearch(int[] nums, int target) {
        int left = 0, right = nums.length - 1;
        while (left <= right) {
            int mid = (left + right) / 2;
            if (target == nums[mid]) {
                //如果找到目标值，直接返回
                return mid;
            } else if (target > nums[mid]) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        //如果没有找到目标值，说明nums[left]刚好是大于target的，left位置将是target插入索引
        return left;
    }


}
