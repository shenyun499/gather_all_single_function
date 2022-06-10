package pers.xue.batch.job;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.job.flow.FlowExecutionStatus;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pers.xue.batch.decider.BatchJobDecider;
import pers.xue.batch.decider.BatchJobDecider2;

/**
 * @author huangzhixue
 * @date 2022/6/10 10:09
 * @Description
 * 开关
 *
 */
@Slf4j
@Configuration
public class DeciderUsage {
    @Autowired
    @Qualifier("firstDeciderStep")
    Step firstDeciderStep;

    @Autowired
    @Qualifier("firstDeciderStep2")
    Step firstDeciderStep2;

    @Autowired
    @Qualifier("firstDeciderStep3")
    Step firstDeciderStep3;

    @Autowired
    @Qualifier("firstDeciderStep4")
    Step firstDeciderStep4;

    @Autowired
    BatchJobDecider batchJobDecider;

    @Autowired
    BatchJobDecider2 batchJobDecider2;

    @Bean
    public Job deciderUsageJob(JobBuilderFactory jobBuilderFactory) {
        return jobBuilderFactory.get("DeciderUsage")
                .start(firstDeciderStep)
                // 采用batchJobDecider
                .next(batchJobDecider)
                .on(FlowExecutionStatus.COMPLETED.getName())
                .to(firstDeciderStep3)

                .next(firstDeciderStep2)
                // 不能再重复用batchJobDecider，启一个新的batchJobDecider2
                .next(batchJobDecider2)
                .on(FlowExecutionStatus.COMPLETED.getName())
                .to(firstDeciderStep4)
                .end()
                .build();

    }

    @Bean
    public Step firstDeciderStep(StepBuilderFactory stepBuilderFactory) {
        // 控制 step2是否执行 COMPLETED让执行， STOPPED 不让执行
        return stepBuilderFactory.get("firstDeciderStep")
                .tasklet((stepContribution, chunkContext) -> {
                    // 通过设置key value到 executionContext
                    ExecutionContext executionContext = chunkContext.getStepContext().getStepExecution().getJobExecution().getExecutionContext();
                    executionContext.put("batchType", "testDecider");
                    //executionContext.put("status", FlowExecutionStatus.STOPPED.getName());
                    executionContext.put("status", FlowExecutionStatus.COMPLETED.getName());
                    return RepeatStatus.FINISHED;
                }).build();
    }

    @Bean
    public Step firstDeciderStep2(StepBuilderFactory stepBuilderFactory) {
        // 控制 step4是否执行 COMPLETED让执行， STOPPED 不让执行
        return stepBuilderFactory.get("firstDeciderStep2")
                .tasklet((stepContribution, chunkContext) -> {
                    ExecutionContext executionContext = chunkContext.getStepContext().getStepExecution().getJobExecution().getExecutionContext();
                    executionContext.put("batchType", "testDecider");
                    executionContext.put("status", FlowExecutionStatus.STOPPED.getName());
                    //executionContext.put("status", FlowExecutionStatus.COMPLETED.getName());
                    return RepeatStatus.FINISHED;
                }).build();
    }

    @Bean
    public Step firstDeciderStep3(StepBuilderFactory stepBuilderFactory) {
        return stepBuilderFactory.get("firstDeciderStep3")
                .tasklet((stepContribution, chunkContext) -> {
                    log.info("execute firstDeciderStep3 successfully");
                    return RepeatStatus.FINISHED;
                }).build();
    }

    @Bean
    public Step firstDeciderStep4(StepBuilderFactory stepBuilderFactory) {
        return stepBuilderFactory.get("firstDeciderStep4")
                .tasklet((stepContribution, chunkContext) -> {
                    log.info("execute firstDeciderStep4 successfully");
                    return RepeatStatus.FINISHED;
                }).build();
    }

}
