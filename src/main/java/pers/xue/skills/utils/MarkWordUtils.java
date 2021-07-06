package pers.xue.skills.utils;

import org.openjdk.jol.info.ClassLayout;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.locks.LockSupport;

/**
 * @auther huangzhixue
 * @data 2021/7/5 10:52 上午
 * @Description
 */
public class MarkWordUtils {

    public static void main(String[] args) throws InterruptedException {
        Thread.sleep(5000);
        MarkWordUtils markWordUtils = new MarkWordUtils();

        Thread t1 = new Thread(() -> {
            synchronized (markWordUtils) {
                System.out.println("拿到偏向锁");
                System.out.println(ClassLayout.parseInstance(markWordUtils).toPrintable());
            }
            System.out.println("出去t1");
        });
        Thread t2 = new Thread(() -> {
            synchronized (markWordUtils) {
                System.out.println("拿到重偏向锁");
                System.out.println(ClassLayout.parseInstance(markWordUtils).toPrintable());
            }
        });
        t1.start();
        while (t1.isAlive()){};
        t2.start();
    }

    public static void a2(String[] args) throws InterruptedException {
        Thread.sleep(5000);
// 首先我们创建一个list，来存放锁对象

        List<MarkWordUtils> list = new LinkedList<>();

// 线程1

        new Thread(() -> {
            for (int i = 0; i < 50; i++) {
                MarkWordUtils testBiasedLocking = new MarkWordUtils();

                list.add(testBiasedLocking); // 新建锁对象

                synchronized (MarkWordUtils.class) {
                    synchronized (testBiasedLocking) {
                        System.out.println("第" + (i + 1) + "次加锁-线程1");

                        System.out.println(ClassLayout.parseInstance(testBiasedLocking).toPrintable()); // 打印对象头信息

                    }
                }

            }

        }, "线程1").start();

// 让线程1跑一会儿

        Thread.sleep(2000);

// 线程2

        new Thread(() -> {
            for (int i = 0; i < 30; i++) {
                MarkWordUtils testBiasedLocking = list.get(i);

                synchronized (testBiasedLocking) {
                    System.out.println("第" + (i + 1) + "次加锁-线程2");

                    System.out.println(ClassLayout.parseInstance(testBiasedLocking).toPrintable()); // 打印对象头信息

                }

            }

        }, "线程2").start();

        LockSupport.park();

    }
}
