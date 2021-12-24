package pers.xue.batch.tasklet;

import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;

/**
 * @author huangzhixue
 * @date 2021/12/24 5:20 下午
 * @Description
 */
public class ReadDBAndWriteFileTasklet implements Tasklet {
    private String generateFilePath;

    public ReadDBAndWriteFileTasklet(String generateFilePath) {
        this.generateFilePath = generateFilePath;
    }

    @Override
    public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {
        return null;
    }
}
