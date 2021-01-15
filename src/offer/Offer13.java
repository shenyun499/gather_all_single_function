package offer;

/**
 * 剑指 Offer 13. 机器人的运动范围
 * 地上有一个m行n列的方格，从坐标 [0,0] 到坐标 [m-1,n-1] 。一个机器人从坐标 [0, 0] 的格子开始移动，它每次可以向左、右、上、下移动一格（不能移动到方格外），也不能进入行坐标
 * 和列坐标的数位之和大于k的格子。例如，当k为18时，机器人能够进入方格 [35, 37] ，因为3+5+3+7=18。但它不能进入方格 [35, 38]，因为3+5+3+8=19。请问该机器人能够到达多少个格子？
 *
 * 示例 1：
 * 输入：m = 2, n = 3, k = 1
 * 输出：3
 *
 * 示例 2：
 * 输入：m = 3, n = 1, k = 0
 * 输出：1
 * 提示：
 * 1 <= n,m <= 100
 * 0 <= k <= 20
 *
 * 数位之和这个坑..
 *
 *
 * @author ：HUANG ZHI XUE
 * @date ：Create in 2021-01-15
 */
public class Offer13 {
    public int movingCount(int m, int n, int k) {
        int[][] arr = new int[m][n];
        return dnf(m, n, 0, 0, k, arr);
    }

    private int dnf(int m, int n, int i, int j, int k, int[][] arr) {
        // 递归结束条件（行+列>k 、不能超出空格外）
        if (i < 0 || i > m - 1 || j < 0 || j > n - 1 || i % 10 + j % 10 > k || arr[i][j] == 1) {
            return 0;
        }
        // 记录是否走过
        arr[i][j] = 1;

        // 递归函数
        int count = dnf(m, n, i + 1, j, k, arr) + dnf(m, n, i - 1, j, k, arr)
                + dnf(m, n, i, j + 1, k, arr) + dnf(m, n, i, j - 1, k, arr);
        // 递归结束时，恢复原始值
        arr[i][j] = 0;
        // 递归返回值
        return count + 1;
    }

}
