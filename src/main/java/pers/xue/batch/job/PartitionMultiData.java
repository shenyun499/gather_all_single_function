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
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemStreamReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.data.RepositoryItemReader;
import org.springframework.batch.item.data.RepositoryItemWriter;
import org.springframework.batch.item.data.builder.RepositoryItemReaderBuilder;
import org.springframework.batch.item.data.builder.RepositoryItemWriterBuilder;
import org.springframework.batch.item.database.HibernatePagingItemReader;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.batch.item.database.builder.HibernatePagingItemReaderBuilder;
import org.springframework.batch.item.database.builder.JpaPagingItemReaderBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import pers.xue.batch.entity.CommonEntity;
import pers.xue.batch.partitioner.MultiDataPartitioner;
import pers.xue.batch.reader.PartitionMultiDataReader;
import pers.xue.batch.repository.CommonRepository;

import java.util.Arrays;
import java.util.Collections;

/**
 * @author huangzhixue
 * @date 2022/3/2 7:41 下午
 * @Description
 * 分区数据，可以针对大数据量的数据库读取
 * 比如10万数据，分成10个Step按数据页去读取，此时只需要计算好数据页的页数就行
 */
@Slf4j
@Configuration
public class PartitionMultiData {

    @Autowired
    StepBuilderFactory stepBuilderFactory;

    @Autowired
    CommonRepository commonRepository;

    @Bean
    public Job partitionMultiDataJob(JobBuilderFactory jobBuilderFactory, Step partitionMasterMultiFileStep) {
        return jobBuilderFactory.get("partitionMultiDataJob")
                .start(partitionMasterMultiFileStep)
                .build();
    }

    @Bean
    public Step partitionMasterMultiDataStep(Partitioner partitionMultiDataPartitioner,
                                             PartitionHandler multiFilePartitionHandler) {
        return stepBuilderFactory.get("partitionMasterMultiDataStep")
                .partitioner("partitionSlaveMultiDataStep", partitionMultiDataPartitioner)
                .partitionHandler(multiFilePartitionHandler)
                .build();
    }

    @Bean
    public Step partitionSlaveMultiDataStep(ItemReader<CommonEntity> partitionMultiDataReader,
                                            ItemWriter<CommonEntity> partitionMultiDataWriter,
                                            StepExecutionListener partitionStepListener) {
        return stepBuilderFactory.get("partitionSlaveMultiDataStep")
                .<CommonEntity, CommonEntity>chunk(1000)
                .reader(partitionMultiDataReader)
                .writer(partitionMultiDataWriter)
                .listener(partitionStepListener)
                .build();
    }

    @Bean
    public PartitionHandler multiDataPartitionHandler(Step partitionSlaveMultiFileStep) {
        TaskExecutorPartitionHandler handler = new TaskExecutorPartitionHandler();
        // 分区，两个区，这个是partition方法的gridSize, 在下面的MultiDataPartitioner类中
        handler.setGridSize(2);
        handler.setStep(partitionSlaveMultiFileStep);
        handler.setTaskExecutor(new SimpleAsyncTaskExecutor());
        return handler;
    }

    @Bean
    @StepScope
    public ItemReader<CommonEntity> partitionMultiDataReader(@Value("#{stepExecutionContext['page']}") Integer page,
                                                             @Value("#{stepExecutionContext['pageSize']}") Integer pageSize) {
        return new PartitionMultiDataReader(page, pageSize, commonRepository);
    }

    @Bean
    public RepositoryItemWriter<CommonEntity> partitionMultiDataWriter() {
        return new RepositoryItemWriterBuilder<CommonEntity>()
                .repository(commonRepository)
                .methodName("save")
                .build();
    }

    /**
     * 分区
     */
    @Bean
    @StepScope
    public Partitioner partitionMultiDataPartitioner() {
        return new MultiDataPartitioner();
    }
}
