package pers.xue.batch.job;

import org.springframework.batch.core.ChunkListener;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.item.data.RepositoryItemReader;
import org.springframework.batch.item.data.builder.RepositoryItemReaderBuilder;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.builder.FlatFileItemWriterBuilder;
import org.springframework.batch.item.file.transform.BeanWrapperFieldExtractor;
import org.springframework.batch.item.file.transform.DelimitedLineAggregator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.data.domain.Sort;
import pers.xue.batch.entity.CommonEntity;
import pers.xue.batch.repository.CommonRepository;
import pers.xue.batch.tasklet.ReadDBAndWriteDatFileTasklet;

import java.time.LocalDate;
import java.util.Collections;

/**
 * @author huangzhixue
 * @date 2022/05/09 10:19 上午
 * @Description
 * 这个类的作用：从db读取记录，然后写入dat 文件中
 *  通过jpa repository指定方法和参数读取记录，这个过程是reader
 *  通过FlatFileItemWriter指定bean 并设置field name和写入path将文件写入，这个过程是writer，writer还会写入header和tail，包含时间以及记录总数
 *  (https://docs.spring.io/spring-batch/docs/4.3.x/reference/html/readersAndWriters.html#SimplifiedFileWritingExample)
 *
 *  process:
 *      1、使用ClassPathResource 并将创建好的file放到classpath路径下没有反应
 *      2、path写错了，使用FileSystemResource竟然不报错，因为它在相对路径下面创建了一个file并写入，
 *      我还以为FlatFileItemWriter必须要和FlatFileItemReader一起使用，后面更正了路径发现能写入
 */
@Configuration
public class ReadDBAndWriteDatFile {

    /**
     * 目前使用相对路径，直接在项目名称下生成ABC_20220509.dat，也可以使用绝对路径，/Users/huangzhixue/IdeaProjects/gather_all_single_function/src/main/resources/ABC_20220509.dat
     */
    private String generateFilePath = "ABC_20220509.dat";

    @Bean
    public Job readDBAndWriteDatFileJob(JobBuilderFactory jobBuilderFactory, Step readDBAndWriteDatFileStep) {
        return jobBuilderFactory.get("readDBAndWriteDatFileJob").start(readDBAndWriteDatFileStep).build();
    }

    /**
     * jpa 方式读取records，FlatFileItemWriter方式写出dat file
     * 需要结合 countChunkListener将读到的总记录写到writer中，如果用tasklet将会十分简单。可以看后面的tasklet处理
     * @param stepBuilderFactory
     * @return
     */
    @Bean
    public Step readDBAndWriteDatFileStep(StepBuilderFactory stepBuilderFactory, CommonRepository commonRepository, CountChunkListener countChunkListener) {
        return stepBuilderFactory.get("readDBAndWriteDatFileStep")
                .<CommonEntity, CommonEntity>chunk(1)
                .reader(readDBAndWriterDatFileItemReader(commonRepository))
                .writer(readDBAndWriterDatFileItemWriter(countChunkListener))
                .listener(countChunkListener)
                .build();
    }

    @Bean
    public FlatFileItemWriter<CommonEntity> readDBAndWriterDatFileItemWriter(CountChunkListener countChunkListener) {
        BeanWrapperFieldExtractor<CommonEntity> fieldExtractor = new BeanWrapperFieldExtractor<>();
        fieldExtractor.setNames(new String[] {"id", "content"});
        fieldExtractor.afterPropertiesSet();

        DelimitedLineAggregator<CommonEntity> lineAggregator = new DelimitedLineAggregator<>();
        lineAggregator.setDelimiter("|");
        lineAggregator.setFieldExtractor(fieldExtractor);

        return new FlatFileItemWriterBuilder<CommonEntity>()
                .name("readDBAndWriterTxtFileItemWriter")
                // 写出path
                .resource(new FileSystemResource(generateFilePath))
                // 允许追加写入
                .append(true)
                .lineAggregator(lineAggregator)
                .headerCallback(h -> h.write("HEADER" + LocalDate.now().minusDays(1) + LocalDate.now()))
                .footerCallback(f -> f.write("tail" + countChunkListener.getCount()))
                .build();
    }

    @Bean
    public RepositoryItemReader<CommonEntity> readDBAndWriterDatFileItemReader(CommonRepository commonRepository) {
        return new RepositoryItemReaderBuilder<CommonEntity>()
                .name("readDBAndWriterDatFileItemReader")
                .repository(commonRepository)
                // 一次读取记录数量
                .pageSize(1000)
                // 查询方法
                .methodName("findAll")
                // 以字段id 排序，倒序
                .sorts(Collections.singletonMap("id", Sort.Direction.DESC))
                .build();
    }

    @Bean
    public CountChunkListener countChunkListener() {
        return new CountChunkListener();
    }

    static class CountChunkListener implements ChunkListener {
        private Integer count;

        @Override
        public void beforeChunk(ChunkContext chunkContext) {

        }

        @Override
        public void afterChunk(ChunkContext chunkContext) {
            this.count = chunkContext.getStepContext().getStepExecution().getReadCount();
            System.out.println(count);
        }

        @Override
        public void afterChunkError(ChunkContext chunkContext) {

        }

        public Integer getCount() {
            return count;
        }
    }

    @Bean
    public Job readDBAndWriteDatFileJob2(JobBuilderFactory jobBuilderFactory, Step readDBAndWriteDatFileStep2) {
        return jobBuilderFactory.get("readDBAndWriteDatFileJob2").start(readDBAndWriteDatFileStep2).build();
    }

    @Bean
    public Step readDBAndWriteDatFileStep2(StepBuilderFactory stepBuilderFactory) {
        return stepBuilderFactory.get("readDBAndWriteDatFileStep2")
                .tasklet(readDBAndWriteDatFileTasklet())
                .build();
    }

    @Bean
    public ReadDBAndWriteDatFileTasklet readDBAndWriteDatFileTasklet() {
        return new ReadDBAndWriteDatFileTasklet();
    }
}
