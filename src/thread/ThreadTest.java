package thread;

import org.junit.Test;

/**
 * 线程测试
 *
 * @author ：HUANG ZHI XUE
 * @date ：Create in 2020-09-23
 */
public class ThreadTest {
    static final Object object1 = new Object();
    static final Object object2 = new Object();

    public static void main(String[] args) {
        Thread a = new Thread(() -> {
            synchronized (object1) {
                try {
                    Thread.sleep(2000);
                    synchronized (object2) {
                        System.out.println("拿锁2");
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        Thread b = new Thread(() -> {
            synchronized (object2) {
                try {
                    Thread.sleep(2000);
                    synchronized (object1) {
                        System.out.println("拿锁1");
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                /*if (var == 1) {
                    try {
                        this.wait();
                    } catch (InterruptedException e) {
                        //e.printStackTrace();
                    }
                }
                if (var == 2) {
                    //do something;
                    System.out.println("out");
                }*/
            }

        });
        a.start();
        b.start();
    }


    public volatile static int var = 1;

    @Test
    public void paTest2() {
        Thread a = new Thread(() -> {
            synchronized (object1) {
                try {
                    Thread.sleep(2000);
                    synchronized (object2) {
                        System.out.println("拿锁2");
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        Thread b = new Thread(() -> {
            synchronized (object2) {
                try {
                    Thread.sleep(2000);
                    synchronized (object1) {
                        System.out.println("拿锁1");
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                /*if (var == 1) {
                    try {
                        this.wait();
                    } catch (InterruptedException e) {
                        //e.printStackTrace();
                    }
                }
                if (var == 2) {
                    //do something;
                    System.out.println("out");
                }*/
            }

        });
        a.start();
        b.start();
    }

    @Test
    public void testTrlLength() {
        System.out.println("13511238454".length());
        Runtime.getRuntime();
        System.out.println("00000000454".substring(8));

    }

}
