package pers.xue.batch.job;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.data.RepositoryItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import pers.xue.batch.entity.CommonEntity;
import pers.xue.batch.listener.ReadDBListener;
import pers.xue.batch.processtor.ReadDBBeanProcesstor;
import pers.xue.batch.reader.ReadDBBeanReader;
import pers.xue.batch.remote.ReadDBBean;
import pers.xue.batch.repository.CommonRepository;
import pers.xue.batch.writer.ReadDBWriter;

import java.util.*;


/**
 * @author huangzhixue
 * @date 2021/12/17 5:50 下午
 * @Description
 * 这个类有两个job
 *      1、readDBJob
 *          普通的读，通过序列器，并且通过listener判断读取是否完毕，适用于非jpa
 *      2、readDBJob2
 *          通过jpa repository指定方法和参数读取记录，这个过程是reader
 *
 *      processor的作用这里是将实体类转成bean
 *      这里的writer除了打印日志，没有做其它任何事
 */
@Configuration
public class ReadDB {

    @Autowired
    private ReadDBListener readDBListener;

    @Autowired
    private CommonRepository commonRepository;

    @Bean
    public Job readDBJob(JobBuilderFactory jobBuilderFactory, Step readDBStep) {
        return jobBuilderFactory.get("readDBJob").start(readDBStep).build();
    }

    @Bean
    public Job readDBJob2(JobBuilderFactory jobBuilderFactory, Step readDBStep2) {
        return jobBuilderFactory.get("readDBJob2").start(readDBStep2).build();
    }

    /**
     * 方式1：读取--写入
     * @param stepBuilderFactory
     * @return
     */
    @Bean
    public Step readDBStep(StepBuilderFactory stepBuilderFactory) {
        return stepBuilderFactory.get("readDBStep")
                .<CommonEntity, ReadDBBean>chunk(5)
                // 读取数据
                .reader(dBItemReader())
                // 转换数据
                .processor(dBProcessor())
                // 写入数据
                .writer(dBItemWriter())
                // 监听记录是否被读完
                .listener(readDBListener)
                .build();
    }

    @Bean
    public ItemProcessor<CommonEntity, ReadDBBean> dBProcessor() {
        return new ReadDBBeanProcesstor();
    }

    @Bean
    public ItemReader<CommonEntity> dBItemReader() {
        return new ReadDBBeanReader();
    }

    @Bean
    public ItemWriter<? super ReadDBBean> dBItemWriter() {
        return new ReadDBWriter();
    }




    @Bean
    public Step readDBStep2(StepBuilderFactory stepBuilderFactory, TaskExecutor taskExecutor) {
        return stepBuilderFactory.get("readDBStep2")
                .<CommonEntity, ReadDBBean>chunk(5)
                // 读取数据
                .reader(dBItemReader2())
                // 转换数据
                .processor(dBProcessor())
                // 写入数据
                .writer(dBItemWriter())
                .taskExecutor(taskExecutor)
                .throttleLimit(2)
                .build();
    }

    /**
     * jpa 读取方式，直接通过repository 和 方法名称、参数分页读取
     * @return
     */
    @Bean
    public RepositoryItemReader<CommonEntity> dBItemReader2() {
        Map<String, Sort.Direction> map = new HashMap<>();
        map.put("id", Sort.Direction.DESC);
        RepositoryItemReader<CommonEntity> repositoryItemReader = new RepositoryItemReader<>();
        repositoryItemReader.setRepository(commonRepository);
        repositoryItemReader.setMethodName("findByContent");
        repositoryItemReader.setArguments(Arrays.asList("神韵学Spring Batch"));
        repositoryItemReader.setSort(map);
        return repositoryItemReader;
    }
}
