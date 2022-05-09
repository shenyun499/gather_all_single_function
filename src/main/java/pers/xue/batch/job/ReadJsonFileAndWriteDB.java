package pers.xue.batch.job;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.json.JacksonJsonObjectReader;
import org.springframework.batch.item.json.JsonItemReader;
import org.springframework.batch.item.json.builder.JsonItemReaderBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import pers.xue.batch.entity.CommonEntity;
import pers.xue.batch.util.JsonUtil;
import pers.xue.batch.writer.CommonEntityItemWriter;

/**
 * @author huangzhixue
 * @date 2022/1/20 5:02 下午
 * @Description
 * 读取json file and write to db
 */
@Configuration
public class ReadJsonFileAndWriteDB {

    private String generateFilePath = "files/sample-data.json";

    @Bean
    public Job readJsonFileAndWriteDBJob(JobBuilderFactory jobBuilderFactory, Step readJsonFileAndWriteDBStep) {
        return jobBuilderFactory.get("readJsonFileAndWriteDBJob").start(readJsonFileAndWriteDBStep).build();
    }

    /**
     * json 方式读取records，自定义类写出db
     * @param stepBuilderFactory
     * @return
     */
    @Bean
    public Step readJsonFileAndWriteDBStep(StepBuilderFactory stepBuilderFactory) {
        return stepBuilderFactory.get("readJsonFileAndWriteDBStep")
                .<CommonEntity, CommonEntity>chunk(1000)
                .reader(jsonItemReader())
                .writer(commonEntityItemWriter())
                .build();
    }


    @Bean
    public JsonItemReader<CommonEntity> jsonItemReader() {
        // 如果CommonEntity里面遇到时间类型，那么 LocalDate/LocalDateTime那么在反序列化的时候会出现error
        // 因为jdk1.8开始需要提供反序列化和序列化的方法，也就是设置ObjectMapper的Module，自定义这个ObjectMapper
        // JacksonJsonObjectReader new出来后，设置一个他的ObjectMapper即可
        /*JacksonJsonObjectReader<CommonEntity> jsonObjectReader = new JacksonJsonObjectReader<>(CommonEntity.class);
        jsonObjectReader.setMapper(JsonUtil.getObjectMapper());*/
        return new JsonItemReaderBuilder<CommonEntity>()
                .jsonObjectReader(new JacksonJsonObjectReader<>(CommonEntity.class))
                .resource(new ClassPathResource(generateFilePath))
                .name("jsonItemReader")
                .build();
    }

    /**
     * 写入db
     * @return ItemWriter
     */
    @Bean
    public ItemWriter<CommonEntity> commonEntityItemWriter() {
        return new CommonEntityItemWriter();
    }
}
