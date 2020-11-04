package niuke;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Description
 * @Author xuexue
 * @Date 2020/3/211:32
 */
public class MyReentLock {
    public static void main(String[] args) {
        Printer printer = new Printer();

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    printer.print1();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    printer.print2();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    printer.print3();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
    private static class Printer {
        private int flat = 0;
        private ReentrantLock rlock = new ReentrantLock();
        Condition c1 = rlock.newCondition();
        Condition c2 = rlock.newCondition();
        Condition c3 = rlock.newCondition();
        public void print1() throws InterruptedException {
            while (true) {
                rlock.lock();
                while (flat != 0) {
                    c1.await();
                }
                System.out.println("A");
                flat = 1;
                c2.signal();
                rlock.unlock();
            }
        }

        public void print2() throws InterruptedException {
            while (true) {
                rlock.lock();
                while (flat != 1) {
                    c2.await();
                }
                System.out.println("B");
                flat = 2;
                c3.signal();
                rlock.unlock();
            }
        }

        public void print3() throws InterruptedException {
            while (true) {
                rlock.lock();
                while (flat != 2) {
                    c3.await();
                }
                System.out.println("C");
                flat = 0;
                c1.signal();
                rlock.unlock();
            }
        }
    }
}
