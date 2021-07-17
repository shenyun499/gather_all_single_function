package musthandwritecode;

/**
 * 死锁代码
 *
 * @author ：HUANG ZHI XUE
 * @date ：Create in 2021-03-11
 */
public class DeadLockCode {
    // 定义两个资源
    private static DeadResourceA deadResourceA = new DeadResourceA();
    private static DeadResourceB deadResourceB = new DeadResourceB();

    public static void main(String[] args) {

        new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (deadResourceA) {
                    try {
                        System.out.println("线程1拿到deadResourceA锁，等待获取deadResourceB");
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
                    synchronized (DeadLockCode.class) {
                        try {
                            System.out.println("线程2拿到deadResourceB锁，等待获取deadResourceA");
                            Thread.sleep(2000);
                            synchronized (deadResourceA) {
                            }
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }).start();

        new Thread(() -> {
            while (true) {}
        }).start();
    }
}

class DeadResourceA {
}

class DeadResourceB {
}
