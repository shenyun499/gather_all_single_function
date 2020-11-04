package leecode;

/**
 * @Description
 * @Author xuexue
 * @Date 2020/1/7 22:49
 */
public class 回文数 {
    public boolean isPalindrome(int x) {

        //小于0和以0结尾的数字都不是回文数
        if (x == 0) {
            return true;
        } else if (x < 0 || x % 10 == 0){
            return false;
        } else {
            int temp = x;
            int res = 0, num = 0;
            while (temp != 0) {
                num = temp % 10;
                temp /= 10;
                res = res * 10 + num;
            }
            return res == x;
        }
    }
}
