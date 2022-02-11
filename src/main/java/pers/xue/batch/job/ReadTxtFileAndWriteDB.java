package pers.xue.batch.job;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.core.io.ClassPathResource;
import pers.xue.batch.entity.CommonEntity;
import pers.xue.batch.writer.CommonEntityItemWriter;

/**
 * @author huangzhixue
 * @date 2022/1/21 2:28 下午
 * @Description
 * 读取txt file， 并写入db
 *
 * 官网对文件有两种解析，意思提供 固定分隔符/固定长度 解析file
 * https://docs.spring.io/spring-batch/docs/4.3.x/reference/html/readersAndWriters.html#lineTokenizer
 * DelimitedLineTokenizer: Used for files where fields in a record are separated by a delimiter. The most common delimiter is a comma, but pipes or semicolons are often used as well.
 * FixedLengthTokenizer: Used for files where fields in a record are each a "fixed width". The width of each field must be defined for each record type
 *
 * for example: https://docs.spring.io/spring-batch/docs/4.3.x/reference/html/readersAndWriters.html#simpleDelimitedFileReadingExample
 *
 * 字段映射成Object，官方介绍了三种方式
 * 1、通过索引，这样子比较简单，但是需要确保字段索引的位置，这样LineTokenizer，就可以配置为lineMapper.setLineTokenizer(new DelimitedLineTokenizer());
 * Simple Delimited File Reading Example --- https://docs.spring.io/spring-batch/docs/4.3.x/reference/html/readersAndWriters.html#simpleDelimitedFileReadingExample
 * 2、通过字段名称，但是需要配置LineTokenizer，tokenizer.setNames(new String[] {"id", "content"});
 * Mapping Fields by Name --- https://docs.spring.io/spring-batch/docs/4.3.x/reference/html/readersAndWriters.html#mappingFieldsByName
 * 3、通过注入bean，通过BeanWrapperFieldSetMapper去set注入需要解析的object bean名称
 * Automapping FieldSets to Domain Objects ---- https://docs.spring.io/spring-batch/docs/4.3.x/reference/html/readersAndWriters.html#beanWrapperFieldSetMapper
 */
@Slf4j
@Configuration
public class ReadTxtFileAndWriteDB {

    private String generateFilePath = "files/sample-data.txt";

    @Bean
    public Job readTxtFileAndWriteDBJob(JobBuilderFactory jobBuilderFactory, Step readTxtFileAndWriteDBStep) {
        return jobBuilderFactory.get("readTxtFileAndWriteDBJob").start(readTxtFileAndWriteDBStep).build();
    }

    /**
     * txt 方式读取records，自定义类写出db
     * @param stepBuilderFactory
     * @return
     */
    @Bean
    public Step readTxtFileAndWriteDBStep(StepBuilderFactory stepBuilderFactory) {
        return stepBuilderFactory.get("readTxtFileAndWriteDBStep")
                .<CommonEntity, CommonEntity>chunk(1000)
                .reader(objectReadTxtFileItemReader())
                .writer(commonEntityItemWriter02())
                .build();
    }

    @Bean
    public ItemReader<CommonEntity> indexReadTxtFileItemReader() {
        DefaultLineMapper<CommonEntity> defaultLineMapper = new DefaultLineMapper<>();
        // 默认的DelimitedLineTokenizer，分隔符delimiter为逗号','，引号包裹'"'。
        defaultLineMapper.setLineTokenizer(new DelimitedLineTokenizer());
        defaultLineMapper.setFieldSetMapper(new IndexCommonEntitySetMapper());

        return new FlatFileItemReaderBuilder<CommonEntity>()
                .name("indexReadCsvFileAndWriteDBReader")
                .resource(new ClassPathResource(generateFilePath))
                // 跳过第一行，标题行
                .linesToSkip(1)
                // 定义文件的注释行 也就是 读取时 会忽略的内容, 可以用来过滤标题行
                //.comments(new String[]{"id"})
                .lineMapper(defaultLineMapper)
                .build();
    }

    /**
     * 索引映射
     */
    protected static class IndexCommonEntitySetMapper implements FieldSetMapper<CommonEntity> {
        @Override
        public CommonEntity mapFieldSet(FieldSet fieldSet) {
            return CommonEntity
                    .builder()
                    // 用index(字段多index不好确定，不太建议, lineMapper.setLineTokenizer(new DelimitedLineTokenizer());)
                    .id(fieldSet.readInt(0))
                    .content(fieldSet.readString(1))
                    .build();
        }
    }

    @Bean
    public ItemReader<CommonEntity> nameReadTxtFileItemReader() {
        DefaultLineMapper<CommonEntity> defaultLineMapper = new DefaultLineMapper<>();
        // 默认的DelimitedLineTokenizer，分隔符delimiter为逗号','，引号包裹'"'。
        DelimitedLineTokenizer tokenizer = new DelimitedLineTokenizer();
        tokenizer.setNames("id", "content");
        defaultLineMapper.setLineTokenizer(tokenizer);
        // 读取后的字段与属性映射，set 值
        defaultLineMapper.setFieldSetMapper(new NameCommonEntitySetMapper());

        return new FlatFileItemReaderBuilder<CommonEntity>()
                .name("nameReadTxtFileItemReader")
                .resource(new ClassPathResource(generateFilePath))
                // 跳过第一行，标题行
                .linesToSkip(1)
                // 定义文件的注释行 也就是 读取时 会忽略的内容, 可以用来过滤标题行
                //.comments(new String[]{"id"})
                .lineMapper(defaultLineMapper)
                .build();
    }

    /**
     * 字段名称映射
     */
    protected static class NameCommonEntitySetMapper implements FieldSetMapper<CommonEntity> {
        @Override
        public CommonEntity mapFieldSet(FieldSet fieldSet) {
            log.info("line :{}", fieldSet.getFieldCount());
            return CommonEntity
                    .builder()
                    // 用字段name（前提tokenizer.setNames(new String[] {"id", "content"});）
                    .id(fieldSet.readInt("id"))
                    .content(fieldSet.readString("content"))
                    .build();
        }
    }

    @Bean
    public ItemReader<CommonEntity> objectReadTxtFileItemReader() {
        DefaultLineMapper<CommonEntity> defaultLineMapper = new DefaultLineMapper<>();
        // 默认的DelimitedLineTokenizer，分隔符delimiter为逗号','，引号包裹'"'。
        DelimitedLineTokenizer tokenizer = new DelimitedLineTokenizer();
        tokenizer.setNames("id", "content");
        defaultLineMapper.setLineTokenizer(tokenizer);
        // 读取后的字段与属性映射，set 值
        // object 映射方式
        defaultLineMapper.setFieldSetMapper(beanWrapperFieldSetMapper());

        return new FlatFileItemReaderBuilder<CommonEntity>()
                .name("objectReadTxtFileItemReader")
                .resource(new ClassPathResource(generateFilePath))
                // 跳过第一行，标题行
                .linesToSkip(1)
                // 定义文件的注释行 也就是 读取时 会忽略的内容, 可以用来过滤标题行
                //.comments(new String[]{"id"})
                .lineMapper(defaultLineMapper)
                .build();
    }

    /**
     * 试过不是通过bean的方式注入BeanWrapperFieldSetMapper，解析时竟然报错...
     * @return
     */
    @Bean
    public BeanWrapperFieldSetMapper beanWrapperFieldSetMapper() {
        BeanWrapperFieldSetMapper fieldSetMapper = new BeanWrapperFieldSetMapper();
        fieldSetMapper.setPrototypeBeanName("objectCommonEntitySetMapper");
        return fieldSetMapper;
    }

    /**
     * bean- object映射
     */
    @Bean
    @Scope("prototype")
    public CommonEntity  objectCommonEntitySetMapper() {
        return new CommonEntity();
    }

    /**
     * 写入db
     * @return ItemWriter
     */
    @Bean
    public ItemWriter<CommonEntity> commonEntityItemWriter02() {
        return new CommonEntityItemWriter();
    }
}
