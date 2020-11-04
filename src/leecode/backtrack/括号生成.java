package leecode.backtrack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @Description
 * 给出 n 代表生成括号的对数，请你写出一个函数，使其能够生成所有可能的并且有效的括号组合。
 *
 * 例如，给出 n = 3，生成结果为：
 *
 * [
 *   "((()))",
 *   "(()())",
 *   "(())()",
 *   "()(())",
 *   "()()()"
 * ]
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/generate-parentheses
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 *
 * @Author xuexue
 * @Date 2020/2/12 9:27
 */
public class 括号生成 {

    List<String> list = new ArrayList<>();
    public List<String> generateParenthesis(int n) {
        backTrack("", n, n);
        return list;
    }

    //深度优先遍历
    public void backTrack(String str, int left, int right) {
        //递归结束条件
        if (left == 0 && right == 0) {
            list.add(str);
            return;
        }

        //剪枝
        if (left > right) {
            return;
        }

        //因为是字符串，每次都会创建新的串，所以不需要回溯
        if (left > 0) {
            backTrack(str + "(", left - 1, right);
        }
        if (right > 0) {
            backTrack(str + ")", left, right - 1);
        }
    }


    public void te(int[] arr) {
        //1、排序--得到有序数组
        Arrays.sort(arr);
        //2、特判
        if (arr[0] > 0) return;
        //3、进行查找，边去重，选定一个值，其它两个双指针（因为数组有序）
        for (int i = 0; i < arr.length; i++) {
            //首先首字去重
            if (i != 0 && arr[i] == arr[i - 1]) {
                continue;
            }
            //双指针判断三数和，并且去重 如33右指针则连续左移两位，左则连续右移两位
            //过程...

        }
    }

}
