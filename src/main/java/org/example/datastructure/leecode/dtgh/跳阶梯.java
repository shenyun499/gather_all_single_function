package org.example.datastructure.leecode.dtgh;

/**
 * @Description
 * @Author xuexue
 * @Date 2020/2/819:57
 */
public class 跳阶梯 {
    //动态规划 -- 1、最优解子结构   2、边界值 3、状态转移公式
    //1、最优解子结构分析出一个关系 f(10)=f(9)+(8),这个f(9)、(8)就是最优解子结构
    //2、边界值，这里1和2就是边界值
    //3、最关键：状态转移公式，这里就是f(n)=f(n-1)+f(n-2)
    public static int next3(int n) {
        if (n == 1) {
            return 1;
        }
        if (n == 2) {
            return 2;
        }
        int a = 1;
        int b = 2;
        for (int i = 3; i < n; i++) {
            int temp = a + b;
            a = b;
            b = temp;
        }
        return a + b;
    }
}
