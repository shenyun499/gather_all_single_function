package thread;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 线程池配置
 * https://blog.csdn.net/qq_41055045/article/details/102646589
 * ThreadPoolTaskExecutor执行流程，先调用initializeExecutor初始化此类。里面最主要的还是实现ThreadPoolExecutor类，当用ThreadPoolTaskExecutor.execute时，其实调的是ThreadPoolExecutor的逻辑
 *
 * @author ：HUANG ZHI XUE
 * @date ：Create in 2021-03-09
 */
@Component
public class ThreadPoolConfig {

    private final static Logger logger = LoggerFactory.getLogger(ThreadPoolConfig.class);
    /**
     * 线程池名称
     */
    private static final String TASK_THREADPOOL_NAME = "task_threadpool_test";

    /**
     * 线程池配置
     * @return 线程池
     */
    @Bean(TASK_THREADPOOL_NAME)
    public Executor asyncThreadPoolExecutor1() {
        // 获得运行机器 逻辑处理器核数（其实就是线程数）
        Integer availibleNum = Runtime.getRuntime().availableProcessors();
        // 内部还是使用了ThreadPoolExecutor，只是参数配置更加方便
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        // 核心参数
        executor.setCorePoolSize(availibleNum);
        // 配置最大线程数 线程数*2
        executor.setMaxPoolSize(availibleNum * 2);
        // 配置队列容量 队列：不配置容量，用SynchronousQueue，配置用LinkedBlockingQueue。 (BlockingQueue)(queueCapacity > 0 ? new LinkedBlockingQueue(queueCapacity) : new SynchronousQueue());
        executor.setQueueCapacity(100);
        // 配置非核心线程超时释放时间（核心线程默认不允许回收）
        executor.setKeepAliveSeconds(60);
        // 配置线程名称前缀
        executor.setThreadNamePrefix(TASK_THREADPOOL_NAME);
        // 配置拒绝策略，CallerRunsPolicy：当拒绝时由调用线程处理该任务
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        return executor;
    }

    /**
     * 自己new的，不会自己初始化，需要调用initialize才能用，否则报错
     * java.lang.IllegalStateException: ThreadPoolTaskExecutor not initialized
     */
    public static ThreadPoolTaskExecutor asyncThreadPoolExecutor() {
        // 获得运行机器 逻辑处理器核数（其实就是线程数）
        Integer availibleNum = Runtime.getRuntime().availableProcessors();
        availibleNum = 0;
        // 内部还是使用了ThreadPoolExecutor，只是参数配置更加方便
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        // 核心参数
        executor.setCorePoolSize(availibleNum);
        // 配置最大线程数
        executor.setMaxPoolSize(1);
        // 配置队列容量 队列：不配置容量，用SynchronousQueue，配置用LinkedBlockingQueue。 (BlockingQueue)(queueCapacity > 0 ? new LinkedBlockingQueue(queueCapacity) : new SynchronousQueue());
        executor.setQueueCapacity(100);
        // 配置非核心线程超时释放时间（核心线程默认不允许回收）
        executor.setKeepAliveSeconds(60);
        // 配置线程名称前缀
        executor.setThreadNamePrefix(TASK_THREADPOOL_NAME);
        // 配置拒绝策略，CallerRunsPolicy：当拒绝时由调用线程处理该任务
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        // 不用bean注入方法，需要自己初始化线程池
        executor.initialize();
        return executor;
    }

    public static void main(String[] args) {
//        Integer availibleNum = Runtime.getRuntime().availableProcessors();
//        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(availibleNum, availibleNum * 2, 60L,
//                TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>(100));

        asyncThreadPoolExecutor();

    }

}
