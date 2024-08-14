package thread;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * @author huangzhixue
 * @date 2024/8/13 09:44
 * @Description
 * 例子：等待所有学生到了才进食堂吃饭
 *
CyclicBarrier叫循环栅栏，它实现让一组线程等待至某个状态之后再全部同时执行，而且当所有等待线程被释放后，CyclicBarrier可以被
重复使用。CyclicBarrier的典型应用场景是用来等待并发线程结束。CyclicBarrier的主要方法是await()，await()每被调用一次，计数便
会减少1，并阻塞住当前线程。当计数减至0时，阻塞解除，所有在此CyclicBarrier上面阻塞的线程开始运行。
在这之后，如果再次调用await()，计数就又会变成N-1，新一轮重新开始，这便是Cyclic的含义所在。CyclicBarrier.await()带有返回
值，用来表示当前线程是第几个到达这个Barrier的线程。
 */
public class CyclicBarrierTest {

    public static void main(String[] args) {
        CyclicBarrier cyclicBarrier = new CyclicBarrier(5);
        for (int i = 0; i < 5; i++) {
            new Thread(new Student(cyclicBarrier)).start();
        }
    }

}

class Student implements Runnable {
    private CyclicBarrier cyclicBarrier;

    public Student(CyclicBarrier cyclicBarrier) {
        this.cyclicBarrier = cyclicBarrier;
    }

    @Override
    public void run() {
        try {
            // 学生需要等待2s到
            Thread.sleep(2000);
            // 先到的学生等待
            cyclicBarrier.await();
        } catch (InterruptedException | BrokenBarrierException e) {
            e.printStackTrace();
        }
        // 所有学生到了开始进食堂吃饭
        System.out.println(Thread.currentThread().getName() + "进食堂");
    }
}
