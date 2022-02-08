package pers.xue.batch.job;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.data.RepositoryItemReader;
import org.springframework.batch.item.data.RepositoryItemWriter;
import org.springframework.batch.item.data.builder.RepositoryItemReaderBuilder;
import org.springframework.batch.item.data.builder.RepositoryItemWriterBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Sort;
import pers.xue.batch.entity.CommonEntity;
import pers.xue.batch.repository.CommonRepository;

import java.util.Arrays;
import java.util.Collections;

/**
 * @author huangzhixue
 * @date 2022/2/7 5:26 下午
 * @Description
 * 通过Repository 读写DB数据
 */
@Configuration
public class ReadDBAndWriteDBByRepository {

    @Autowired
    CommonRepository commonRepository;

    @Bean
    public Job readDBAndWriteDBByRepositoryJob(JobBuilderFactory jobBuilderFactory, Step readDBAndWriteDBByRepositoryStep) {
        return jobBuilderFactory.get("readDBAndWriteDBByRepositoryJob").start(readDBAndWriteDBByRepositoryStep).build();
    }

    @Bean
    public Step readDBAndWriteDBByRepositoryStep(StepBuilderFactory stepBuilderFactory, ItemReader<CommonEntity> readDBAndWriteDBByRepositoryReader
    , ItemWriter<CommonEntity> readDBAndWriteDBByRepositoryWriter) {
        return stepBuilderFactory.get("readDBAndWriteDBByRepositoryStep")
                .<CommonEntity, CommonEntity>chunk(5)
                // 读取数据
                .reader(readDBAndWriteDBByRepositoryReader)
                .processor(readDBAndWriteDBByRepositoryProcessor())
                // 写入数据
                .writer(readDBAndWriteDBByRepositoryWriter)
                .build();
    }

    /**
     *  repository 和 方法名称、参数分页读取
     * @return
     */
    @Bean
    public RepositoryItemReader<CommonEntity> readDBAndWriteDBByRepositoryReader() {
        return new RepositoryItemReaderBuilder<CommonEntity>()
                .name("readDBAndWriteDBByRepositoryReader")
                .repository(commonRepository)
                .pageSize(5)
                .methodName("findByContent")
                .arguments(Arrays.asList("神韵学Spring Batch"))
                .sorts(Collections.singletonMap("id", Sort.Direction.DESC))
                .build();
    }

    public ItemProcessor<CommonEntity, CommonEntity> readDBAndWriteDBByRepositoryProcessor() {
        return new ReadDBAndWriteDBByRepositoryProcesstor();
    }

    @Bean
    public RepositoryItemWriter<CommonEntity> readDBAndWriteDBByRepositoryWriter() {
        return new RepositoryItemWriterBuilder<CommonEntity>()
                .repository(commonRepository)
                .methodName("save")
                .build();
    }

    class ReadDBAndWriteDBByRepositoryProcesstor implements ItemProcessor<CommonEntity, CommonEntity> {
        @Override
        public CommonEntity process(CommonEntity commonEntity) {
            // reset CommonEntity, make a action for update
            commonEntity.setContent("22222");
            return commonEntity;
        }
    }
}
