package pers.xue.batch.job;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.mapping.PatternMatchingCompositeLineMapper;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.batch.item.file.transform.FixedLengthTokenizer;
import org.springframework.batch.item.file.transform.LineTokenizer;
import org.springframework.batch.item.file.transform.Range;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindException;
import pers.xue.batch.entity.CommonEntity;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @author huangzhixue
 * @date 2022/7/11 16:47
 * @Description
 * 读取固定格式的txt文件
 * 跳过header和tail行
 * 1-2 表示id
 * 3-12表示content
 */
@Slf4j
@Configuration
public class ReadFixedWidthTxtFile {

    private JobBuilderFactory jobBuilderFactory;
    private StepBuilderFactory stepBuilderFactory;
    private String filePath = "files/fixed_widt.txt";

    public ReadFixedWidthTxtFile(JobBuilderFactory jobBuilderFactory, StepBuilderFactory stepBuilderFactory) {
        this.jobBuilderFactory = jobBuilderFactory;
        this.stepBuilderFactory = stepBuilderFactory;
    }

    @Bean
    public Job readFixedWidthTxtFileJob() {
        return jobBuilderFactory.get("readFixedWidthTxtFileJob")
                .start(readFixedWidthTxtFileStep())
                .build();
    }

    @Bean
    public Step readFixedWidthTxtFileStep() {
        return stepBuilderFactory.get("readFixedWidthTxtFileStep")
                .<CommonEntity, CommonEntity>chunk(10)
                .reader(readFixedWidthTxtFileReader())
                .processor(readFixedWidthTxtFileProcessor())
                .writer(readFixedWidthTxtFileWriter())
                .build();
    }

    @Bean
    public ItemWriter<CommonEntity> readFixedWidthTxtFileWriter() {
        return items -> items.forEach(i -> log.info("write record: {}", i));
    }

    @Bean
    public ItemProcessor<CommonEntity, CommonEntity>  readFixedWidthTxtFileProcessor() {
        return item -> {
            if (item.getId() == null) {
                return null;
            }
            log.info("process cord: {}", item);
            return item;
        };
    }

    @Bean
    public FlatFileItemReader<CommonEntity> readFixedWidthTxtFileReader() {
        return new FlatFileItemReaderBuilder<CommonEntity>()
                .name("readFixedWidthTxtFileReader")
                // 也可以跳过第一行，但是会丢失 log
//                .linesToSkip(1)
                .resource(new ClassPathResource(filePath))
                .lineMapper(readFixedWidthTxtFileLineMapper())
                .strict(false)
                .build();
    }

    @Bean
    public PatternMatchingCompositeLineMapper<CommonEntity> readFixedWidthTxtFileLineMapper() {
        // 匹配三种，header开头的，和tail开头的，前面两种没匹配到的走第三种匹配，即*代表全能匹配
        Map<String, LineTokenizer> tokenizers = new HashMap<>(3);
        tokenizers.put("header*", headerLineTokenizer2());
        tokenizers.put("tail*", tailLineTokenizer2());
        tokenizers.put("*", commonEntityLineTokenizer());

        PatternMatchingCompositeLineMapper<CommonEntity> pmc = new PatternMatchingCompositeLineMapper<>();
        pmc.setTokenizers(tokenizers);
        // 全部都用一种field set mapper
        pmc.setFieldSetMappers(Collections.singletonMap("*", commonFieldSetMapper()));
        return pmc;
    }

    @Bean
    public FixedLengthTokenizer headerLineTokenizer2() {
        FixedLengthTokenizer fixedLengthTokenizer = new FixedLengthTokenizer();
        // 将header和tail设置成id，这样就可以区分内容行和非内容行取值，从而进行后面的过滤
        fixedLengthTokenizer.setNames("id", "date");
        fixedLengthTokenizer.setColumns(new Range(1, 6), new Range(7, 16));
        return fixedLengthTokenizer;
    }

    @Bean
    public FixedLengthTokenizer tailLineTokenizer2() {
        FixedLengthTokenizer fixedLengthTokenizer = new FixedLengthTokenizer();
        // 将header和tail设置成id，这样只用一个field set mapper就可以取值
        fixedLengthTokenizer.setNames("id", "count");
        fixedLengthTokenizer.setColumns(new Range(1, 4), new Range(5));
        return fixedLengthTokenizer;
    }

    @Bean
    public FixedLengthTokenizer commonEntityLineTokenizer() {
        FixedLengthTokenizer fixedLengthTokenizer = new FixedLengthTokenizer();
        fixedLengthTokenizer.setNames("id", "content");
        fixedLengthTokenizer.setColumns(new Range(1, 2), new Range(3, 12));
        // 设置了strict属性，才能正确读，不然遇到长度不够的会error
        fixedLengthTokenizer.setStrict(false);
        return fixedLengthTokenizer;
    }

    @Bean
    public CommonFieldSetMapper commonFieldSetMapper() {
        return new CommonFieldSetMapper();
    }

    static class CommonFieldSetMapper implements FieldSetMapper<CommonEntity> {

        @Override
        public CommonEntity mapFieldSet(FieldSet fieldSet) {
            String id = fieldSet.readString("id");
            if ("header".equals(id)) {
                // 返回一个什么都没有的对象，在processor那边进行过滤，有很多方法可以做，比如填充一些约定值等..
                log.info("read header record: {}, {}", id, fieldSet.readString("date"));
                return new CommonEntity();
            }
            if ("tail".equals(id)) {
                // 返回一个什么都没有的对象，在processor那边进行过滤，有很多方法可以做，比如填充一些约定值等..
                log.info("read tail record: {}, {}", id, fieldSet.readString("count"));
                return new CommonEntity();
            }
            log.info("u/i common entity record, id: {}, content: {}", fieldSet.readInt("id"), fieldSet.readString("content"));
            return new CommonEntity(fieldSet.readInt("id"), fieldSet.readString("content"));
        }
    }

}
