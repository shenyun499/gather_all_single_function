package leecode.twozhizhen;

/**
 * @Description
 * @Author xuexue
 * @Date 2020/2/820:21
 */
public class 容器最大水 {
    public int maxArea(int[] height) {
        //容器两边
        int i = 0, j = height.length - 1;
        int maxArea = 0;

        while (i <= j) {
            //记录最大，面积
            maxArea = Math.max(Math.min(height[i], height[j]) * (j - i), maxArea);

            //哪边高度小就往相移动
            int a = (height[i] < height[j]) ? i++ : j--;
        }
        return maxArea;
    }
}
