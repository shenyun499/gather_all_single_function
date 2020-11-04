package enumtest;

import org.junit.Test;

/**
 *
 * 枚举类
 */
enum  EnumTests {
    MONDAY,TUESDAY,WEDNESDAY,THURSDAY,FRIDAY,SATURDAY,SUNDAY;
}

public class EnumTest {
    public static void main(String[] args) {
        //3、4、5站点
        int[][] arr = {{-5, 0}, {0, 0}, {5, 0}};
        double max = remote(arr[0][0], arr[0][1], 2, 2);
        int x = arr[0][0], y = arr[0][1];
        for(int i = 1; i < arr.length; i++) {
            double remote = remote(arr[i][0], arr[i][1], 2, 2);
            if (max < remote) {
                max = remote > max ? remote : max;
                //刷新最大距离的站点
                x = arr[0][0];
                y = arr[0][1];
            }

        }
        System.out.println("x:" + x + ",y:" + y);
    }

    /**
     * @param a 站点x轴大小
     * @param b 站点y轴大小
     * @param x 新发地x轴大小
     * @param y 新发地y轴大小
     * @return 站点到新发地距离
     */
    public static double remote(int a, int b, int x, int y) {
        //站点距离新发地x轴距离
        int x1 = a > x ? a - x : x - a;
        //站点距离新发地y轴距离
        int y1 = b > y ? b - y : y - b;
        //站点距离新发地距离
        double remote = Math.pow(x1, y1);
        return remote;
    }
}

