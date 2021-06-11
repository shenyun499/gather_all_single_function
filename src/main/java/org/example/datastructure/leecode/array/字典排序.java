package org.example.datastructure.leecode.array;

/**
 * @Description
 * @Author xuexue
 * @Date 2020/2/820:01
 */
public class 字典排序 {
    public void nextPermutation(int[] nums) {
        if (nums == null || nums.length == 0) {
            throw new IllegalArgumentException("data error");
        }
        //判断数组是否有序,且逆序
        boolean isSort = true;
        int i = nums.length -1;
        //从后面往前面
        for (; i > 0; i--) {
            //字典排序
            //定位逆序区域 如1234则if无法执行，如12354则索引i=3，索引及后面都是逆序的，
            //此时只要将i-1与逆序中右边刚好大于i-1的值互换就行，然后将逆序区域转为顺序
            if (nums[i] > nums[i - 1]) {
                isSort = false;
                break;
            }
        }
        //数组不是逆序，则取更大的字典数组。否则进行逆转操作
        if (!isSort) {//132 i=1 j=2
            //将逆序区域的刚好大于nums[i]的值交换位置
            for (int j = nums.length - 1; j >= i; j--) {
                if (nums[i - 1] < nums[j]) {
                    exchange(nums, i - 1, j);
                    break;
                }
            }
            //将逆序区域转为顺序
            for (int l = i, r = nums.length - 1; l < r; l++, r--) {
                exchange(nums, l, r);
            }
        } else {
            int j = nums.length - 1;
            for (;i < j; i++, j--) {
                exchange(nums, i, j);
            }
        }
    }
    public void exchange(int[] nums, int i, int j) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }
}
