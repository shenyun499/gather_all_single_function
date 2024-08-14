package thread;

import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author huangzhixue
 * @date 2024/8/9 15:22
 * @Description
 *
 * 等待所有用户登陆之后，进行压测
 *
 * 发令枪:叫倒计数，允许一个或多个线程等待某些操作完成
 * 是共享Node.SHARED, ReentrantLock是独占Node.EXCLUSIVE
 *
 * 场景：
 * 跑步比赛，裁判需要等到所有的运动员（“其他线程”）都跑到终点（达到目标），才能去算排名和颁奖。
 * 模拟并发，我需要启动100个线程去同时访问某一个地址，我希望它们能同时并发，而不是一个一个的去执行
 *
 *
 * 比较栅栏CyclicBarrier
 * 1.CountDownLatch是不可以重置的，所以无法重用，CyclicBarrier没有这种限制，可以重用。
 * 2.CountDownLatch的基本操作组合是countDown/await，调用await的线程阻塞等待countDown足够的次数，
 * 不管你是在一个线程还是多个线程里countDown，只要次数足够即可。CyclicBarrier的基本操作组合就是await，
 * 当所有的伙伴都调用了await，才会继续进行任务，并自动进行重置。
 * 3.CountDownLatch目的是让一个线程等待其他N个线程达到某个条件后，自己再去做某个事（通过CyclicBarrier的第二个构造方法
 * public CyclicBarrier(intparties,RunnablebarrierAction)，在新线程里做事可以达到同样的效果）。而CyclicBarrier的目的是让N多
 * 线程互相等待直到所有的都达到某个状态，然后这N个线程再继续执行各自后续（通过CountDownLatch在某些场合也能完成类似的效果）。
 */
public class CountDownLatchTest {

    public static void main(String[] args) {
        CountDownLatch countDownLatch = new CountDownLatch(5);
        for (int i = 0; i < 5; i++) {
            new UserLoginTask(countDownLatch).start();
        }
        try {
            countDownLatch.await();
            // performance test. 伪代码
            System.out.println("用户登陆完毕，压测开始");
        } catch (InterruptedException e) {
            System.out.println("压测异常");
        }
    }
}

class UserLoginTask extends Thread {
    private CountDownLatch countDownLatch;
    UserLoginTask(CountDownLatch countDownLatch) {
        this.countDownLatch = countDownLatch;
    }
    @Override
    public void run() {
        try {
            // login -- 睡眠代替 伪代码
            Thread.sleep(new Random(1).nextInt(10000));
            System.out.println(Thread.currentThread().getName() + "用户登陆");
        } catch (InterruptedException e) {
            System.out.println("登陆异常");
        } finally {
            countDownLatch.countDown();
        }

    }
}
