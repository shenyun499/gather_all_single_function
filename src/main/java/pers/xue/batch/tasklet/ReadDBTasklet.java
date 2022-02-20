package pers.xue.batch.tasklet;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import pers.xue.batch.entity.CommonEntity;
import pers.xue.batch.repository.CommonRepository;

import java.util.List;

/**
 * @author huangzhixue
 * @date 2021/12/24 5:20 下午
 * @Description
 */
@Slf4j
@Component
public class ReadDBTasklet implements Tasklet {

    @Autowired
    private CommonRepository commonRepository;

    @Override
    public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) {
        List<CommonEntity> commonEntitys = commonRepository.findAll();
        if (CollectionUtils.isEmpty(commonEntitys)) {
            throw new RuntimeException("the record can't be found");
        }
        log.info("exist record : {}", commonEntitys);
        return RepeatStatus.FINISHED;
    }
}
