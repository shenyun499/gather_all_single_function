package org.example.datastructure.thread;

import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import util.concurrent.*;

/**
 * 线程中断
 * 1、当一个队列里面的任务需要被清理时，可以通过t.interrupt()，抛出异常，在捕获那里做资源清理，队列出队处理
 * 2、当一个运行中的任务，我们需要取消这个任务时
 *
 * @author ：HUANG ZHI XUE
 * @date ：Create in 2021-04-01
 */
public class ThreadInterupt {

    private volatile static boolean IS_STOP;

    /**
     * 在进程sleep时 去中断它
     *
     * @param args
     * @throws InterruptedException
     */
//    public static void main(String[] args) throws InterruptedException {
//        在Thread类提供interrupt()方法，用于中止正在被阻塞的任务。当打断一个正在被阻塞的任务时，通常还需要清理资源，这正如处理抛出异常，
//        由于有这种需求存在，Java在设计阻塞时中断方法时，即在调用interrupt()设置线程为中断状态后，如果线程遇到阻塞就会抛出InterruptedException异常，
//        并且将会重置线程的中断状态
    // sleep阻塞情况，调用interrupt去结束线程，结果是抛出异常
//        Thread org.example.datastructure.thread = new Thread(() -> {
//            try {
//                Thread.sleep(100000L);
//            } catch (InterruptedException e) {
//                // 在这里清理资源
//                e.printStackTrace();
//                return;
//            }
//        });
//
//        org.example.datastructure.thread.start();
//        Thread.sleep(2000L);
//        org.example.datastructure.thread.interrupt();
//    }


    /**
     * 设置状态位去中断
     * 缺点：当try catch中sleep()较长时间时，不能及时中断。
     * 本应该在3秒之后就及时中断，但在程序处于阻塞状态，没有中断
     *
     * @throws InterruptedException
     */
    public void sign() throws InterruptedException {
        new Thread(() -> {
            while (!IS_STOP) {
                System.out.println(Thread.currentThread().getName());
                try {
                    Thread.sleep(1000000L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
        Thread.sleep(3000L);
        IS_STOP = true;
    }

    /**
     * 线程初始时，中断标志位=false
     * 调用线程的interrupt()方法，该线程的中断标志位=true
     * 如果线程处于阻塞态，中断抛出InterruptedException时(该异常在while循环体内被捕获)，会重置线程的中断标志位(即interrupted()会返回false)
     * <p>
     * 重：调用interrupt()，便将线程的中断标志位置为true，当遇到阻塞（或者在调用interrupt方法中，正处于阻塞），都会立马中断阻塞，将线程的中断标志位置为false，并抛出异常
     *
     * @throws InterruptedException
     */
    public static void main2(String[] args) throws InterruptedException {
        Thread t = new Thread(() -> {
            // 线程对象.isInterrupted()作用：只返回中断标志位，不做任何修改
            // 线程对象.interrupted()作用：只返回中断标志位，并做修改为false
            while (!Thread.interrupted()) {
                System.out.println(IS_STOP);
                try {
                    System.out.println(Thread.currentThread().getName());
                    Thread.sleep(1000L);
                } catch (InterruptedException e) {
                    // 线程阻塞的时候，抛出InterruptedException中断线程，isInterrupted=false;
                    // 清理资源--出队等
                    e.printStackTrace();
                    return;
                }
            }
        });
        t.start();
        Thread.sleep(3000L);
        // 线程状态位被设为true
        t.interrupt();
    }

    public static void main3(String[] args) throws InterruptedException {
        ThreadPoolTaskExecutor threadPoolTaskExecutor = ThreadPoolConfig.asyncThreadPoolExecutor();
        TestRunable thread01 = new TestRunable("thread01");
        TestRunable thread02 = new TestRunable("thread02");
        threadPoolTaskExecutor.execute(thread01);
        threadPoolTaskExecutor.execute(thread02);
        BlockingQueue<Runnable> queue = threadPoolTaskExecutor.getThreadPoolExecutor().getQueue();
        System.out.println(queue.size());
        queue.remove(thread01);
        // https://blog.csdn.net/weixin_39616565/article/details/114046984 任务超时队列

    }

    public static void main(String[] args) {
        ThreadPoolTaskExecutor executorService = ThreadPoolConfig.asyncThreadPoolExecutor();
        Future future = executorService.submit(new Callable() {
            @Override
            public Object call() throws Exception {
                try {
                    Thread.sleep(10000L);
                    // TimeUnit.SECONDS.sleep(10);
                    // 具体业务执行
                } catch (InterruptedException e) {
                    System.out.println("任务被中断。");
                }
                return "OK";
            }
        });
        try {
            Object o = future.get(2, TimeUnit.SECONDS);
        } catch (InterruptedException | ExecutionException | TimeoutException e) {
            future.cancel(true);
            System.out.println("任务超时。");
        } finally {
            System.out.println("清理资源。");
        }
    }
}