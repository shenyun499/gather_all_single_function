package pers.xue.batch.job;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pers.xue.batch.entity.CommonEntity;
import pers.xue.batch.listener.ReadDBAndWriteFileTaskletListener;
import pers.xue.batch.reader.ReadDBAndWriterFileItemReader;
import pers.xue.batch.tasklet.ReadDBAndWriteFileTasklet;
import pers.xue.batch.writer.ReadDBAndWriterFileItemWriter;

import java.util.List;

/**
 * @author huangzhixue
 * @date 2021/12/23 5:35 下午
 * @Description
 */
@Configuration
public class ReadDBAndWriteFile {

    private String generateFilePath = "";

    @Bean
    public Job readDBAndWriteFileJob(JobBuilderFactory jobBuilderFactory, Step readDBAndWriteFileStep) {
        return jobBuilderFactory.get("readDBAndWriteFileJob").start(readDBAndWriteFileStep).build();
    }

    /**
     * 普通的方式
     * @param stepBuilderFactory
     * @return
     */
    @Bean
    public Step readDBAndWriteFileStep(StepBuilderFactory stepBuilderFactory) {
        return stepBuilderFactory.get("readDBAndWriteFileStep")
                .<List<CommonEntity>, CommonEntity>chunk(1)
                .reader(readDBAndWriterFileItemReader())
                //.processor(dBProcessor())
                .writer(readDBAndWriterFileItemWriter())
                .build();
    }

    /**
     * tasklet方式
     * @param stepBuilderFactory
     * @return
     */
    @Bean
    public Step readDBAndWriteFileStep2(StepBuilderFactory stepBuilderFactory) {
        return stepBuilderFactory.get("readDBAndWriteFileStep")
                .tasklet(readDBAndWriteFileTasklet())
                .listener(readDBAndWriteFileTaskletListener())
                .build();
    }

    private StepExecutionListener readDBAndWriteFileTaskletListener() {
        return new ReadDBAndWriteFileTaskletListener();
    }

    private ReadDBAndWriteFileTasklet readDBAndWriteFileTasklet() {
        return new ReadDBAndWriteFileTasklet(generateFilePath);
    }

    @Bean
    public ItemReader<List<CommonEntity>> readDBAndWriterFileItemReader() {
        return new ReadDBAndWriterFileItemReader();
    }

    @Bean
    public ItemWriter<? super CommonEntity> readDBAndWriterFileItemWriter() {
        return new ReadDBAndWriterFileItemWriter();
    }
}
