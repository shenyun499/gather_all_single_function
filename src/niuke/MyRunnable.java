package niuke;

/**
 * @Description
 * @Author xuexue
 * @Date 2020/3/211:16
 */
public class MyRunnable {
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
        public synchronized void print1() throws InterruptedException {
            while (true) {
                while (flat != 0) {
                    wait();
                }
                System.out.println("A");
                flat = 1;
                notifyAll();
            }
        }

        public synchronized void print2() throws InterruptedException {
            while (true) {
                while (flat != 1) {
                    wait();
                }
                System.out.println("B");
                flat = 2;
                notifyAll();
            }
        }

        public synchronized void print3() throws InterruptedException {
            while (true) {
                while (flat != 2) {
                    wait();
                }
                System.out.println("C");
                flat = 0;
                notifyAll();
            }
        }
    }
}

