package org.example.datastructure.leecode.digui;

import util.Stack;

/**
 * @Description
 * @Author xuexue
 * @Date 2020/2/820:00
 */
public class 最长连续子串 {
    public static int longestValidParentheses(String s) {
        if (s.isEmpty()) {
            return 0;
        }
        int max = 0;
        Stack<Integer> stack = new Stack<>();
        stack.push(-1);
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == '(') {
                stack.push(i);
            } else {
                stack.pop();
                if (stack.isEmpty()) {
                    stack.push(i);
                } else {
                    max = Math.max(max, i - stack.peek());
                }
            }
        }
        return max;
    }
}
