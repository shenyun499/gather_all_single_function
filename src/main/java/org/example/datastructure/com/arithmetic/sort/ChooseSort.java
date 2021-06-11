package org.example.datastructure.com.arithmetic.sort;

/**
 * @Description
 * 功能：实现选择排序
 * 每次选择一个数为标准，找出最小的数，最后将选择的数和最小数交换位置
 * 时间复杂度O(n^2)；空间复杂度O(1)；不稳定；算法复杂度最好与最坏情况都是O(n^2)
 * @Author xuexue
 * @Date 2019/11/9 20:25
 */
public class ChooseSort {
    public static void main(String[] args) {
        int[] arrs = new int[]{3, 5, 2, 1, 4, 10};

        for (int i = 0; i < arrs.length -1; i++) {
            int index = i;//保存当前索引，用来记录最小值索引
            for(int j = i + 1; j < arrs.length; j++) {
                index = (arrs[index] > arrs[j]) ? j : index;//记录最小值索引
            }
            //交换值
            if (index != i) {
                int temp = arrs[i];
                arrs[i] = arrs[index];
                arrs[index] = temp;
            }
        }
        //chooseSort(arrs);
        for (int arr : arrs) {
            System.out.println(arr);
        }

    }

    /**
     * 选择排序算法实现
     * 1、需要排序的数组
     * 2、第一次for循环，n-1次选择
     * 3、定义变量index保存当前的循环索引值，如果找出了最小的数，则改变index的下标为最小数索引
     * 4、内循环，判断并找出最小的数，保存其索引值
     * 5、判断当前循环索引值是否与index相同，相同则不作任何操作，否则互换两个索引的值
     * @param arr
     */
    public static void chooseSort(int[] arr) {
        for (int i = 0; i < arr.length - 1; i++) {//每一次选择的数 总共需要选择n-1个
            //每一次选择的数和其它数 算法复杂度：n-1/n-2/n-3/../1 (1+n-1)n/2=n^2
            int index = i;//记录最小数的位置

            for (int j = i + 1; j < arr.length; j++) {
                //与冒泡排序比较，交互次数少，但是不稳定如 2 5 2 1 4
                if (arr[index] > arr[j])
                    index = j;//记录最小数位置
            }

            //进行交换 最多交换n-1次
            if (index != i) {
                int temp = arr[i];
                arr[i] = arr[index];
                arr[index] = temp;
            }
        }
    }
}
