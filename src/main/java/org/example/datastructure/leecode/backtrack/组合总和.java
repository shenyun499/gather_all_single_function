package org.example.datastructure.leecode.backtrack;

import com.sun.media.jfxmedia.track.Track;
import org.junit.Test;

import util.*;

/**
 * @Description
 * 给定一个无重复元素的数组 candidates 和一个目标数 target ，找出 candidates 中所有可以使数字和为 target 的组合。
 *
 * candidates 中的数字可以无限制重复被选取。
 *
 * 说明：
 *
 * 所有数字（包括 target）都是正整数。
 * 解集不能包含重复的组合。 
 * 示例 1:
 *
 * 输入: candidates = [2,3,6,7], target = 7,
 * 所求解集为:
 * [
 *   [7],
 *   [2,2,3]
 * ]
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/combination-sum
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 * @Author xuexue
 * @Date 2020/2/11 19:22
 */
public class 组合总和 {

    //进行必要数据准备
    List<List<Integer>> lists = new ArrayList<>();
    LinkedList<Integer> list = new LinkedList<>();
    public List<List<Integer>> combinationSum(int[] candidates, int target) {
        Arrays.sort(candidates);
        backTrack(candidates, 0 , target);
        return lists;
    }

    /**
     * 1、查找数组第一层(全部数字)，target与第一个作减法，其它剩下的等待回溯继续查找作减法
     * 2、递归结束条件：作减法为负数，及等于0,等于0正是所求
     * @param arr 数组
     * @param start 去重复
     * @param target 目标值
     */
    public void backTrack(int[] arr, int start, int target) {
        //递归结束条件
        if (target < 0) {
            return;
        }
        if (target == 0) {
            lists.add(new ArrayList<>(list));
            return;
        }

        //核心代码
        //&& target - arr[i] >= 0代码做了点优化，但是要求数组要有序；可以理解成剪枝
        for (int i = start; i < arr.length && target - arr[i] >= 0; i++) {
            //做选择
            list.add(arr[i]);
            backTrack(arr, i, target - arr[i]);
            //回溯,丢元素
            list.removeLast();
        }
    }

    @Test
    public void t() {
        List<List<Integer>> lists = new ArrayList<>();
        LinkedList<Integer> list = new LinkedList<Integer>() {{
            add(3);add(4);
        }};
        list.removeLast();
        lists.add(list);
        for (List<Integer> li : lists) {
            System.out.println(li);
        }
        System.out.println(lists);
    }
}
