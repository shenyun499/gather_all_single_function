package pers.xue.batch.job;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.builder.FlatFileItemWriterBuilder;
import org.springframework.batch.item.file.transform.BeanWrapperFieldExtractor;
import org.springframework.batch.item.file.transform.FormatterLineAggregator;
import org.springframework.batch.item.support.ListItemReader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import pers.xue.batch.entity.CommonEntity;
import pers.xue.batch.repository.CommonRepository;

import java.time.LocalDate;
import java.util.Arrays;

/**
 * @author huangzhixue
 * @date 2022/7/11 15:10
 * @Description
 * 生成目标txt文件，带header和tail，生成固定长度来生成
 * id生成 1-2 即%-2s
 * content生成3-12  即%-10%
 */
@Slf4j
@Configuration
public class GenerateFixedWidthTxtFile {
    private JobBuilderFactory jobBuilderFactory;
    private StepBuilderFactory stepBuilderFactory;
    private CommonRepository commonRepository;
    private int count;
    private String generateFilePath = "src/main/resources/files/fixed_width.txt";

    public GenerateFixedWidthTxtFile(JobBuilderFactory jobBuilderFactory, StepBuilderFactory stepBuilderFactory, CommonRepository commonRepository) {
        this.jobBuilderFactory = jobBuilderFactory;
        this.stepBuilderFactory = stepBuilderFactory;
        this.commonRepository = commonRepository;
    }

    @Bean
    public Job generateFixedWidthTxtFileJob() {
        return jobBuilderFactory.get("generateFixedWidthTxtFileJob")
                .start(generateFixedWidthTxtFileStep())
                .build();
    }

    @Bean
    public Step generateFixedWidthTxtFileStep() {
        return stepBuilderFactory.get("generateFixedWidthTxtFileStep")
                .<CommonEntity, CommonEntity>chunk(1000)
                .reader(generateFixedWidthTxtFileReader())
//                .writer(generateFixedWidthTxtFileWriter())
                .writer(generateFixedWidthTxtFileWriter2())
                .build();
    }

    @Bean
    public FlatFileItemWriter<CommonEntity> generateFixedWidthTxtFileWriter() {
            return new FlatFileItemWriterBuilder<CommonEntity>()
                    .name("generateFixedWidthTxtFileWriter")
                    .resource(new FileSystemResource(generateFilePath))
                    .headerCallback(h -> h.write("header" + LocalDate.now()))
                    .footerCallback(f -> f.write("tail" + count))
                    .formatted()
                    // %-3 是指id字段占位三个空格， %-10 是指content占位10个字段，但是作为最后一个字段，如果它比10还要长也会输出超出的那部分内容
                    .format("%-2s%-3s")
                    .names("id", "content")
                    .build();
    }

    @Bean
    public FlatFileItemWriter<CommonEntity> generateFixedWidthTxtFileWriter2() {
        BeanWrapperFieldExtractor<CommonEntity> fieldExtractor = new BeanWrapperFieldExtractor<>();
        fieldExtractor.setNames(new String[] {"id", "content"});
        fieldExtractor.afterPropertiesSet();

        FormatterLineAggregator<CommonEntity> lineAggregator = new FormatterLineAggregator<>();
        lineAggregator.setFormat("%-2s%-3s");
        lineAggregator.setFieldExtractor(fieldExtractor);

        return new FlatFileItemWriterBuilder<CommonEntity>()
                .name("generateFixedWidthTxtFileWriter2")
                .resource(new FileSystemResource(generateFilePath))
                .lineAggregator(lineAggregator)
                .headerCallback(h -> h.write("header" + LocalDate.now()))
                .footerCallback(f -> f.write("tail" + count))
                .build();
    }

    @Bean
    public ListItemReader<CommonEntity> generateFixedWidthTxtFileReader() {
        return new ListItemReader<CommonEntity>(Arrays.asList(
                new CommonEntity(null, "test1"), new CommonEntity(2, "test22"), new CommonEntity(3, "test333")
        )) {
            @Override
            public CommonEntity read() {
                CommonEntity commonEntity = super.read();
                if (commonEntity != null) {
                    count++;
                    log.info("read record : {}", commonEntity);
                }
                return commonEntity;
            }
        };
    }
}
