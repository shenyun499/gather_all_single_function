package leecode.array;

import java.util.LinkedHashSet;
import java.util.LinkedList;

/**
 * @Description
 * 给定长度为 2n 的数组, 你的任务是将这些数分成 n 对, 例如 (a1, b1), (a2, b2), ..., (an, bn) ，使得从1 到 n 的 min(ai, bi) 总和最大。
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/array-partition-i
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 * @Author xuexue
 * @Date 2020/2/11 17:28
 */
public class 数组拆分1 {
    public static void main(String[] args) {
        数组拆分1 a  = new 数组拆分1();
        int[] arr = {3, 7, 5, 4, 8, 2};
        a.quickSort(arr, 0, arr.length - 1);
        for (int i = 0; i < arr.length; i++) {
            System.out.println(arr[i]);
        }
        LinkedHashSet<Integer> list = new LinkedHashSet<>();
        LinkedList<Integer> list2 = new LinkedList<>();
        list2.getLast();
        list2.contains(2);
    }
    //使用快排实现
    public int arrayPairSum(int[] nums) {
        int sum = 0;
        quickSort(nums, 0, nums.length - 1);
        for (int i = 0; i < nums.length; i = i + 2) {
            sum += nums[i];
        }
        return sum;
    }

    //快速排序
    public void quickSort(int[] nums, int left, int right) {
        //递归结束条件
        if (left >= right) {
            return;
        }
        //得到一次排序后的索引
        int pivotIndex = findInValue(nums, left, right);
        //左边继续排序
        quickSort(nums, left, pivotIndex - 1);
        //右边继续排序
        quickSort(nums, pivotIndex + 1, right);
    }

    //确定一次排序后的索引
    public int findInValue(int[] nums, int left, int right) {
        //选取基准元素
        int pivot = nums[left];
        int start = left;

        //双指针实现一次排序
        while (left < right) {
            //先从右边开始
            while (left < right && pivot < nums[right]) {
                right--;
            }
            //将小数插入到第一个位置
            nums[left] = nums[right];
            while (left < right && pivot >= nums[left]) {
                left++;
            }
            //将大数插入到右边边位置
            nums[right] = nums[left];
        }
        //交换基准数
        nums[left] = pivot;
        return left;
    }
}
