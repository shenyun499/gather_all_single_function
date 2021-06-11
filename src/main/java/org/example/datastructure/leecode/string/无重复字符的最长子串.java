package org.example.datastructure.leecode.string;

import util.HashMap;
import util.HashSet;
import util.Map;
import util.Set;

/**
 * @Description
 * @Author xuexue
 * @Date 2019/12/12 10:55
 */
public class 无重复字符的最长子串 {
    public static void main(String[] args) {
        int maxLength = lengthOfLongestSubstring("aab");
        System.out.println(maxLength);
    }

    public static int lengthOfLongestSubstring(String s) {
        //记录最大长度
        int maxLength = 0;
        //创建Set记录字符
        HashSet<Character> hashSet = new HashSet<>();
        //记录位置
        int place = 0;
        //将s转成字符数组
        char[] chars = s.toCharArray();
        //遍历字符，并将字符存入hashmap。如果遇到相同key，则返回长度，将重复的key及它之前的元素去掉，重新入值字符
        for (int i = 0; i < chars.length; i++) {
            //如果遇到相同key，则返回长度，将重复的key及它之前的元素去掉，重新记录最大不重复子串长度
            if (!hashSet.isEmpty() && hashSet.contains(chars[i])) {
                //将重复的key及它之前的元素去掉
                for (; place < i; place++) {
                    if (chars[place] == chars[i]) {
                        hashSet.remove(chars[place]);
                        place++;
                        break;
                    }
                    hashSet.remove(chars[place]);
                }

            }
            //字符存入hashmap
            hashSet.add(chars[i]);
            //子串比之前的子串还要长，则重新计算
            maxLength = hashSet.size() > maxLength ? hashSet.size() : maxLength;
        }
        //返回长度
        if (maxLength == 0) {
            return 0;
        }
        return maxLength;
    }

    //滑动窗口
    public class Solution {
        public int lengthOfLongestSubstring(String s) {
            //滑动窗口辅助变量
            int i = 0, j = 0;
            //最大值
            int maxValue = 0;
            //set集合判断重复
            Set<Character> set = new HashSet<>();

            while (i < s.length() && j < s.length()) {
                if (!set.contains(s.charAt(j))) {
                    //添加
                    set.add(s.charAt(j++));
                    //取最大值
                    maxValue = Math.max(maxValue, j - i);//abca 3
                } else {
                    //删除一个元素，滑动一个距离
                    set.remove(s.charAt(i++));
                }
            }
            return maxValue;
        }
    }

}
