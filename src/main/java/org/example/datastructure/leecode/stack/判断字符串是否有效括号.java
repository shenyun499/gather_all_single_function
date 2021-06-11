package org.example.datastructure.leecode.stack;

import util.HashMap;
import util.Map;
import util.Stack;

/**
 * @Description
 * @Author xuexue
 * @Date 2020/2/820:14
 */
public class 判断字符串是否有效括号 {
    public boolean isValid(String s) {
        Map<Character, Character> hashMap = new HashMap<Character, Character>() {{put('(',')');put('[', ']');put('{','}');put('?','?');}};
        //通过键来操作
        Stack<Character> stack = new Stack<>();
        stack.add('?');
        for (int i = 0; i < s.length(); i++) {
            if (hashMap.containsKey(s.charAt(i))) {
                stack.add(s.charAt(i));
            } else {
                if (hashMap.get(stack.peek()) == s.charAt(i)) {
                    stack.pop();
                } else {
                    return false;
                }
            }
        }
        if (stack.size() == 1) {
            return true;
        }
        return false;
    }
}
