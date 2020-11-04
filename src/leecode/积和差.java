package leecode;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description
 * 输入：n = 234
 * 输出：15
 * 解释：
 * 各位数之积 = 2 * 3 * 4 = 24
 * 各位数之和 = 2 + 3 + 4 = 9
 * 结果 = 24 - 9 = 15
 *
 * @Author xuexue
 * @Date 2020/1/414:12
 */
public class 积和差 {

    public int subtractProductAndSum(int n) {
        //乘积
        int num1 = 1;
        //和
        int num2 = 0;

        //list存放数字各个数
        List<Integer> list = new ArrayList<>();
        int temp = n;

        //得到一个数字的各个数，存在list中
        while (temp / 10 > 0) {
            list.add(temp % 10);
            temp = temp / 10;
        }
        list.add(temp);

        for (Integer value : list) {
            num1 *= value;
            num2 += value;
        }
        return num1 - num2;
    }
}
