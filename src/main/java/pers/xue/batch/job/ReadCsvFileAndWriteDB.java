package pers.xue.batch.job;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.data.RepositoryItemWriter;
import org.springframework.batch.item.data.builder.RepositoryItemWriterBuilder;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import pers.xue.batch.entity.CommonEntity;
import pers.xue.batch.repository.CommonRepository;

/**
 * @author huangzhixue
 * @date 2022/2/8 2:42 下午
 * @Description
 * 读取带标题的csv文件，经过processor处理后再写入数据库
 * DelimitedLineTokenizer: 与文件的映射读取
 * FieldSetMapper：已经读到值，这个是与对象的映射，将值填充到对象中
 *
 */
@Slf4j
@Configuration
public class ReadCsvFileAndWriteDB {

    private String generateFilePath = "files/sample-data.csv";

    @Autowired
    CommonRepository commonRepository;

    @Bean
    public Job readCsvFileAndWriteDBJob(JobBuilderFactory jobBuilderFactory, Step readCsvFileAndWriteDBStep) {
        return jobBuilderFactory.get("readCsvFileAndWriteDBJob").start(readCsvFileAndWriteDBStep).build();
    }

    @Bean
    public Step readCsvFileAndWriteDBStep(StepBuilderFactory stepBuilderFactory, ItemReader<CommonEntity> readCsvFileAndWriteDBReader
            , ItemWriter<CommonEntity> readCsvFileAndWriteDBWriter) {
        return stepBuilderFactory.get("readCsvFileAndWriteDBStep")
                .<CommonEntity, CommonEntity>chunk(5)
                // 读取数据
                .reader(readCsvFileAndWriteDBReader)
                .processor(readCsvFileAndWriteDBProcessor())
                // 写入数据
                .writer(readCsvFileAndWriteDBWriter)
                .build();
    }

    /**
     *  csv 文件读取
     * @return
     */
    @Bean
    public ItemReader<CommonEntity> readCsvFileAndWriteDBReader() {
        DefaultLineMapper<CommonEntity> defaultLineMapper = new DefaultLineMapper<>();
        // 默认的DelimitedLineTokenizer，分隔符delimiter为逗号','，引号包裹'"'。
        DelimitedLineTokenizer tokenizer = new DelimitedLineTokenizer();
        tokenizer.setNames("id", "content");
        defaultLineMapper.setLineTokenizer(tokenizer);
        // 读取后的字段与属性映射，set 值
        defaultLineMapper.setFieldSetMapper(new NameCommonEntitySetMapper());

        return new FlatFileItemReaderBuilder<CommonEntity>()
                .name("readCsvFileAndWriteDBReader")
                .resource(new ClassPathResource(generateFilePath))
                // 跳过第一行，标题行
                .linesToSkip(1)
                .lineMapper(defaultLineMapper)
                .build();
    }

    public ItemProcessor<CommonEntity, CommonEntity> readCsvFileAndWriteDBProcessor() {
        return new ReadCsvFileAndWriteDB.ReadDBAndWriteDBByRepositoryProcesstor();
    }

    @Bean
    public RepositoryItemWriter<CommonEntity> readCsvFileAndWriteDBWriter() {
        return new RepositoryItemWriterBuilder<CommonEntity>()
                .repository(commonRepository)
                .methodName("save")
                .build();
    }

    private static class ReadDBAndWriteDBByRepositoryProcesstor implements ItemProcessor<CommonEntity, CommonEntity> {
        @Override
        public CommonEntity process(CommonEntity commonEntity) {
            // reset CommonEntity, make a action for update, can do more things..
            commonEntity.setContent("22222");
            log.info("--------------common entity: {}", commonEntity.toString());
            return commonEntity;
        }
    }

    /**
     * 字段名称映射
     */
    private static class NameCommonEntitySetMapper implements FieldSetMapper<CommonEntity> {
        @Override
        public CommonEntity mapFieldSet(FieldSet fieldSet) {
            return CommonEntity
                    .builder()
                    // 用字段name（前提tokenizer.setNames(new String[] {"id", "content"});）
                    .id(fieldSet.readInt("id"))
                    .content(fieldSet.readString("content"))
                    .build();
        }
    }

}
