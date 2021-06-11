package org.example.datastructure.leecode.array;

/**
 * @Description
 * @Author xuexue
 * @Date 2020/2/819:59
 */
public class 自定义快速排序 {
    public void quickSort(int[] array, int startIndex, int endIndex) {
        if (startIndex >= endIndex) {
            return;
        }
        int pivotIndex = partition(array, startIndex, endIndex);
        quickSort(array, startIndex, pivotIndex - 1);
        quickSort(array, pivotIndex + 1, endIndex);
    }

    public int partition(int[] array, int startIndex, int endIndex) {
        //双边分治
        int pivot = array[startIndex];
        int l = startIndex, r = endIndex;
        while (l < r) {
            while (l < r && array[r] > pivot) {
                r--;
            }
            array[l] = array[r];
            while (l < r && array[l] <= pivot) {
                l++;
            }
//            while (l < r && smaller(pivot, array[r])) {
//                r--;
//            }
//            while (l < r && smaller(array[l], pivot)) {
//                l++;
//            }
            array[r] = array[l];
        }
        array[startIndex] = array[l];
        array[l] = pivot;
        return l;
    }

    public boolean smaller(int a, int b) {
        String s1 = Integer.toString(a) + b;
        String s2 = Integer.toString(b) + a;
        //比较两个数的字典顺序
        int compareTo = s1.compareTo(s2);
        if (compareTo <= 0) {
            return true;
        }
        return false;
    }
}
