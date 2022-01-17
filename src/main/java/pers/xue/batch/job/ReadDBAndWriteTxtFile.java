package pers.xue.batch.job;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
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

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author huangzhixue
 * @date 2021/12/23 5:35 下午
 * @Description
 * 这个类的作用：从db读取记录，然后写入txt 文件中
 *  通过jpa repository指定方法和参数读取记录，这个过程是reader
 *  通过FlatFileItemWriter指定bean 并设置field name和写入path将文件写入，这个过程是writer
 *  (https://docs.spring.io/spring-batch/docs/4.3.x/reference/html/readersAndWriters.html#SimplifiedFileWritingExample)
 *
 *  process:
 *      1、使用ClassPathResource 并将创建好的file放到classpath路径下没有反应
 *      2、path写错了，使用FileSystemResource竟然不报错，因为它在相对路径下面创建了一个file并写入，
 *      我还以为FlatFileItemWriter必须要和FlatFileItemReader一起使用，后面更正了路径发现能写入
 */
@Configuration
public class ReadDBAndWriteTxtFile {

    /**
     * 目前使用相对路径，直接在项目名称下生成sample-data.txt，也可以使用绝对路径，/Users/huangzhixue/IdeaProjects/gather_all_single_function/src/main/resources/sample-data.txt
     */
    private String generateFilePath = "sample-data.txt";

    @Bean
    public Job readDBAndWriteTxtFileJob(JobBuilderFactory jobBuilderFactory, Step readDBAndWriteTxtFileStep) {
        return jobBuilderFactory.get("readDBAndWriteTxtFileJob").start(readDBAndWriteTxtFileStep).build();
    }

    /**
     * jpa 方式读取records，FlatFileItemWriter方式写出txt file
     * @param stepBuilderFactory
     * @return
     */
    @Bean
    public Step readDBAndWriteTxtFileStep(StepBuilderFactory stepBuilderFactory, CommonRepository commonRepository) {
        return stepBuilderFactory.get("readDBAndWriteFileStep")
                .<CommonEntity, CommonEntity>chunk(1)
                .reader(readDBAndWriterTxtFileItemReader(commonRepository))
                .writer(readDBAndWriterTxtFileItemWriter())
                .build();
    }

    @Bean
    public FlatFileItemWriter<CommonEntity> readDBAndWriterTxtFileItemWriter() {
        BeanWrapperFieldExtractor<CommonEntity> fieldExtractor = new BeanWrapperFieldExtractor<>();
        fieldExtractor.setNames(new String[] {"id", "credit"});
        fieldExtractor.afterPropertiesSet();

        DelimitedLineAggregator<CommonEntity> lineAggregator = new DelimitedLineAggregator<>();
        lineAggregator.setDelimiter(",");
        lineAggregator.setFieldExtractor(fieldExtractor);

        return new FlatFileItemWriterBuilder<CommonEntity>()
                .name("readDBAndWriterTxtFileItemWriter")
                // 写出path
                .resource(new FileSystemResource(generateFilePath))
                .encoding(StandardCharsets.UTF_8.name())
                // 允许追加写入
                .append(true)
                .lineAggregator(lineAggregator)
                .build();
    }

    @Bean
    public RepositoryItemReader<CommonEntity> readDBAndWriterTxtFileItemReader(CommonRepository commonRepository) {
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
