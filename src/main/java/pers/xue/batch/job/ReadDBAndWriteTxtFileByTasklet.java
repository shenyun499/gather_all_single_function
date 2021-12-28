package pers.xue.batch.job;

import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.context.annotation.Bean;
import pers.xue.batch.listener.ReadDBAndWriteFileTaskletListener;
import pers.xue.batch.tasklet.ReadDBAndWriteFileTasklet;

/**
 * @author huangzhixue
 * @date 2021/12/28 3:26 下午
 * @Description
 */
public class ReadDBAndWriteTxtFileByTasklet {


    /**
     * tasklet方式
     * @param stepBuilderFactory
     * @return
     */
/*    @Bean
    public Step readDBAndWriteFileStep2(StepBuilderFactory stepBuilderFactory) {
        return stepBuilderFactory.get("readDBAndWriteFileStep")
                .tasklet(readDBAndWriteFileTasklet())
                .listener(readDBAndWriteFileTaskletListener())
                .build();
    }*/

/*    private StepExecutionListener readDBAndWriteFileTaskletListener() {
        return new ReadDBAndWriteFileTaskletListener();
    }

    private ReadDBAndWriteFileTasklet readDBAndWriteFileTasklet() {
        return new ReadDBAndWriteFileTasklet(generateFilePath);
    }*/
}
