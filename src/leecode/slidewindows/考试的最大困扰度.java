package leecode.slidewindows;

/**
 * @author huangzhixue
 * @date 2024/9/2 14:46
 * @Description
 * 滑动窗口
 * 定义left,right
 * 如果中间小于k个变种，滑动右窗口，边滑动边记录变种数，最大max
 * 中间最大只能有k个变种，如果大于k，则需要滑动左边的窗口指针
 *
 * 一位老师正在出一场由 n 道判断题构成的考试，每道题的答案为 true （用 'T' 表示）或者 false （用 'F' 表示）。老师想增加学生对自己做出答案的不确定性，方法是 最大化 有 连续相同 结果的题数。（也就是连续出现 true 或者连续出现 false）。
 *
 * 给你一个字符串 answerKey ，其中 answerKey[i] 是第 i 个问题的正确结果。除此以外，还给你一个整数 k ，表示你能进行以下操作的最多次数：
 *
 * 每次操作中，将问题的正确答案改为 'T' 或者 'F' （也就是将 answerKey[i] 改为 'T' 或者 'F' ）。
 * 请你返回在不超过 k 次操作的情况下，最大 连续 'T' 或者 'F' 的数目。
 *
 *
 *
 * 示例 1：
 *
 * 输入：answerKey = "TTFF", k = 2
 * 输出：4
 * 解释：我们可以将两个 'F' 都变为 'T' ，得到 answerKey = "TTTT" 。
 * 总共有四个连续的 'T' 。
 */
public class 考试的最大困扰度 {
    public int maxConsecutiveAnswers(String answerKey, int k) {
        return Math.max(maxLength(answerKey, k, 'T'), maxLength(answerKey, k, 'F'));
    }
    public int maxLength(String answerKey, int k, char ch) {
        int ans = 0;
        for (int sum = 0, left = 0, right = 0; right < answerKey.length(); right++) {
            sum += answerKey.charAt(right) != ch ? 1 : 0;
            // 中间最大只能有k个变种，如果大于k，则需要滑动左边的窗口指针
            while (sum > k) {
                sum -= answerKey.charAt(left++) != ch ? 1 : 0;
            }
            ans = Math.max(ans, right - left + 1);
        }
        return ans;
    }
}
