package pers.xue.batch.job;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.data.RepositoryItemWriter;
import org.springframework.batch.item.data.builder.RepositoryItemWriterBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.mapping.PatternMatchingCompositeLineMapper;
import org.springframework.batch.item.file.transform.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.task.TaskExecutor;
import org.springframework.validation.BindException;
import pers.xue.batch.entity.CommonEntity;
import pers.xue.batch.repository.CommonRepository;

import java.util.HashMap;

/**
 * @author huangzhixue
 * @date 2022/7/7 17:00
 * @Description
 * 读取前面生成的dat文件写入db，会识别header tail，识别正文间隙是'，'
 */
@Slf4j
@Configuration
public class ReadMultiFormattedDatFile {

    @Autowired
    private CommonRepository commonRepository;

    private String path = "files/ABC_20220707.dat";

    @Bean
    public Job readMultiFormattedDatFileJob(JobBuilderFactory jobBuilderFactory, Step readMultiFormattedDatFileStep) {
        return jobBuilderFactory.get("readMultiFormattedDatFileJob")
                .start(readMultiFormattedDatFileStep)
                .build();
    }

    @Bean
    public Step readMultiFormattedDatFileStep(StepBuilderFactory stepBuilderFactory,
                                              ItemReader<CommonEntity> readMultiFormattedDatFileReader,
                                              ItemWriter<CommonEntity> readMultiFormattedDatFileWriter,
                                              TaskExecutor taskExecutor) {
        return stepBuilderFactory.get("readMultiFormattedDatFileStep")
                .<CommonEntity, CommonEntity>chunk(10)
                .reader(readMultiFormattedDatFileReader)
                .writer(readMultiFormattedDatFileWriter)
                .taskExecutor(taskExecutor)
//                .throttleLimit(1)
                .build();
    }

    @Bean
    public FlatFileItemReader<CommonEntity> readMultiFormattedDatFileReader() {
        return new FlatFileItemReaderBuilder<CommonEntity>()
                .name("readMultiFormattedDatFileReader")
                .lineMapper(patternMatchingCompositeLineMapper())
                .resource(new ClassPathResource(path))
                .saveState(false)
                .build();
    }

    @Bean
    public PatternMatchingCompositeLineMapper<CommonEntity> patternMatchingCompositeLineMapper() {
        // link - https://docs.spring.io/spring-batch/docs/4.3.x/reference/html/readersAndWriters.html#prefixMatchingLineMapper
        PatternMatchingCompositeLineMapper<CommonEntity> pmc = new PatternMatchingCompositeLineMapper<>();
        HashMap<String, LineTokenizer> lineTokenizerHashMap = new HashMap<>(3);
        // 第一个映射以HEADER开头的行，使用headerLineTokenizer()-FixedLengthTokenizer
        lineTokenizerHashMap.put("HEADER*", headerLineTokenizer());
        // 第二个映射tail开头的行，使用tailLineTokenizer()-FixedLengthTokenizer
        lineTokenizerHashMap.put("tail*", tailLineTokenizer());
        // 第三个映射前面两种没映射到的行，使用otherLineTokenizer()-DelimitedLineTokenizer
        lineTokenizerHashMap.put("*", otherLineTokenizer());

        // 注意：下面将定义三个setMapper，其实最好的做法是将header和tail的name设置成和commonEntity对象首个属性一样名称(id)，这样只需要一个setMapper对象，然后拿到id，判断是不是以header开头或者tail开头分别进行不同的处理
        HashMap<String, FieldSetMapper<CommonEntity>> fieldSetMapperHashMap = new HashMap<>(3);
        fieldSetMapperHashMap.put("HEADER*", headerFieldSetMapper());
        fieldSetMapperHashMap.put("tail*", tailFieldSetMapper());
        fieldSetMapperHashMap.put("*", fieldSetMapper());

        pmc.setTokenizers(lineTokenizerHashMap);
        pmc.setFieldSetMappers(fieldSetMapperHashMap);
        return pmc;
    }

    @Bean
    public FieldSetMapper<CommonEntity> fieldSetMapper() {
        return new CommonFieldSetMapper();
    }

    @Bean
    public FieldSetMapper<CommonEntity> headerFieldSetMapper() {
        return new CommonHeaderFieldSetMapper();
    }

    @Bean
    public FieldSetMapper<CommonEntity> tailFieldSetMapper() {
        return new CommonTailFieldSetMapper();
    }

    @Bean
    public FixedLengthTokenizer headerLineTokenizer() {
        FixedLengthTokenizer fixedLengthTokenizer = new FixedLengthTokenizer();
        fixedLengthTokenizer.setNames(new String[]{"header", "firstDate", "secondDate"});
        fixedLengthTokenizer.setColumns(new Range[]{new Range(1,6), new Range(7, 16), new Range(17, 26)});
        fixedLengthTokenizer.setStrict(false);
        return fixedLengthTokenizer;
    }

    @Bean
    public FixedLengthTokenizer tailLineTokenizer() {
        FixedLengthTokenizer fixedLengthTokenizer = new FixedLengthTokenizer();
        fixedLengthTokenizer.setNames(new String[]{"tail", "totalCount"});
        fixedLengthTokenizer.setColumns(new Range[]{new Range(1, 4), new Range(5)});
        return fixedLengthTokenizer;
    }

    @Bean
    public DelimitedLineTokenizer otherLineTokenizer() {
        DelimitedLineTokenizer delimitedLineTokenizer = new DelimitedLineTokenizer();
        delimitedLineTokenizer.setNames(new String[]{"id", "content"});
        delimitedLineTokenizer.setDelimiter("|");
        return delimitedLineTokenizer;
    }

    @Bean
    public RepositoryItemWriter<CommonEntity> readMultiFormattedDatFileWriter() {
        return new RepositoryItemWriterBuilder<CommonEntity>()
                .repository(commonRepository)
                .methodName("save")
                .build();
    }

    static class CommonFieldSetMapper implements FieldSetMapper<CommonEntity> {

        @Override
        public CommonEntity mapFieldSet(FieldSet fieldSet) throws BindException {
            log.info("u/i common entity record, id: {}, content: {}", fieldSet.readInt("id"), fieldSet.readString("content"));
            return new CommonEntity(fieldSet.readInt("id"), fieldSet.readString("content"));
        }
    }

    static class CommonHeaderFieldSetMapper implements FieldSetMapper<CommonEntity> {

        @Override
        public CommonEntity mapFieldSet(FieldSet fieldSet) {
            // header handle
            log.info("skip header record, first date: {}, second date: {}", fieldSet.readString("firstDate"), fieldSet.readString("secondDate"));
            return null;
        }
    }

    static class CommonTailFieldSetMapper implements FieldSetMapper<CommonEntity> {

        @Override
        public CommonEntity mapFieldSet(FieldSet fieldSet) {
            // tail handle
            log.info("skip tail record, total count: {}", fieldSet.readString("totalCount"));
            return null;
        }
    }

}
