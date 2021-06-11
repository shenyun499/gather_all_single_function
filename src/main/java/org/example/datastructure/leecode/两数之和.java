package org.example.datastructure.leecode;

import util.HashMap;

/**
 * @Description
 * 给定一个整数数组 nums 和一个目标值 target，请你在该数组中找出和为目标值的那 两个 整数，并返回他们的数组下标。
 *
 * 你可以假设每种输入只会对应一个答案。但是，你不能重复利用这个数组中同样的元素。
 *
 * @Author xuexue
 * @Date 2019/12/11 13:00
 */
public class 两数之和 {

    public static void main(String[] args) {
        int[] ints = {2, 3, 4, 6};
        int[] ints1 = twoSum(ints, 11);
        System.out.println(ints1.toString());
    }

    //暴力解法
    public static int[] twoSum(int[] nums, int target) {
        //一个循环，拿一个数，与它的之后全部数分别相加，然后与target比较，相同则跳出循环
        for (int i = 0; i < nums.length - 1; i++) {
            for (int j = i + 1; j < nums.length; j++) {
                if (target == nums[i] + nums[j]) {
                    return new int[]{i,j};
                }
            }
        }
        throw new IllegalArgumentException("没有找到匹配的");
    }

    //一遍哈希
    public static int[] twoSum2(int[] nums, int target) {
        //拿target-数组值与哈希表进行判断，边存入边检查是否

        HashMap<Integer,Object> hashMap = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            //得出差值
            int complement = target - nums[i];

            //判断哈希表中是否存在此差值
            if (hashMap.containsKey(complement)) {
                //若存在，直接创建新的数组，并将索引设入返回
                return new int[]{(int)hashMap.get(complement), i};
            }
            //不存在差值，则将此数存在hash
            hashMap.put(nums[i], i);
        }
        throw new IllegalArgumentException("没有找到匹配的");
    }

}
