package pers.xue.batch.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.stereotype.Component;

/**
 * @author huangzhixue
 * @date 2021/12/24 4:40 下午
 * @Description
 */
@Slf4j
@Component
public class ReadDBListener implements StepExecutionListener {
    @Override
    public void beforeStep(StepExecution stepExecution) {
        // it is not need to do anything now
    }

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        if (stepExecution.getReadCount() == 0) {
            log.info("no record can be processed");
        }
        return ExitStatus.COMPLETED;
    }
}
