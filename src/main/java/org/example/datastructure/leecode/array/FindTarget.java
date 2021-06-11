package org.example.datastructure.leecode.array;
import org.junit.Test;
/**
 * @Description
 * 思路：
 * 1、由题目得：连续正数数列，至少有两个数
 * 2、用双指针实现，定义双指针left，right，赋值上正数最小值left=1,right=2
 * 3、定义sum值，记录left-right的sum，如果sum<target，right++,sum+right
 * 4、sum>target等于则输出这一段值，sum-left,left++
 * 5、sum==target等于则输出这一段值，left++,right++
 * 6、结束条件：因为至少是两个数，所以当left>target/2时，就可以结束查找
 * @Author xuexue
 * @Date 2020/3/2 14:35
 */
public class FindTarget {
    @Test
    public void solution() {
        int left = 1, right = 2;
        //定义sum值，记录left--right区间的和
        int sum = left + right;

        //target值
        int target = 15;

        while (left <= (target >> 1)) {
            //sum<target，right++,sum+right
            if (sum < target) {
                right++;
                sum += right;
            } else if (sum > target) {
                //sum>target等于则输出这一段值，sum-left,left++
                sum -= left;
                left++;
            } else {
                //sum==target等于则输出这一段值，left++,right++
                print(left, right);
                sum -= left;
                left++;
            }
        }
    }
    /**
     * 输出left---right的区间全部整数
     * @param left
     * @param right
     */
    public void print(int left, int right) {
        for (; left <= right; left++) {
            System.out.print(left + " ");
        }
        System.out.println();
    }
}
