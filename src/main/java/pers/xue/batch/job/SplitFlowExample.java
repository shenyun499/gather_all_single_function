package pers.xue.batch.job;

import lombok.extern.java.Log;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.job.builder.FlowBuilder;
import org.springframework.batch.core.job.flow.Flow;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.data.RepositoryItemReader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.data.domain.Sort;
import pers.xue.batch.entity.CommonEntity;
import pers.xue.batch.repository.CommonRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author huangzhixue
 * @date 2022/2/7 3:55 下午
 * @Description
 * 分流，多个step并行/并发执行
 * flowJob1/flowJob2 并行执行
 */
@Log
@Configuration
public class SplitFlowExample {

    @Bean
    public Job splitFlowExampleJob(JobBuilderFactory jobBuilderFactory, Step splitFlowStep1, Step splitFlowStep2) {
        // 定义流 1、2
        Flow flowJob1 = new FlowBuilder<Flow>("flowJob1").start(splitFlowStep1).build();
        Flow flowJob2 = new FlowBuilder<Flow>("flowJob2").start(splitFlowStep2).build();
        // SimpleAsyncTaskExecutor下面用的默认， 可以自己实现一个@Bean TaskExecutor，配置concurrencyLimit参数
        Flow splitFlows = new FlowBuilder<Flow>("split_flows")
                .split(new SimpleAsyncTaskExecutor())
                .add(flowJob1, flowJob2)
                .build();

        return jobBuilderFactory.get("splitFlowExample")
                .incrementer(new RunIdIncrementer())
                .start(splitFlows)
                .build()
                .build();
    }

    @Bean
    public Step splitFlowStep1(StepBuilderFactory stepBuilderFactory, ItemReader<CommonEntity> splitFlowReader1
    , ItemWriter<CommonEntity> splitFlowWriter1) {
        return stepBuilderFactory.get("splitFlowStep1")
                .<CommonEntity, CommonEntity>chunk(5)
                // 读取数据
                .reader(splitFlowReader1)
                // 写入数据
                .writer(splitFlowWriter1)
                .build();
    }

    @Bean
    public Step splitFlowStep2(StepBuilderFactory stepBuilderFactory, ItemReader<CommonEntity> splitFlowReader
            , ItemWriter<CommonEntity> splitFlowWriter2) {
        return stepBuilderFactory.get("splitFlowStep2")
                .<CommonEntity, CommonEntity>chunk(5)
                // 读取数据
                .reader(splitFlowReader)
                // 写入数据
                .writer(splitFlowWriter2)
                .build();
    }

    /**
     * jpa 读取方式，直接通过repository 和 方法名称、参数分页读取
     * @return
     */
    @Bean
    public RepositoryItemReader<CommonEntity> splitFlowReader(CommonRepository commonRepository) {
        Map<String, Sort.Direction> map = new HashMap<>();
        map.put("id", Sort.Direction.DESC);
        List<String> params = new ArrayList();
        params.add("神韵学Spring Batch");
        RepositoryItemReader<CommonEntity> repositoryItemReader = new RepositoryItemReader<>();
        repositoryItemReader.setRepository(commonRepository);
        repositoryItemReader.setPageSize(5);
        repositoryItemReader.setMethodName("findByContent");
        repositoryItemReader.setArguments(params);
        repositoryItemReader.setSort(map);
        return repositoryItemReader;
    }

    @Bean
    public RepositoryItemReader<CommonEntity> splitFlowReader1(CommonRepository commonRepository) {
        Map<String, Sort.Direction> map = new HashMap<>();
        map.put("id", Sort.Direction.DESC);
        List<String> params = new ArrayList();
        params.add("神韵学Spring Batch");
        RepositoryItemReader<CommonEntity> repositoryItemReader = new RepositoryItemReader<>();
        repositoryItemReader.setRepository(commonRepository);
        repositoryItemReader.setPageSize(5);
        repositoryItemReader.setMethodName("findByContent");
        repositoryItemReader.setArguments(params);
        repositoryItemReader.setSort(map);
        return repositoryItemReader;
    }

    @Bean
    public ItemWriter<CommonEntity> splitFlowWriter1() {
        return new SplitFlow1();
    }

    @Bean
    public ItemWriter<CommonEntity> splitFlowWriter2() {
        return new SplitFlow2();
    }

    class SplitFlow1 implements ItemWriter<CommonEntity> {
        @Override
        public void write(List<? extends CommonEntity> list) {
            // throw exception 尝试
            log.info("I am flow 01");
        }
    }

    class SplitFlow2 implements ItemWriter<CommonEntity> {
        @Override
        public void write(List<? extends CommonEntity> list) {
            // 可以尝试抛出异常测试并发
            log.info("I am flow 02");
        }
    }
}
