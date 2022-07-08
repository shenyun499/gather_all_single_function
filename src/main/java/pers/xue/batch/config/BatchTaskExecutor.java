package pers.xue.batch.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;

/**
 * @author huangzhixue
 * @date 2022/7/8 17:18
 * @Description
 */
@Configuration
public class BatchTaskExecutor {

    @Bean
    public TaskExecutor taskExecutor() {
        // 要加个名称，不然识别不了
        SimpleAsyncTaskExecutor taskExecutor = new SimpleAsyncTaskExecutor("spring_batch");
        // 最大线程数
        taskExecutor.setConcurrencyLimit(Runtime.getRuntime().availableProcessors());
        return taskExecutor;
    }
}
