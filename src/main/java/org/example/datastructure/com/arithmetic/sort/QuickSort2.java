package org.example.datastructure.com.arithmetic.sort;

import util.HashMap;
import util.Stack;

/**
 * @Description
 * 功能：实现快速排序 非递归方式
 * 快速排序：是冒泡排序的延伸，优化；时间复杂度O(nlogn)；最好情况O(nlogn)，最坏情况O(n^2);不稳定
 * 方法：不同的是，冒泡排序在每一轮中只把1个元素冒泡到数列的一端，而快速排序则 在每一轮挑选一个基准元素，
 * 并让其他比它大的元素移动到数列一边，比它小的元 素移动到数列的另一边，从而把数列拆解成两个部分，这种方
 * 法叫做分治法
 * 空间复杂度O(logn):首先就地快速排序使用的空间是O(1)的，也就是个常数级；而真正消耗空间的就是递归调用了，因为每次递归就要保持一些数据；
 * 不稳定 3 3 3 2
 * @Author xuexue
 * @Date 2019/11/10 13:06
 */
public class QuickSort2 {
    public static void main(String[] args) {
        int[] arrs = new int[]{3, 5, 2, 3, 4, 10};
        quickSort(arrs, 0, arrs.length - 1);
        for (int arr : arrs) {
            System.out.println(arr);
        }
    }

    /**
     * 非递归方式实现快速排序算法
     * @param arr
     * @param startIndex
     * @param endIndex
     */
    public static void quickSort(int[] arr, int startIndex, int endIndex) {
        //定义一个栈
        Stack<HashMap<String, Integer>> quickSortStack = new Stack<>();

        HashMap<String, Integer> rootParam = new HashMap<>();
        rootParam.put("startIndex", startIndex);
        rootParam.put("endIndex", endIndex);
        quickSortStack.push(rootParam);

        // 循环结束条件：栈为空时 一栈出顶一分二，左右两入栈，右在顶出栈又分左右...
        while (!quickSortStack.isEmpty()) {
            //栈顶元素出栈，得到起止下标
            HashMap<String, Integer> param = quickSortStack.pop();

            //得到基准元素新的坐标
            int newPivotIndex = parsition(arr,param.get("startIndex"), param.get("endIndex"));

            //根据基准元素分成两部分, 把每一部分的起止下标入栈
            if(param.get("startIndex") < newPivotIndex - 1) {
                HashMap<String, Integer> leftParam = new HashMap<>();
                leftParam.put("startIndex", startIndex);
                leftParam.put("endIndex", newPivotIndex - 1);
                quickSortStack.push(leftParam);
            }

            if(newPivotIndex + 1 < param.get("endIndex")) {
                HashMap<String, Integer> rightParam = new HashMap<>();
                rightParam.put("startIndex", newPivotIndex + 1);
                rightParam.put("endIndex", endIndex);
                quickSortStack.push(rightParam);
            }


        }
    }

    /**
     * 分治（单边循环）
     * 移动数组元素位置，计算每次的pivot的位置
     * @param arr
     * @param startIndex
     * @param endIndex
     * @return
     */
    public static int parsition(int[] arr, int startIndex, int endIndex) {
        //选择数组第一个元素为基准元素pivot，也可以随机选择
        int pivot = arr[startIndex];

        //设置mark指针，用来记录需要交换值的索引位置
        int mark = startIndex;

        //进行一趟排序 例如数组：4 3 5 2 6 则pivot=4，mark=0;1、pivot>3，mark=1;2、4<5,不作操作；3、pivot>2,mark=2,交换arr[2]与元素2的位置，数组变成4 3 2 5 6；4、pivot<6,不作操作；5、结束循环，进入下面的arr[0]与arr[mark]交换值，即将4与2交换位置变成2 3 4 5 6
        for (int i = startIndex + 1; i <= endIndex; i++) {
            //如果基准元素pivot大于arr[i]，则mark自增1，并且交换mark和i两个位置的值
            if (pivot > arr[i]) {
                mark ++;
                int temp = arr[mark];
                arr[mark] = arr[i];
                arr[i] = temp;

            }
        }

        //最后将数组第一个元素值pivot和mark最终位置值交换
        arr[startIndex] = arr[mark];
        arr[mark] = pivot;

        //返回新的交换后的pivot新的索引，也就是mark
        return mark;
    }
}
