package pers.xue.batch.tasklet;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.stereotype.Component;

/**
 * @author huangzhixue
 * @date 2022/2/21 2:38 下午
 * @Description
 */
@Component
@Slf4j
public class DownloadJsonFileBySftpTasklet implements Tasklet {

    @Override
    public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) {
        log.info("need to add download's logic");
        return null;
    }
}
