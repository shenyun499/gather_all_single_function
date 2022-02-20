package pers.xue.batch.job;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pers.xue.batch.tasklet.ReadDBTasklet;

/**
 * @author huangzhixue
 * @date 2021/12/28 3:26 下午
 * @Description
 *
 * 面向块的处理并不是在一个步骤中进行处理的唯一方法。如果Step必须包含一个简单的存储过程调用，该怎么办?
 * 您可以将调用实现为ItemReader，并在过程完成后返回null。然而，这样做有点不自然，因为需要一个无操作的ItemWriter。
 * Spring Batch为这个场景提供了TaskletStep
 *
 * 也就是说只是读而不做写操作的时候，那么可以使用TaskletStep，比如从远程通过sftp 下载文件可以用TaskletStep
 */
@Configuration
public class ReadDBByTasklet {

    @Bean
    public Job readDBByTaskletJob(JobBuilderFactory jobBuilderFactory, Step readDBByTaskletStep, ReadDBTasklet readDBTasklet) {
        return jobBuilderFactory.get("readDBByTaskletJob")
                .start(readDBByTaskletStep)
                .build();
    }

    /**
     * tasklet方式
     * @param stepBuilderFactory
     * @return
     */
    @Bean
    public Step readDBByTaskletStep(StepBuilderFactory stepBuilderFactory, ReadDBTasklet readDBTasklet) {
        return stepBuilderFactory.get("readDBByTaskletJob")
                .tasklet(readDBTasklet)
                // .listener(xxxListener) 可以加step监听
                .build();
    }
}
