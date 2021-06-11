package org.example.datastructure.offer;

/**
 * 剑指 Offer 11. 旋转数组的最小数字
 * 把一个数组最开始的若干个元素搬到数组的末尾，我们称之为数组的旋转。输入一个递增排序的数组的一个旋转，输出旋转数组的最小元素。例如，数组 [3,4,5,1,2] 为 [1,2,3,4,5] 的一个旋转，该数组的最小值为1。  
 *
 * 示例 1：
 *
 * 输入：[3,4,5,1,2]
 * 输出：1
 *
 * 示例 2：
 * 输入：[2,2,2,0,1]
 * 输出：0
 * 思路1：直接一次遍历找最小值，时间复杂度O(n)
 * 思路2：二分法找，时间复杂度O(logn)，坑：只用右边和中位数，不然得不到效果
 *
 * @author ：HUANG ZHI XUE
 * @date ：Create in 2021-01-13
 */
public class Offer11 {
    public int minArray(int[] numbers) {
        // 双指针
        int l = 0, r = numbers.length - 1;
        while (l < r) {
            int mid = (l + r) >>> 1;
            // 最右边比中位数大，则直接将右边改成中位数
            if (numbers[r] > numbers[mid]) {
                r = mid;
            } else if (numbers[r] < numbers[mid]) {
                // 右边比中位数小，则直接将左边移到中位数+1
                l = mid + 1;
            } else {
                // 右边等于中位数
                r--;
            }
        }
        return numbers[l];
    }
    /*public int minArray(int[] numbers) {
        int min = numbers[0];
        for (int i = 1; i < numbers.length; i++) {
            min = min > numbers[i] ? numbers[i] : min;
        }
        return min;
    }*/
}
