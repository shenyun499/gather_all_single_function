package leecode.array;

import org.junit.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * @Description
 * @Author xuexue
 * @Date 2020/2/99:20
 */
public class 数组中重复的数字 {
    // Parameters:
    //    numbers:     an array of integers
    //    length:      the length of array numbers
    //    duplication: (Output) the duplicated number in the array number,length of duplication array is 1,so using duplication[0] = ? in implementation;
    //                  Here duplication like pointor in C/C++, duplication[0] equal *duplication in C/C++
    //    这里要特别注意~返回任意重复的一个，赋值duplication[0]
    // Return value:       true if the input is valid, and there are some duplications in the array number
    //                     otherwise false
    public static boolean duplicate(int numbers[],int length,int [] duplication) {
        //将数组排序
        Arrays.sort(numbers);
        boolean sign = false;
        int j = 0;

        //遍历数组，并将重复数据填入
        for(int i = 0; i < numbers.length - 1; i++) {
            //出现重复，则存取元素并返回true结束
            if (numbers[i] == numbers[i + 1]) {
                //初始相等时，直接填入
                if (j == 0) {
                    duplication[j++] = numbers[i];
                    sign = true;
                }

                //如果不是初始，则判断duplication数组是否存在数据，存在就不填入；22233 当判断第二个2和第三个2时，检查到duplication存在，所以不填入
                if (j > 0 && duplication[j - 1] != numbers[i]) {
                    duplication[j++] = numbers[i];
                }
            }
        }
        return sign;
    }

    @Test
    public void te() {
        int[] arr = {2, 2, 2, 3, 3};
        int[] a = new int[10];
        boolean duplicate = duplicate(arr, 3, a);
        System.out.println(duplicate);
        for (int i = 0; i < 3; i++) {
            System.out.println(a[i]);
        }
    }
}
