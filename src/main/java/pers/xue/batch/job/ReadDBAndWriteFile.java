package pers.xue.batch.job;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.data.RepositoryItemReader;
import org.springframework.batch.item.file.FlatFileItemWriter;
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
 * @date 2021/12/23 5:35 下午
 * @Description
 * 这个类的作用：从db读取记录，然后写入txt 文件中
 *  通过jpa repository指定方法和参数读取记录，这个过程是reader
 *  通过FlatFileItemWriter指定bean 并设置field name和写入path将文件写入，这个过程是writer
 *
 *  process:
 *      1、使用ClassPathResource 并将创建好的file放到classpath路径下没有反应
 *      2、path写错了，使用FileSystemResource竟然不报错，因为它在相对路径下面创建了一个file并写入，
 *      我还以为FlatFileItemWriter必须要和FlatFileItemReader一起使用，后面更正了路径发现能写入
 */
@Configuration
public class ReadDBAndWriteFile {

    // 注释的是绝对路径，目前使用相对路径，直接在项目名称下生成sample-data.txt
    //private String generateFilePath = "/Users/huangzhixue/IdeaProjects/gather_all_single_function/src/main/resources/sample-data.txt";
    private String generateFilePath = "sample-data.txt";

    @Bean
    public Job readDBAndWriteFileJob(JobBuilderFactory jobBuilderFactory, Step readDBAndWriteFileStep) {
        return jobBuilderFactory.get("readDBAndWriteFileJob").start(readDBAndWriteFileStep).build();
    }

    /**
     * jpa 方式读取records，FlatFileItemWriter方式写出txt file
     * @param stepBuilderFactory
     * @return
     */
    @Bean
    public Step readDBAndWriteFileStep(StepBuilderFactory stepBuilderFactory, CommonRepository commonRepository) {
        return stepBuilderFactory.get("readDBAndWriteFileStep")
                .<CommonEntity, CommonEntity>chunk(1)
                .reader(readDBAndWriterFileItemReader(commonRepository))
                .writer(readDBAndWriterFileItemWriter())
                .build();
    }

    @Bean
    public FlatFileItemWriter<CommonEntity> readDBAndWriterFileItemWriter() {
        FlatFileItemWriter<CommonEntity> txtItemWriter = new FlatFileItemWriter<>();
        // 允许追加写入 file
        txtItemWriter.setAppendAllowed(true);
        txtItemWriter.setEncoding("UTF-8");
        // fileSystemResource = new FileSystemResource("D:\\aplus\\shuqian\\target\\"+clz.getSimpleName()+".csv");
        txtItemWriter.setResource(new FileSystemResource(generateFilePath));
        txtItemWriter.setLineAggregator(new DelimitedLineAggregator<CommonEntity>() {{
            // 字段生成分隔符
            setDelimiter(",");
            // 字段映射
            setFieldExtractor(new BeanWrapperFieldExtractor<CommonEntity>() {{
                setNames(new String[]{"id", "content"});
            }});
        }});
        return txtItemWriter;
    }

    @Bean
    public RepositoryItemReader<CommonEntity> readDBAndWriterFileItemReader(CommonRepository commonRepository) {
        Map<String, Sort.Direction> map = new HashMap<>();
        // 以字段id 排序，倒序
        map.put("id", Sort.Direction.DESC);
        // 添加查询参数
        List<String> params = new ArrayList();
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
