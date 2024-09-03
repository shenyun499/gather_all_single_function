package thread;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author huangzhixue
 * @date 2024/8/14 17:08
 * @Description
 */
public class SomeTest {
    public static void main(String[] args) throws InterruptedException {
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(5, 6, 1, TimeUnit.HOURS, new LinkedBlockingQueue<>(5));
        threadPoolExecutor.execute(() -> {
            try {
                Thread.sleep(20000);
            } catch (InterruptedException e) {
            }
            System.out.println("thread end");
        });
        Thread.sleep(30000);
        threadPoolExecutor.execute(() -> {
            try {
                Thread.sleep(20000);
                System.out.println("thread end");
            } catch (InterruptedException e) {
            }
        });

//        for (int i = 0; i < str.length(); i++) {
//            // a = 97
//            int index = str.charAt(i) - 97;
//            nums[index]++;
//            if (nums[index] > 1) {
//                System.out.println("存在重复");
//                break;
//            }
//        }
    }


}


