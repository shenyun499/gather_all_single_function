package leecode;

import java.util.Stack;

/**
 * @Description
 *
 * 给定一个从1 到 n 排序的整数列表。
 * 首先，从左到右，从第一个数字开始，每隔一个数字进行删除，直到列表的末尾。
 * 第二步，在剩下的数字中，从右到左，从倒数第一个数字开始，每隔一个数字进行删除，直到列表开头。
 * 我们不断重复这两步，从左到右和从右到左交替进行，直到只剩下一个数字。
 * 返回长度为 n 的列表中，最后剩下的数字。
 * @Author xuexue
 * @Date 2020/1/9 18:53
 */
public class 算法1 {

    public static void main(String[] args) {
        //int i = lastRemaining(9);
        //System.out.println(i);
    }

    public int lastRemaining(int n) {
        if (n == 1) {
            return 1;
        }
        int j = 1;
        //栈1、栈2交互数据
        Stack<Integer> stack = new Stack<>();
        Stack<Integer> stack2 = new Stack<>();

        //将数据倒序放入栈中
        for (int i = n; i > 0; i--) {
            stack.push(i);
        }
        boolean isS = true;

        while (!stack.isEmpty() || !stack2.isEmpty()) {
            if (isS) {
                if ((j & 1) == 1) {
                    stack.pop();
                    j++;
                } else {
                    stack2.push(stack.pop());
                    j++;
                }
                if (stack.isEmpty()) {
                    if (stack2.size() == 1) {
                        return stack2.pop();
                    }
                    j = 1;
                    isS = !isS;
                }
            }

            if (!isS) {
                if ((j & 1) == 1) {
                    stack2.pop();
                    j++;
                } else {
                    stack.push(stack2.pop());
                    j++;
                }
                if (stack2.isEmpty()) {
                    if (stack.size() == 1) {
                        return stack.pop();
                    }
                    j = 1;
                    isS = !isS;
                }
            }

        }
        return stack.pop();
    }
}
