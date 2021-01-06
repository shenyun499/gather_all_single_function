package offer;

/**
 * 剑指Offer04题
 * 在一个 n * m 的二维数组中，每一行都按照从左到右递增的顺序排序，每一列都按照从上到下递增的顺序排序。
 * 请完成一个高效的函数，输入这样的一个二维数组和一个整数，判断数组中是否含有该整数。
 *
 * 示例:
 * 现有矩阵 matrix 如下：
 * [
 *   [1,   4,  7, 11, 15],
 *   [2,   5,  8, 12, 19],
 *   [3,   6,  9, 16, 22],
 *   [10, 13, 14, 17, 24],
 *   [18, 21, 23, 26, 30]
 * ]
 *
 * 给定 target = 5，返回 true。
 * 给定 target = 20，返回 false。
 *
 * 限制：
 * 0 <= n <= 1000
 * 0 <= m <= 1000
 *
 * 思路：从左下角开始，如果target > 它，则列数j+1，小于则行数i-1，否则相等返回true
 * @author ：HUANG ZHI XUE
 * @date ：Create in 2021-01-05
 */
public class Offer04 {
    public static void main(String[] args) {
    }

    public boolean findNumberIn2DArray(int[][] matrix, int target) {
        // 定位初始值
        int i = matrix.length - 1, j = 0;
        for (; i >= 0 && j <= matrix[0].length - 1;) {
            // target大，则列数+1
            if (target > matrix[i][j]) {
                j++;
            } else if (target < matrix[i][j]) {
                // target小，则行数-1
                i--;
            } else {
                return true;
            }
        }
        return false;
    }
}
