package thread;

import java.util.Random;
import java.util.concurrent.Semaphore;

/**
 * @author huangzhixue
 * @date 2024/8/9 15:41
 * @Description -- 停车场使用案例代码
 * 信号量
 * 1.概念：计数信号量，JDK1.5引入，作用是可以控制同时访问资源的线程个数。
 * 比如一共有5台电脑，有10名考生，那么同时考生的只能5名，如果有一名考生考完了，剩下的5名考生就可以再上一名考生，依次下去，直到所有考生考完。
 *
 * 2.工作原理：
 * 信号量中有n个初始化指定的令牌，每个线程执行任务需要调用acquire()获取到一块令牌才能执行，最多允许n个线程同时执行任务，其它线程阻塞在那里等待获取令牌，当线程执行完任务调用release()方法将令牌释放，这时候其他线程就可以获取令牌了。
 *
 * 3.案例：停车场使用案例
 *
 * 4.使用场景
 * 用作限流，控制同时访问资源的线程数量或者用户数量等
 *
 * 是共享Node.SHARED, ReentrantLock是独占Node.EXCLUSIVE
 *
 */
public class SemaphoreTest {
    public static void main(String[] args) {
        // 五个车位
        int parkNumber = 5;
        Semaphore semaphore = new Semaphore(parkNumber, true);

        // 十辆车排队进场
        int carNumber = 10;

        for (int i = 1; i <= carNumber; i++) {
            ParkingTask parkingTask = new ParkingTask(semaphore, i);
            new Thread(parkingTask).start();
        }
    }
}
class ParkingTask implements Runnable {

    /**
     * 标识第几量车
     */
    private int num;
    private Semaphore semaphore;

    public ParkingTask(Semaphore semaphore, int num) {
        this.semaphore = semaphore;
        this.num = num;
    }
    @Override
    public void run() {
        try {
            semaphore.acquire();
            System.out.println(num + "车进场");
            // 假设车辆在里面待10s
            Thread.sleep(new Random(1).nextInt(100000));
            semaphore.release();
            System.out.println(num + "车出场");
        } catch (InterruptedException e) {
            System.out.println("获取车位异常");
        }
    }
}
