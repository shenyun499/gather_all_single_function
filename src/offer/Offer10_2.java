package offer;

import java.util.HashMap;
import java.util.Map;

/**
 * 剑指 Offer 10- II. 青蛙跳台阶问题
 *
 * 一只青蛙一次可以跳上1级台阶，也可以跳上2级台阶。求该青蛙跳上一个 n 级的台阶总共有多少种跳法。
 *
 * 答案需要取模 1e9+7（1000000007），如计算初始结果为：1000000008，请返回 1。
 *
 * 示例 1：
 * 输入：n = 2
 * 输出：2
 *
 * 示例 2：
 * 输入：n = 7
 * 输出：21
 *
 * 示例 3：
 * 输入：n = 0
 * 输出：1
 * 提示：
 * 0 <= n <= 100
 *
 * 思路：假设n=3时，那有几种选择？可以跳n=1 + n=2的数，为啥？因为选择了1、2，则1到3只有跳2的选择，2跳3只有跳1的选择，两则加起来，不管前面怎么跳，都是n的总次数
 * 递归函数确定：f(n) = f(n - 1) + f(n - 2);
 * 递归结束条件确定：f(1) = 1,f(0) = 1;
 * 注意，不剪枝过不去此题
 *
 * @author ：HUANG ZHI XUE
 * @date ：Create in 2021-01-11
 */
public class Offer10_2 {
    Map<Integer, Integer> map = new HashMap<>();

    public int numWays(int n) {
        if (map.containsKey(n)) {
            return map.get(n);
        }
        if (n == 1) {
            return 1;
        }
        if (n == 0) {
            return 1;
        }
        int value = (numWays(n - 1) + numWays(n - 2)) % 1000000007;
        map.put(n, value);
        return value;
    }
}
