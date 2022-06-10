package pers.xue.batch.decider;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.job.flow.FlowExecutionStatus;
import org.springframework.batch.core.job.flow.JobExecutionDecider;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * @author huangzhixue
 * @date 2022/6/10 10:10
 * @Description
 * COMPLETED: 表示可以继续处理下一个Step
 * STOPPED：表示终止当前job
 */
@Slf4j
@Component
public class BatchJobDecider2 implements JobExecutionDecider {
    /**
     * 可以通过 @Value注入
     * 是否忽视这个Decider
     */
    private boolean ignoreDecider = false;

    @Override
    public FlowExecutionStatus decide(JobExecution jobExecution, StepExecution stepExecution) {
        ExecutionContext executionContext = Objects.requireNonNull(stepExecution).getJobExecution().getExecutionContext();
        String batchType = executionContext.getString("batchType");
        String status = executionContext.getString("status");
        if (ignoreDecider || FlowExecutionStatus.COMPLETED.getName().equals(status)) {
            return new FlowExecutionStatus(FlowExecutionStatus.COMPLETED.getName());
        }
        log.info("batchType: {}, status: {}", batchType, status);
        return new FlowExecutionStatus(FlowExecutionStatus.STOPPED.getName());
    }
}
