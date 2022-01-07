package pers.xue.batch.job;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.data.RepositoryItemReader;
import org.springframework.batch.item.json.JsonFileItemWriter;
import org.springframework.batch.item.json.JsonObjectMarshaller;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Sort;
import pers.xue.batch.entity.CommonEntity;
import pers.xue.batch.repository.CommonRepository;
import pers.xue.batch.writer.ReadDBAndWriteJsonFileWriter;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.HashMap;

/**
 * @author huangzhixue
 * @date 2022/1/7 11:40 上午
 * @Description
 */
@Configuration
public class ReadDBAndWriteJsonFile {

    private String generateFilePath = "sample-data.json";

    @Bean
    public Job readDBAndWriteJsonFileJob(JobBuilderFactory jobBuilderFactory, Step readDBAndWriteJsonFileStep) {
        return jobBuilderFactory.get("readDBAndWriteJsonFileJob").start(readDBAndWriteJsonFileStep).build();
    }

    @Bean
    public Step readDBAndWriteJsonFileStep(StepBuilderFactory stepBuilderFactory, ItemReader<CommonEntity> readDBAndWriteJsonFileReader) {
        return stepBuilderFactory.get("readDBAndWriteJsonFileStep")
                .<CommonEntity, CommonEntity>chunk(1000)
                .reader(readDBAndWriteJsonFileReader)
                .writer(readDBAndWriteJsonFileWriter())
                .build();
    }

    @Bean
    public ItemReader<CommonEntity> readDBAndWriteJsonFileReader(CommonRepository commonRepository) {
        RepositoryItemReader<CommonEntity> repositoryItemReader = new RepositoryItemReader<>();
        repositoryItemReader.setRepository(commonRepository);
        repositoryItemReader.setPageSize(5);
        // 查询方法
        repositoryItemReader.setMethodName("findByContent");
        // 添加查询参数
        repositoryItemReader.setArguments(Arrays.asList("神韵学Spring Batch"));
        // 以字段id 排序，倒序
        repositoryItemReader.setSort(new HashMap<String, Sort.Direction>(){{put("id", Sort.Direction.DESC);}});
        return repositoryItemReader;
    }

    /**
     * @return json写
     * 直接通过注入bean方式写出json文件，可以自己去定义json格式
     */
    @Bean
    public ItemWriter<CommonEntity> readDBAndWriteJsonFileWriter() {
        ObjectMapper objectMapper = new ObjectMapper();
        Resource resource = new FileSystemResource(generateFilePath);
        JsonObjectMarshaller<CommonEntity> jsonObjectMarshaller = commonEntity -> {
            try {
                return objectMapper.writeValueAsString(commonEntity);
            } catch (JsonProcessingException e) {
                return null;
            }
        };
        JsonFileItemWriter<CommonEntity> jsonFileItemWriter = new JsonFileItemWriter<>(resource, jsonObjectMarshaller);
        jsonFileItemWriter.setEncoding(StandardCharsets.UTF_8.name());
        jsonFileItemWriter.setName("readDBAndWriteJsonFileWriter");
        return jsonFileItemWriter;
    }

    /**
     * @return json写
     * 跟上面方式差不多，只是简单封装了一些参数（别看这种，影响，只是我写了就不去掉这个方法和writer class）
     */
    @Bean
    public ItemWriter<CommonEntity> readDBAndWriteJsonFileWriter2() {
        ObjectMapper objectMapper = new ObjectMapper();
        Resource resource = new FileSystemResource(generateFilePath);
        JsonObjectMarshaller<CommonEntity> jsonObjectMarshaller = commonEntity -> {
            try {
                return objectMapper.writeValueAsString(commonEntity);
            } catch (JsonProcessingException e) {
                return null;
            }
        };
        return new ReadDBAndWriteJsonFileWriter(resource, jsonObjectMarshaller);
    }

}