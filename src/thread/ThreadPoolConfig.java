package thread;

import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

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
    private static final String TASK_THREADPOOL_NAME = "task_threadpool_test";

    @Bean(TASK_THREADPOOL_NAME)
    public Executor asyncThreadPoolExecutor() {
        // 获得运行机器 逻辑处理器核数（其实就是支持同时运行的线程数）
        Integer availibleNum = Runtime.getRuntime().availableProcessors();
        // 内部还是使用了ThreadPoolExecutor，只是参数配置更加方便
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        // 核心参数
        executor.setCorePoolSize(availibleNum);
        // 配置最大线程数
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
}
