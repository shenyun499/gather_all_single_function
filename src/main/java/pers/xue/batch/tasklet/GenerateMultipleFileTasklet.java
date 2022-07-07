package pers.xue.batch.tasklet;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.builder.FlatFileItemWriterBuilder;
import org.springframework.batch.item.file.transform.BeanWrapperFieldExtractor;
import org.springframework.batch.item.file.transform.DelimitedLineAggregator;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Component;
import pers.xue.batch.entity.CommonEntity;
import pers.xue.batch.repository.CommonRepository;

import java.util.List;

/**
 * @author huangzhixue
 * @date 2022/7/5 14:50
 * @Description
 */
@Slf4j
@Component
public class GenerateMultipleFileTasklet implements Tasklet {

    @Autowired
    private CommonRepository commonRepository;

    private String generateFilePath = "src/main/resources/files/generate-data-tasklet.txt";

    @Override
    public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {
        List<CommonEntity> commonEntities = commonRepository.findAll();
        generateFile(CommonEntity.class, new String[]{"id", "content"}, commonEntities, generateFilePath, stepContribution);

        // 其他需要生成的表 可以继续加...
        return RepeatStatus.FINISHED;
    }

    <T> void generateFile(Class<T> t, String[] fieldNames, List<T> data, String path, StepContribution stepContribution) {
        // 设置字段名称
        BeanWrapperFieldExtractor<T> tBeanWrapperFieldExtractor = new BeanWrapperFieldExtractor<>();
        tBeanWrapperFieldExtractor.setNames(fieldNames);

        // 设置分隔符，bean
        DelimitedLineAggregator<T> tDelimitedLineAggregator = new DelimitedLineAggregator<>();
        tDelimitedLineAggregator.setDelimiter(",");
        tDelimitedLineAggregator.setFieldExtractor(tBeanWrapperFieldExtractor);

        FlatFileItemWriter<T> tFlatFileItemWriter = new FlatFileItemWriterBuilder<T>()
                .name(t.getName())
                .lineAggregator(tDelimitedLineAggregator)
                .resource(new FileSystemResource(path))
                .headerCallback(h -> h.write("header"))
                .footerCallback(f -> f.write("footer"))
                .build();

        try {
            tFlatFileItemWriter.open(stepContribution.getStepExecution().getExecutionContext());
            tFlatFileItemWriter.write(data);
        } catch (Exception e) {
            log.error("generate file fail, path: {}", path, e);
        } finally {
            tFlatFileItemWriter.close();
        }
        log.info("generate file is: {}", path);
    }
}
