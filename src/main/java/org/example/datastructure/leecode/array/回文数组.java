package org.example.datastructure.leecode.array;

import util.Scanner;

/**
 * @Description
 * @Author xuexue
 * @Date 2020/2/1516:22
 */
public class 回文数组 {
    public static void main(String[] args) {
        //数据录入
        Scanner scan = new Scanner(System.in);
        int n = scan.nextInt();
        int[] arr = new int[n];
        int s = 0;
        for (int i = 0; i < n; i++) {
            int a = scan.nextInt();
            arr[i] = a;
            s += a;
        }

        //双指针进行操作，并记录最小和值
        int num = 0;
        int left = 0, right = n - 1;
        while (left < right) {
            //两边取最小值
            if (arr[left] <= arr[right])  {
                arr[right] = arr[left];
                num += 2*arr[left];
            } else if (arr[left] > arr[right]){
                arr[left] = arr[right];
                num += 2*arr[left];
            }
            left++;
            right--;
        }
        //左奇偶判断,奇数需要将中间值加入num中
        if ((n & 1) == 1) {
            num += arr[left];
        }
        System.out.println(num);
        System.out.println(s);
    }
}
