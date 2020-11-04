package leecode.array;

import leecode.ListNode;
import org.junit.Test;

import java.util.List;

/**
 * @Description
 * 假设按照升序排序的数组在预先未知的某个点上进行了旋转。
 *
 * ( 例如，数组 [0,1,2,4,5,6,7] 可能变为 [4,5,6,7,0,1,2] )。
 *
 * 搜索一个给定的目标值，如果数组中存在这个目标值，则返回它的索引，否则返回 -1 。
 *
 * 你可以假设数组中不存在重复的元素。
 *
 * 你的算法时间复杂度必须是 O(log n) 级别。
 *
 * 示例 1:
 *
 * 输入: nums = [4,5,6,7,0,1,2], target = 0
 * 输出: 4
 * 示例 2:
 *
 * 输入: nums = [4,5,6,7,0,1,2], target = 3
 * 输出: -1
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/search-in-rotated-sorted-array
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 * @Author xuexue
 * @Date 2020/2/10 12:00
 */
public class 搜索旋转排序数组 {

    @Test
    public void binarySearchTest() {
        int[] arr = {4,5,1,2,3};
        int search = search(arr, 1);
        System.out.println(search);
    }

    public static int search(int[] nums, int target) {
        //特判--数组长度0和1的情况
        if (nums.length == 0) {
            return -1;
        } else if (nums.length == 1) {
            return target == nums[0] ? 0 : -1;
        }

        //定位旋转点,因为必须是O(logn),分治解决
        int inV = findInNumber(nums, 0, nums.length - 1);
        System.out.println(inV);
        //根据旋转点，取数组左还是右得到索引
        if (target < nums[0]) {
            return binarySerach(nums, inV, nums.length - 1, target);
        } else {
            return binarySerach(nums, 0, inV, target);
        }
    }
    //找到旋转点--二分查找变种
    public static int findInNumber(int[] nums, int left, int right) {
        while (left < right) {
            int inV = (left + right) / 2;
            if (nums[inV] > nums[inV + 1]) {
                return inV + 1;
            } else {
                //第一个数小于中间数，成立则右边继续查找
                if (nums[left] <= nums[inV]) {
                    left = inV + 1;
                } else {
                    right = inV;
                }
            }
        }
        return nums.length - 1;
    }

    //二分查找得到target索引
    public static int binarySerach(int[] nums, int left, int right, int target) {
        while (left <= right) {
            int inV = (left + right) / 2;
            if (target == nums[inV]) {
                return inV;
            } else if (target < nums[inV]) {
                right = inV - 1;
            } else {
                left = inV + 1;
            }
        }
        return -1;
    }

}
