package musthandwritecode;

/**
 * 两个线程之间的通讯
 *
 * @author ：HUANG ZHI XUE
 * @date ：Create in 2021-03-11
 */
public class TwoThreadCommunication {
    private static Deal deal = new Deal();
    public static void main(String[] args) {
        Thread thread1 = new Thread(() -> {
            deal.consumer1();
        });
        Thread thread2 = new Thread(() -> {
            deal.consumer2();
        });
        thread1.setName("消费者1线程");
        thread2.setName("消费者2线程");
        thread1.start();
        thread2.start();
    }
}

// 线程需要处理的类，消费者1和消费者2轮流消费，根据线程通知的模式进行
class Deal {
    private int flag = 1;
    public void consumer1() {
        while (true) {
            synchronized (this) {
                if (flag != 1) {
                    try {
                        wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                System.out.println("消费者1消费完成了，通知消费者2去消费");
                flag = 2;
                this.notify();
            }
        }
    }
    public void consumer2() {
        while (true) {
            synchronized (this) {
                if (flag != 2) {
                    try {
                        wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                System.out.println("消费者2消费完成了，通知消费者1去消费");
                flag = 1;
                this.notify();
            }
        }
    }
}