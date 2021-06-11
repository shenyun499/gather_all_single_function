package org.example.datastructure.niuke;

import util.concurrent.*;

/**
 * @Description
 * @Author xuexue
 * @Date 2020/3/211:55
 */
public class MyCallable implements Callable<Integer> {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ExecutorService pool = Executors.newFixedThreadPool(2);
        //可以得到返回值
        Future<Integer> f1 = pool.submit(new MyCallable(100));
        Future<Integer> f2 = pool.submit(new MyCallable(50));
        //输出返回值
        System.out.println(f1.get());
        System.out.println(f2.get());
        //关闭线程池
        pool.shutdown();
    }

    private  int num;
    public MyCallable(int num) {
        this.num = num;
    }

    @Override
    public Integer call() throws Exception {
        int sum = 0;
        return sum + num;
    }
}
