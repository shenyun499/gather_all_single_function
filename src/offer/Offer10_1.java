package offer;

import java.util.HashMap;
import java.util.Map;

/**
 * 剑指 Offer 10- I. 斐波那契数列
 *
 * 写一个函数，输入 n ，求斐波那契（Fibonacci）数列的第 n 项。斐波那契数列的定义如下：
 *
 * F(0) = 0,   F(1) = 1
 * F(N) = F(N - 1) + F(N - 2), 其中 N > 1.
 * 斐波那契数列由 0 和 1 开始，之后的斐波那契数就是由之前的两数相加而得出。
 *
 * 答案需要取模 1e9+7（1000000007），如计算初始结果为：1000000008，请返回 1。
 *
 *  
 *
 * 示例 1：
 * 输入：n = 2
 * 输出：1
 *
 * 示例 2：
 * 输入：n = 5
 * 输出：5
 *
 * 提示：
 * 0 <= n <= 100
 *
 * 思路1：采用递归方式完成，或者动态规划，此种解法时间太长。
 * 1、确定递归结束条件：F(0) = 0,   F(1) = 1，即if (n == 0) return 0; if (n == 1) return 1;
 * 2、确定递归函数表达式：F(N) = F(N - 1) + F(N - 2), 其中 N > 1，即fib(n - 1) + fib(n - 2)
 *
 * 思路2：采用递归，但是需要进行剪枝，怎么剪枝，就是通过map集合将已经计算过的结果存储起来，如果遇到已经计算过直接返回结果
 *
 * @author ：HUANG ZHI XUE
 * @date ：Create in 2021-01-08
 */
public class Offer10_1 {
    Map<Integer, Integer> map = new HashMap<>();
    public int fib(int n) {
        // 如果集合包含，则直接返回值
        if (map.containsKey(n)) {
            return map.get(n);
        }
        if (n == 0) {
            return 0;
        }
        if (n == 1) {
            return 1;
        }
        // 不包含，则进行递归操作
        int val = (fib(n - 1) + fib(n - 2)) % 1000000007;
        // 记录操作过的n值
        map.put(n, val);
        return val;
    }

/*    public int fi(int n) {
        // 递归结束条件
        if (n == 0) {
            return 0;
        }
        if (n == 1) {
            return 1;
        }
        // 递归函数
        return (fib(n - 1) + fib(n - 2)) % 1000000007;
    }*/
}
