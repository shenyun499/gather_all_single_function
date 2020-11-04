package com.arithmetic.sort;

/**
 * @Description
 * 功能：实现插入排序
 * 从第二个数开始，拿出第二个数进行向前插入排序，一直到拿到最后一个数向前做插入排序
 * 时间复杂度O(n^2)；空间复杂度O(1)；稳定；算法复杂度最好O(n),不需要加条件实现，最坏情况是O(n^2)
 * @Author xuexue
 * @Date 2019/11/9 22:25
 */
public class InsertSort {
    public static void main(String[] args) {
        int[] arrs = new int[]{3, 5, 2, 1, 4, 10};

        insertSort(arrs);

        for (int arr : arrs) {
            System.out.println(arr);
        }
    }

    /**
     * 算法：插入排序
     * 需要选择作为插入的数：从第二个开始，共n-1个数
     * 每次将选择作为插入的数与前面比较，如果小于则前面的数往后移动一位，否则插入到这个位置
     * @param arr
     */
    public static void insertSort(int[] arr) {
        for (int i = 1; i < arr.length; i++) {//总共需要选择作为插入的数为n-1个
            int num = arr[i];//选择作为插入的数
/*            int leftIndex = i -1;//插入的数前一个位置索引

            //插入排序算法实现
            while (leftIndex >= 0 && num < arr[leftIndex]) {
                arr[leftIndex + 1] = arr[leftIndex];//交换位置
                leftIndex--;
            }

            if (num != arr[i])
                arr[leftIndex + 1] = num;*/

           int j = i - 1;//记录插入的数前一个位置的索引
           for (; j >= 0; j--) {//从插入元素的前一个开始
                //进行比较 如果作为插入的数小于则前面的数往后移动一位，否则进入else插入到这个位置
                if (num < arr[j])
                    arr[j + 1] = arr[j];//前面的数往后移动一位
                else
                    break;

            }
            if (num != arr[i])//说明移动了位置，需要插入
                arr[j + 1] = num;//将作为比较的数插入数组中，形成新的片段有序数组
        }
    }
}
