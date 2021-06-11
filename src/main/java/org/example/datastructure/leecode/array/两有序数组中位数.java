package org.example.datastructure.leecode.array;

import util.ArrayList;
import util.Arrays;
import util.List;

/**
 * @Description
 * 给定两个大小为 m 和 n 的有序数组 nums1 和 nums2。
 *
 * 请你找出这两个有序数组的中位数，并且要求算法的时间复杂度为 O(log(m + n))。
 *
 * 你可以假设 nums1 和 nums2 不会同时为空。
 *
 * 解法：双指针 指针1 2 针对两个数组同时操作
 *
 * @Author xuexue
 * @Date 2020/1/520:00
 */
public class 两有序数组中位数 {

    public double findMedianSortedArrays(int[] nums1, int[] nums2) {
        if (nums1.length > 0 && nums2.length > 0) {
            List<Integer> list = new ArrayList<>();
            int i = 0;
            if (nums1.length == nums2.length) {
                while (i < nums1.length) {
                    list.add(nums1[i]);
                    list.add(nums2[i++]);
                }
            } else if(nums1.length < nums2.length) {//大于或者等于
                while (i < nums1.length) {
                    list.add(nums1[i]);
                    list.add(nums2[i++]);
                }
                while (i < nums2.length) {
                    list.add(nums2[i++]);
                }
            } else {
                while (i < nums2.length) {
                    list.add(nums1[i]);
                    list.add(nums2[i++]);
                }
                while (i < nums1.length) {
                    list.add(nums1[i++]);
                }
            }
            if ((list.size() & 1) == 1) {
                return list.get((list.size() / 2));
            } else {
                return (list.get(list.size() / 2) + list.get((list.size() / 2) - 1)) / 2;
            }
        } else if (nums1.length > 0) {
            if ((nums1.length & 1) == 1) {
                return nums1[nums1.length / 2];
            } else {
                return (nums1[nums1.length / 2] + nums1[nums1.length / 2 - 1]) / 2;
            }
        } else {
            if ((nums1.length & 1) == 1) {
                return nums1[nums1.length / 2];
            } else {
                return (nums1[nums1.length / 2] + nums1[nums1.length / 2 - 1]) / 2;
            }
        }
    }

    public static void main(String[] args) {
        int[] arr = new int[]{1, 2};
        int[] arr2 = new int[]{3, 4};
        findMedianSortedArrays2(arr, arr2);
    }


    //双指针法解决两数组中位数
    public static double findMedianSortedArrays2(int[] nums1, int[] nums2) {
        //1、如果nums1长度为0或者null
        if (nums1 == null || nums1.length == 0) {
            return bw(nums2);
        }

        //2、如果nums2长度为0或者null
        if (nums2 == null || nums2.length == 0) {
            return bw(nums1);
        }

        //3、nums1和num2都不为0
        //定义双指针
        int i = 0, j = 0;
        //定义集合，消耗空间复杂度m+n
        List<Integer> list = new ArrayList<>(nums1.length + nums2.length);

        while (i < nums1.length && j < nums2.length) {
            if (nums1[i] < nums2[j]) {
                list.add(nums1[i++]);
            } else if (nums1[i] > nums2[j]) {
                list.add(nums2[j++]);
            } else {
                list.add(nums1[i++]);
                list.add(nums2[j++]);
            }
        }

        //判断剩下的数组
        if (i < nums1.length) {
            while (i < nums1.length) {
                list.add(nums1[i++]);
            }
        }
        if (j < nums2.length) {
            while (j < nums2.length) {
                list.add(nums2[j++]);
            }
        }
        for (Integer integer : list) {
            System.out.println(integer);
        }
        return bw(list.toArray(new Integer[0]));//集合转数组
    }

    public static double bw(Integer[] arr) {
        //(arr.length & 1) == 1 奇数
        // else 偶数 取两数平均值
        if ((arr.length & 1) == 1) {
            return arr[arr.length / 2];
        } else {
            return (arr[arr.length / 2] + arr[arr.length / 2 - 1]) / 2.0;
        }
    }

    public static double bw(int[] arr) {
        //(arr.length & 1) == 1 奇数
        // else 偶数 取两数平均值
        if ((arr.length & 1) == 1) {
            return arr[arr.length / 2];
        } else {
            return (arr[arr.length / 2] + arr[arr.length / 2 - 1]) / 2.0;
        }
    }
}
