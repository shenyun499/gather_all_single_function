package org.example.datastructure.musthandwritecode;

/**
 * 死锁代码
 *
 * @author ：HUANG ZHI XUE
 * @date ：Create in 2021-03-11
 */
public class DeadLockCode {
    // 定义两个资源
    private static DeadResource deadResourceA = new DeadResource();
    private static DeadResource deadResourceB = new DeadResource();

    public static void main(String[] args) {

        new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (deadResourceA) {
                    try {
                        System.out.println("线程A拿到deadResourceA锁");
                        Thread.sleep(2000);
                        synchronized (deadResourceB) {
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (deadResourceB) {
                    try {
                        System.out.println("线程A拿到deadResourceA锁");
                        Thread.sleep(2000);
                        synchronized (deadResourceA
                        ) {
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }
}

class DeadResource {}
