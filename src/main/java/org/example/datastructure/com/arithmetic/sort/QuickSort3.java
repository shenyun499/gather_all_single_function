package org.example.datastructure.com.arithmetic.sort;

/**
 * @Description
 * 功能：实现快速排序
 * 快速排序：是冒泡排序的延伸，优化；时间复杂度O(nlogn)；最好情况O(nlogn)，最坏情况O(n^2);不稳定
 * 方法：不同的是，冒泡排序在每一轮中只把1个元素冒泡到数列的一端，而快速排序则 在每一轮挑选一个基准元素，
 * 并让其他比它大的元素移动到数列一边，比它小的元 素移动到数列的另一边，从而把数列拆解成两个部分，这种方
 * 法叫做分治法
 * 空间复杂度O(logn):首先就地快速排序使用的空间是O(1)的，也就是个常数级；而真正消耗空间的就是递归调用了，因为每次递归就要保持一些数据；
 * 不稳定 3 3 3 2
 * @Author xuexue
 * @Date 2019/11/10 13:06
 */
public class QuickSort3 {
    public static void main(String[] args) {
        int[] arrs = new int[]{3, 5, 2, 3, 4, 10};
        quickSort(arrs, 0, arrs.length - 1);
        for (int arr : arrs) {
            System.out.println(arr);
        }
    }

    /**
     * 功能：递归实现快速排序
     * 递归结束条件 startIndex >= endIndex
     * 重新获得pivot基数元素的中间的位置索引，作为新的递归函数的endIndex和startIndex
     * @param arr
     * @param startIndex
     * @param endIndex
     */
    public static void quickSort(int[] arr, int startIndex, int endIndex) {
        //递归结束条件
        if (startIndex >= endIndex)
            return;

        //重新计算获得pivot的位置索引
        int newPivotIndex = parsition(arr, startIndex, endIndex);

        //根据新的pivot位置索引，进行左边数组与右边数组递归
        quickSort(arr, startIndex, newPivotIndex - 1);//左边数组递归
        quickSort(arr, newPivotIndex + 1, endIndex);//右边数组递归
    }

    /**
     * 分治（双边循环）
     * 移动数组元素位置，计算每次的pivot的位置
     * @param arr
     * @param startIndex
     * @param endIndex
     * @return
     */
    public static int parsition(int[] arr, int startIndex, int endIndex) {
        int pivot = arr[startIndex];
        int low = startIndex, high = endIndex;
        while (low < high) {
            //从右边开始，找到小于pivot的数
            while (low < high && arr[high] >= pivot) {
                high--;
            }
            //将小于pivot的值放到第一个位置
            arr[low] = arr[high];
            //从左边开始，找到大于pivot的数
            while (low < high && arr[low] <= pivot) {
                low++;
            }
            //将大于pivot的值放入high的位置
            arr[high] = arr[low];
        }
        //结束后，将pivot放入最终值
        arr[low] = pivot;
        return low;
    }
}
