package pers.xue.batch.listener;

import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;

/**
 * @author huangzhixue
 * @date 2021/12/24 5:23 下午
 * @Description
 */
public class ReadDBAndWriteFileTaskletListener implements StepExecutionListener {
    @Override
    public void beforeStep(StepExecution stepExecution) {

    }

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        // todo: record batch info
        return null;
    }
}
