package pers.xue.batch.job;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pers.xue.batch.tasklet.GenerateMultipleFileTasklet;

/**
 * @author huangzhixue
 * @date 2022/7/5 15:09
 * @Description
 */
@Slf4j
@Configuration
public class GenerateMultiTxtFileByTasklet {

    @Autowired
    private GenerateMultipleFileTasklet generateMultipleFileTasklet;

    @Bean
    public Job generateMultiTxtFileByTaskletJob(JobBuilderFactory jobBuilderFactory, Step generateFileStep) {
        return jobBuilderFactory.get("generateMultiTxtFileByTasklet")
                .start(generateFileStep)
                .build();
    }

    @Bean
    public Step generateFileStep(StepBuilderFactory stepBuilderFactory) {
        return stepBuilderFactory.get("generateFileStep")
                .tasklet(generateMultipleFileTasklet)
                .build();
    }
}
