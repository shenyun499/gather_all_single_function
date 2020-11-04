package leecode.stack;

import java.util.Arrays;
import java.util.Stack;

/**
 * @Description
 * 给出由小写字母组成的字符串 S，重复项删除操作会选择两个相邻且相同的字母，并删除它们。
 * 在 S 上反复执行重复项删除操作，直到无法继续删除。
 * 在完成所有重复项删除操作后返回最终的字符串。答案保证唯一。
 *
 * @Author xuexue
 * @Date 2019/12/10 20:49
 */
public class 删除字符串中的所有相邻重复项 {
    public static void main(String[] args) {
        String strs = removeDuplicates("aabbbcccadbdbs");
        System.out.println(strs);

    }


    public static String removeDuplicates1(String S) {
        //创建栈
        Stack<Character> stack = new Stack<>();

        //将字符串转为字符数组
        char[] chars = S.toCharArray();

        for (char aChar : chars) {
            if (!stack.isEmpty()) {
                //当栈顶的元素不等于数组元素时，入栈；否则出栈
                if (aChar != stack.peek())
                    stack.push(aChar);
                else
                    stack.pop();
            } else {
                stack.push(aChar);
            }
        }
        char[] newChars = new char[stack.size()];
        for (int i = newChars.length - 1; i >= 0; i--) {
            newChars[i] = stack.pop();
        }

        return String.valueOf(newChars);
    }

    public static String removeDuplicates(String S) {
        StringBuffer sf = new StringBuffer();
        int sbLength = 0;
        for (char character : S.toCharArray()) {
            if (sbLength != 0 && character == sf.charAt(sbLength - 1)) {
                sf.deleteCharAt(sbLength-- - 1);
            } else {
                sf.append(character);
                sbLength++;
            }

        }
        return sf.toString();
    }
}
