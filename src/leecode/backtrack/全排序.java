package leecode.backtrack;

import java.util.*;

/**
 * @Description
 * 给定一个没有重复数字的序列，返回其所有可能的全排列。
 *
 * 示例:
 *
 * 输入: [1,2,3]
 * 输出:
 * [
 *   [1,2,3],
 *   [1,3,2],
 *   [2,1,3],
 *   [2,3,1],
 *   [3,1,2],
 *   [3,2,1]
 * ]
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/permutations
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 * @Author xuexue
 * @Date 2020/2/12 22:34
 */
public class 全排序 {
    //数据准备
    List<List<Integer>> lists = new ArrayList<>();
    LinkedList<Integer> list = new LinkedList<>();

    public List<List<Integer>> permute(int[] nums) {
        //特判
        if (nums.length == 0) {
            return lists;
        }
        Arrays.sort(nums);
//        Stack<Integer> stack = new Stack<>(nums);
//        List<Integer> liN =
        return null;
    }

    public void backTrack() {

    }
}
