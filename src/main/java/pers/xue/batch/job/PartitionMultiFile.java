package pers.xue.batch.job;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.partition.PartitionHandler;
import org.springframework.batch.core.partition.support.Partitioner;
import org.springframework.batch.core.partition.support.TaskExecutorPartitionHandler;
import org.springframework.batch.item.ItemStreamReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.data.RepositoryItemWriter;
import org.springframework.batch.item.data.builder.RepositoryItemWriterBuilder;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import pers.xue.batch.entity.CommonEntity;
import pers.xue.batch.partitioner.MultiResourcePartitioner;
import pers.xue.batch.repository.CommonRepository;

import java.io.IOException;

/**
 * @author huangzhixue
 * @date 2022/3/1 4:53 下午
 * @Description
 * 分区文件，读取多个csv文件
 */
@Slf4j
@Configuration
public class PartitionMultiFile {

    @Autowired
    StepBuilderFactory stepBuilderFactory;

    //String path = "/Users/huangzhixue/IdeaProjects/gather_all_single_function/src/main/resources/partitioner";
    String path = "classpath*:/partitioner/*.csv";

    @Bean
    public Job partitionMultiFileJob(JobBuilderFactory jobBuilderFactory, Step partitionMasterMultiFileStep) {
        return jobBuilderFactory.get("partitionMultiFileJob")
                .start(partitionMasterMultiFileStep)
                .build();
    }

    @Bean
    public Step partitionMasterMultiFileStep(Partitioner partitionMultiFilePartitioner,
                                             PartitionHandler multiFilePartitionHandler) {
        return stepBuilderFactory.get("partitionMasterMultiFileStep")
                .partitioner("partitionSlaveMultiFileStep", partitionMultiFilePartitioner)
                .partitionHandler(multiFilePartitionHandler)
                .build();
    }

    @Bean
    public Step partitionSlaveMultiFileStep(ItemStreamReader<CommonEntity> partitionMultiFileReader,
                                            ItemWriter<CommonEntity> partitionMultiFileWriter,
                                            StepExecutionListener partitionStepListener) {
        return stepBuilderFactory.get("partitionSlaveMultiFileStep")
                .<CommonEntity, CommonEntity>chunk(1000)
                .reader(partitionMultiFileReader)
                .writer(partitionMultiFileWriter)
                .listener(partitionStepListener)
                .build();
    }

    @Bean
    public PartitionHandler multiFilePartitionHandler(Step partitionSlaveMultiFileStep) {
        TaskExecutorPartitionHandler handler = new TaskExecutorPartitionHandler();
        handler.setGridSize(2);
        handler.setStep(partitionSlaveMultiFileStep);
        handler.setTaskExecutor(new SimpleAsyncTaskExecutor());
        return handler;
    }

    @Bean
    @StepScope
    public ItemStreamReader<CommonEntity> partitionMultiFileReader(@Value("#{stepExecutionContext['fileName']}") String fileName) {
        DefaultLineMapper<CommonEntity> defaultLineMapper = new DefaultLineMapper<>();
        // 默认的DelimitedLineTokenizer，分隔符delimiter为逗号','，引号包裹'"'。
        DelimitedLineTokenizer tokenizer = new DelimitedLineTokenizer();
        tokenizer.setNames("id", "content");
        tokenizer.setStrict(false);
        defaultLineMapper.setLineTokenizer(tokenizer);
        // 读取后的字段与属性映射，set 值
        defaultLineMapper.setFieldSetMapper(new NameCommonEntitySetMapper());

        return new FlatFileItemReaderBuilder<CommonEntity>()
                .name("partitionMultiFileReader")
                .resource(new FileSystemResource(fileName))
                // 跳过第一行，标题行
                .linesToSkip(1)
                .lineMapper(defaultLineMapper)
                .build();
    }

    @Bean
    public RepositoryItemWriter<CommonEntity> partitionMultiFileWriter(CommonRepository commonRepository) {
        return new RepositoryItemWriterBuilder<CommonEntity>()
                .repository(commonRepository)
                .methodName("save")
                .build();
    }

    /**
     * 字段名称映射
     */
    private static class NameCommonEntitySetMapper implements FieldSetMapper<CommonEntity> {
        @Override
        public CommonEntity mapFieldSet(FieldSet fieldSet) {
            return CommonEntity
                    .builder()
                    // 用字段name（前提tokenizer.setNames(new String[] {"id", "content"});）
                    .id(fieldSet.readInt("id"))
                    .content(fieldSet.readString("content"))
                    .build();
        }
    }

    /**
     * 分区
     */
    @Bean
    @StepScope
    public Partitioner partitionMultiFilePartitioner(ResourcePatternResolver resourcePatternResolver) {
        MultiResourcePartitioner multiResourcePartitioner = new MultiResourcePartitioner();
        Resource[] resources = null;
        try {
            log.info("Reading all files from path: {}", path);
            resources = resourcePatternResolver.getResources(path);
        } catch (IOException e) {
            throw new RuntimeException(String.format("Error reading from path: %s", path), e);
        }
        multiResourcePartitioner.setResources(resources);
        return multiResourcePartitioner;
    }
}
