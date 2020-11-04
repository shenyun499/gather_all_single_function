package leecode.array;

/**
 * @Description
 * @Author xuexue
 * @Date 2020/2/820:30
 */
public class 最长子前缀 {
    /**
     * 动态规划解
     * @param strs
     * @return
     */
    public String longestCommonPrefix(String[] strs) {
        //长度为0直接返回
        if (strs == null || strs.length == 0) {
            return "";
        }

        //HashSet<Character> set = new HashSet<>();
        int min = strs[0].length();
        //求出最小串长度
        for (int i = 1; i < strs.length; i++) {
            if (min > strs[i].length()) {
                min = strs[i].length();
            }
        }
        //按照最小的数遍历值
        for(int i = 0; i < min; i++) {
            for (int j = 1; j < strs.length; j++) {
                if (strs[0].charAt(i) != strs[j].charAt(i)) {
                    return i == 0 ? "" : strs[j].substring(0, i);
                }
            }
        }
        return strs[0].substring(0, min);
    }
}
