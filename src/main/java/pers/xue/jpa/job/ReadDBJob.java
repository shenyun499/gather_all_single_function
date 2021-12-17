package pers.xue.jpa.job;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pers.xue.jpa.processtor.ReadDBBeanProcesstor;
import pers.xue.jpa.reader.ReadDBBeanReader;
import pers.xue.jpa.remote.ReadDBBean;
import pers.xue.jpa.writer.ReadDBWriter;


/**
 * @author huangzhixue
 * @date 2021/12/17 5:50 下午
 * @Description
 */
@Configuration
public class ReadDBJob {
    @Bean
    public Job readDBJob(JobBuilderFactory jobBuilderFactory, Step readDBStep) {
        return jobBuilderFactory.get("readDBJob").start(readDBStep).build();
    }

    @Bean
    public Step readDBStep(StepBuilderFactory stepBuilderFactory) {
        return stepBuilderFactory.get("readDBJobStep")
                .<ReadDBBean, ReadDBBean>chunk(1)
                .reader(dBItemReader())
                //.processor(dBProcessor())
                .writer(dBItemWriter())
                .build();
    }

    @Bean
    public ItemProcessor<ReadDBBean, ReadDBBean> dBProcessor() {
        return new ReadDBBeanProcesstor();
    }

    @Bean
    public ItemReader<ReadDBBean> dBItemReader() {
        return new ReadDBBeanReader();
    }


    public ItemWriter<? super ReadDBBean> dBItemWriter() {
        return new ReadDBWriter();
    }

}
