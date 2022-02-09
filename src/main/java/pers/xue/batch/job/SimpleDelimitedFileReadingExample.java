package pers.xue.batch.job;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemProcessor;
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
import org.springframework.stereotype.Component;
import pers.xue.batch.entity.CommonEntity;
import pers.xue.batch.entity.Player;
import pers.xue.batch.writer.CommonEntityItemWriter;

/**
 * @author huangzhixue
 * @date 2022/2/9 3:48 下午
 * @Description
 *
 * https://docs.spring.io/spring-batch/docs/4.3.x/reference/html/readersAndWriters.html#simpleDelimitedFileReadingExample
 *
 * 读取下面例子
 * ID,lastName,firstName,position,birthYear,debutYear
 * "AbduKa00,Abdul-Jabbar,Karim,rb,1974,1996",
 * "AbduRa00,Abdullah,Rabih,rb,1975,1999",
 * "AberWa00,Abercrombie,Walter,rb,1959,1982",
 * "AbraDa00,Abramowicz,Danny,wr,1945,1967",
 * "AdamBo00,Adams,Bob,te,1946,1969",
 * "AdamCh00,Adams,Charlie,wr,1979,2003"
 */
@Configuration
@Slf4j
public class SimpleDelimitedFileReadingExample {

    /**
     * 目前使用相对路径
     */
    private String generateFilePath = "files/office-example.csv";

    @Bean
    public Job simpleDelimitedFileReadingExampleJob(JobBuilderFactory jobBuilderFactory, Step simpleDelimitedFileReadingExampleStep) {
        return jobBuilderFactory.get("simpleDelimitedFileReadingExampleJob").start(simpleDelimitedFileReadingExampleStep).build();
    }

    /**
     * csv 文件读取
     * @param stepBuilderFactory
     * @return
     */
    @Bean
    public Step simpleDelimitedFileReadingExampleStep(StepBuilderFactory stepBuilderFactory, ItemReader<Player> readCsvFileReader,
                                                      PlayerConvertCommonEntityProcessor playerConvertCommonEntityProcessor) {
        return stepBuilderFactory.get("simpleDelimitedFileReadingExampleStep")
                .<Player, CommonEntity>chunk(1000)
                .reader(readCsvFileReader)
                .processor(playerConvertCommonEntityProcessor)
                .writer(commonEntityItemWriter03())
                .build();
    }

    /**
     *  csv 文件读取
     * @return
     */
    @Bean
    public ItemReader<Player> readCsvFileReader() throws Exception {
        FlatFileItemReader<Player> itemReader = new FlatFileItemReader<>();
        itemReader.setResource(new ClassPathResource(generateFilePath));
        DefaultLineMapper<Player> lineMapper = new DefaultLineMapper<>();
        //DelimitedLineTokenizer defaults to comma as its delimiter
        lineMapper.setLineTokenizer(new DelimitedLineTokenizer());
        lineMapper.setFieldSetMapper(new PlayerFieldSetMapper());
        itemReader.setLineMapper(lineMapper);
        // 官网没加，这里是需要的
        itemReader.setLinesToSkip(1);
        // 如果添加了，则全部以"开头的不能解析
        // itemReader.setComments(new String[]{"\""});
        /*itemReader.open(new ExecutionContext());
        Player player = itemReader.read();
        log.info(player.toString());*/
        return itemReader;
    }

    protected static class PlayerFieldSetMapper implements FieldSetMapper<Player> {
        @Override
        public Player mapFieldSet(FieldSet fieldSet) {
            Player player = new Player();
            player.setID(fieldSet.readString(0));
            player.setLastName(fieldSet.readString(1));
            player.setFirstName(fieldSet.readString(2));
            player.setPosition(fieldSet.readString(3));
            player.setBirthYear(fieldSet.readInt(4));
            player.setDebutYear(fieldSet.readInt(5));
            return player;
        }
    }

    /**
     * 写入db
     * @return ItemWriter
     */
    @Bean
    public ItemWriter<CommonEntity> commonEntityItemWriter03() {
        return new CommonEntityItemWriter();
    }

    @Component
    private static class PlayerConvertCommonEntityProcessor implements ItemProcessor<Player, CommonEntity> {
        @Override
        public CommonEntity process(Player player) {
            // reset CommonEntity, make a action for update, can do more things..
            CommonEntity commonEntity = new CommonEntity();
            commonEntity.setId(1);
            commonEntity.setContent("22222");
            log.info("--------------common entity: {}", commonEntity.toString());
            return commonEntity;
        }
    }
}
