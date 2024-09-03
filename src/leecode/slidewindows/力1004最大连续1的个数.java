package leecode.slidewindows;

/**
 * @author huangzhixue
 * @date 2024/9/2 15:37
 * @Description
 * 给定一个二进制数组 nums 和一个整数 k，如果可以翻转最多 k 个 0 ，则返回 数组中连续 1 的最大个数 。
 *
 *
 *
 * 示例 1：
 *
 * 输入：nums = [1,1,1,0,0,0,1,1,1,1,0], K = 2
 * 输出：6
 * 解释：[1,1,1,0,0,1,1,1,1,1,1]
 * 粗体数字从 0 翻转到 1，最长的子数组长度为 6。
 * 示例 2：
 *
 * 输入：nums = [0,0,1,1,0,0,1,1,1,0,1,1,0,0,0,1,1,1,1], K = 3
 * 输出：10
 * 解释：[0,0,1,1,1,1,1,1,1,1,1,1,0,0,0,1,1,1,1]
 * 粗体数字从 0 翻转到 1，最长的子数组长度为 10。
 */
public class 力1004最大连续1的个数 {
    public int longestOnes(int[] nums, int k) {
        int longestOnes = 0;
        for (int sum = 0, left = 0, right = 0;right < nums.length; right++) {
            sum += nums[right] != 1 ? 1 : 0;
            // 刚好找到第三个0，则开始滑动左边窗口
            while (sum > k) {
                sum -= nums[left++] != 1 ? 1 : 0;
            }
            longestOnes = Math.max(longestOnes, right - left + 1);
        }
        return longestOnes;
    }
}
