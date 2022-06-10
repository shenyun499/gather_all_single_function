package pers.xue.batch.tasklet;

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
import pers.xue.batch.entity.CommonEntity;
import pers.xue.batch.repository.CommonRepository;

import java.time.LocalDate;
import java.util.List;

/**
 * 读取db，并写入 dat文件
 *
 * @author ：HUANG ZHI XUE
 * @date ：Create in 2022-05-09
 */
public class ReadDBAndWriteDatFileTasklet implements Tasklet {

    @Autowired
    private CommonRepository commonRepository;

    @Override
    public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {
        List<CommonEntity> commonEntitys = commonRepository.findAll();
        BeanWrapperFieldExtractor<CommonEntity> fieldExtractor = new BeanWrapperFieldExtractor<>();
        fieldExtractor.setNames(new String[] {"id", "content"});
        fieldExtractor.afterPropertiesSet();

        DelimitedLineAggregator<CommonEntity> lineAggregator = new DelimitedLineAggregator<>();
        lineAggregator.setDelimiter("|");
        lineAggregator.setFieldExtractor(fieldExtractor);

        FlatFileItemWriter<CommonEntity> readDBAndWriterTxtFileItemWriter = new FlatFileItemWriterBuilder<CommonEntity>()
                .name("readDBAndWriterTxtFileItemWriter")
                // 写出path
                .resource(new FileSystemResource("ABC_20220509.dat"))
                // 允许追加写入
                .append(true)
                .lineAggregator(lineAggregator)
                .headerCallback(h -> h.write("HEADER" + LocalDate.now().minusDays(1) + LocalDate.now()))
                .footerCallback(f -> f.write("tail" + commonEntitys.size()))
                .build();
        readDBAndWriterTxtFileItemWriter.open(stepContribution.getStepExecution().getExecutionContext());
        readDBAndWriterTxtFileItemWriter.write(commonEntitys);
        readDBAndWriterTxtFileItemWriter.close();
        return RepeatStatus.FINISHED;
    }
}
