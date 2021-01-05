package offer;

import java.util.HashSet;
import java.util.Scanner;

/**
 * 剑指Offer03
 * 在一个长度为 n 的数组 nums 里的所有数字都在 0～n-1 的范围内。
 * 数组中某些数字是重复的，但不知道有几个数字重复了，也不知道每个数字重复了几次。请找出数组中任意一个重复的数字。
 *
 * 示例 1：
 * 输入：
 * [2, 3, 1, 0, 2, 5, 3]
 * 输出：2 或 3
 *
 * 限制：
 * 2 <= n <= 100000
 * 思路1：用容器，放入就检查容器是否存在。如，set集合，存在则返回false。空间复杂度O(n)，时间复杂度O(n)
 * 思路2：排序
 * 思路3：临时数组，计数算法
 *
 * @author ：HUANG ZHI XUE
 * @date ：Create in 2021-01-04
 */
public class Offer03 {
    public static void main(String[] args) {
        int sign = 0;
        HashSet hashSet = new HashSet();
        Scanner scan = new Scanner(System.in);
        while (true) {
            int nextInt = scan.nextInt();
            if (hashSet.contains(nextInt)) {
                sign = nextInt;
                break;
            }
            hashSet.add(nextInt);
        }
        System.out.println(sign);
    }

    public int findRepeatNumber(int[] nums) {
        // 临时数组
        int[] arr = new int[nums.length];
        for (int i = 0; i < nums.length; i++) {
            // 将数组元素当作下标索引放入新数组中，放一次新数组值就加1，如果里面的值为2则返回重复值
            arr[nums[i]]++;
            if (arr[nums[i]] > 1) {
                return nums[i];
            }
        }
        return 0;
    }

}
