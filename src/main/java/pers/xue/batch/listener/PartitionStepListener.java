package pers.xue.batch.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.stereotype.Component;

/**
 * @author huangzhixue
 * @date 2022/3/1 5:52 下午
 * @Description
 */
@Slf4j
@Component
public class PartitionStepListener implements StepExecutionListener {

    @Override
    public void beforeStep(StepExecution stepExecution) {
        log.info("ThreadName={},steName={},FileName={}",Thread.currentThread().getName(),
                stepExecution.getStepName(),stepExecution.getExecutionContext().getString("fileName"));
    }

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        return null;
    }
}

