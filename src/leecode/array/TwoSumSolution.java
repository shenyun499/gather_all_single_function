package leecode.array;
import org.junit.Test;
import java.util.*;
/**
 * @Description
 * 两数之和
 * 思路：
 * 特殊处理，如果数组长度小于2，则直接返回-1，-1
 * 1、有序数组，选择双指针解法，定义left、right双指针，进行求解
 * 2、如果a1+a2<target，那么left右移加1
 * 3、如果a1+a2>target，那么right左移减1
 * 4、如果相等，记录两个数（创建一个容器记录），并且左右指针同时向中间缩进
 * 5、结束条件 left>right
 * 算法时间复杂度：O(n)
 * @Author xuexue
 * @Date 2020/3/2 14:15
 */
public class TwoSumSolution {
    @Test
    public void solution() {
        //输入处理，这里我选择了用直接赋值，没有做输入操作
        int[] nums = new int[] {1,2,4,7,8,11,15};
        int target = 15;
        //定义一个容器存放存在的目标值
        List<List<Integer>> list = new ArrayList<>(nums.length >> 2);
        //特殊处理
        if (nums.length < 2) {
            list.add(new ArrayList<>(Arrays.asList(-1, -1)));
        }
        //逻辑处理
        findTargetValue(nums, list, target);
        System.out.println(list);
    }

    /**
     * 求两数之和等于目标值，存放于容器中
     * @param nums 有序数组
     * @param list 存放两数之和等于目标值的两数，容器
     * @param target 目标值
     */
    public void findTargetValue(int[] nums, List<List<Integer>> list, int target) {
        //定义双指针
        int left = 0, right = nums.length - 1;
        while (left < right) {
            int sum = nums[left] + nums[right];
            if (sum < target) {
                left++;
            } else if (sum > target) {
                right--;
            } else {
                //找到两数之和等于目标值，将两数加入容器中
                list.add(new ArrayList<>(Arrays.asList(nums[left], nums[right])));
                left++;
                right--;
            }
        }
    }
}
