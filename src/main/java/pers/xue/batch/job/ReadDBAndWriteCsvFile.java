package pers.xue.batch.job;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.data.RepositoryItemReader;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author huangzhixue
 * @date 2022/1/17 3:47 下午
 * @Description
 * 从db 读取记录写入CSV file中
 */
@Configuration
public class ReadDBAndWriteCsvFile {

    /**
     * 目前使用相对路径，直接在项目名称下生成sample-data.csv，也可以使用绝对路径，/Users/huangzhixue/IdeaProjects/gather_all_single_function/src/main/resources/sample-data.csv
     */
    private String generateFilePath = "sample-data.csv";

    @Bean
    public Job readDBAndWriteCsvFileJob(JobBuilderFactory jobBuilderFactory, Step readDBAndWriteCsvFileStep) {
        return jobBuilderFactory.get("readDBAndWriteCsvFileJob").start(readDBAndWriteCsvFileStep).build();
    }

    /**
     * jpa 方式读取records，FlatFileItemWriter方式写出csv file
     * @param stepBuilderFactory
     * @return
     */
    @Bean
    public Step readDBAndWriteCsvFileStep(StepBuilderFactory stepBuilderFactory, ItemReader<CommonEntity> readDBAndWriterCsvFileItemReader,
                                          ItemWriter<CommonEntity> readDBAndWriterCsvFileItemWriter) {
        return stepBuilderFactory.get("readDBAndWriteCsvFileStep")
                .<CommonEntity, CommonEntity>chunk(1000)
                .reader(readDBAndWriterCsvFileItemReader)
                .writer(readDBAndWriterCsvFileItemWriter)
                .build();
    }

    @Bean
    public FlatFileItemWriter<CommonEntity> readDBAndWriterCsvFileItemWriter() {
        BeanWrapperFieldExtractor<CommonEntity> fieldExtractor = new BeanWrapperFieldExtractor<>();
        fieldExtractor.setNames(new String[] {"id", "content"});
        fieldExtractor.afterPropertiesSet();

        DelimitedLineAggregator<CommonEntity> lineAggregator = new DelimitedLineAggregator<>();
        lineAggregator.setDelimiter(",");
        lineAggregator.setFieldExtractor(fieldExtractor);

        return new FlatFileItemWriterBuilder<CommonEntity>()
                .name("readDBAndWriterCsvFileItemWriter")
                // 写出path
                .resource(new FileSystemResource(generateFilePath))
                // 不允许追加写入（默认是false）
                .append(false)
                .lineAggregator(lineAggregator)
                // 写入标题
                .headerCallback(writer -> {
                    writer.write("id,");
                    writer.write("content");
                })
                // 写入结尾
                //.footerCallback(writer -> {})
                .build();
    }

    @Bean
    public RepositoryItemReader<CommonEntity> readDBAndWriterCsvFileItemReader(CommonRepository commonRepository) {
        Map<String, Sort.Direction> map = new HashMap<>();
        // 以字段id 排序，倒序
        map.put("id", Sort.Direction.DESC);
        // 添加查询参数
        List<String> params = new ArrayList<>();
        params.add("神韵学Spring Batch");
        RepositoryItemReader<CommonEntity> repositoryItemReader = new RepositoryItemReader<>();
        repositoryItemReader.setRepository(commonRepository);
        repositoryItemReader.setPageSize(5);
        // 查询方法
        repositoryItemReader.setMethodName("findByContent");
        repositoryItemReader.setArguments(params);
        repositoryItemReader.setSort(map);
        return repositoryItemReader;
    }
}
