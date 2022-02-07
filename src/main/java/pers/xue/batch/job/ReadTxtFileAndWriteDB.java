package pers.xue.batch.job;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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
 * 2、通过字段名称，但是需要配置LineTokenizer，tokenizer.setNames(new String[] {"id", "content"});
 * 3、通过注入bean，通过BeanWrapperFieldSetMapper去set注入需要解析的object bean名称
 */
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
                .reader(readTxtFileItemReader())
                .writer(commonEntityItemWriter02())
                .build();
    }

    @Bean
    public ItemReader<CommonEntity> readTxtFileItemReader() {
        FlatFileItemReader<CommonEntity> itemReader = new FlatFileItemReader<>();
        itemReader.setResource(new ClassPathResource(generateFilePath));
        DefaultLineMapper<CommonEntity> lineMapper = new DefaultLineMapper<>();
        // 默认的DelimitedLineTokenizer，分隔符delimiter为逗号','，引号包裹'"'。
        DelimitedLineTokenizer delimitedLineTokenizer = new DelimitedLineTokenizer();
        delimitedLineTokenizer.setQuoteCharacter('"');
        lineMapper.setLineTokenizer(delimitedLineTokenizer);
        lineMapper.setFieldSetMapper(new CommonEntitySetMapper());
        itemReader.setLineMapper(lineMapper);
        itemReader.open(new ExecutionContext());
        // 定义文件的注释行 也就是 读取时 会忽略的内容, 可以用来过滤标题行
        itemReader.setComments(new String[]{"id"});
        return itemReader;
    }

    protected static class CommonEntitySetMapper implements FieldSetMapper<CommonEntity> {
        @Override
        public CommonEntity mapFieldSet(FieldSet fieldSet) {
            return CommonEntity
                    .builder()
                    // 可以用字段name（前提tokenizer.setNames(new String[] {"id", "content"});）,也可以用index(字段多index不好确定，不太建议, lineMapper.setLineTokenizer(new DelimitedLineTokenizer());)
                    //.id(fieldSet.readInt("id"))
                    //.content(fieldSet.readString("content"))
                    .id(fieldSet.readInt(0))
                    .content(fieldSet.readString(1))
                    .build();
        }
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
