package pers.xue.batch.job;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pers.xue.batch.tasklet.DownloadJsonFileBySftpTasklet;
import pers.xue.batch.tasklet.UploadJsonFileBySftpTasklet;

/**
 * @author huangzhixue
 * @date 2022/2/21 2:27 下午
 * @Description
 *  1、根据sftp协议 upload file 到服务器
 *
 *  2、从服务器 根据sftp协议 download file
 *
 *  题外话：如果担心上传文件还未完成就被拿来用了，也就是说不能确保文件完整性，通常我们的解决方案是：
 *      对上传的文件加tmp后缀, 等传输完成改回原名。取文件的那边提前约定好，过滤.tmp的文件即可。
 */
@Configuration
public class UploadAndDownloadJsonFileBySftp {

    @Autowired
    UploadJsonFileBySftpTasklet uploadJsonFileBySftpTasklet;

    @Autowired
    DownloadJsonFileBySftpTasklet downloadJsonFileBySftpTasklet;

    @Bean
    public Job uploadAndDownloadJsonFileBySftpJob(JobBuilderFactory jobBuilderFactory, Step uploadJsonFileBySftpStep, Step downloadJsonFileBySftpStep) {
        return jobBuilderFactory.get("uploadAndDownloadJsonFileBySftpJob")
                .start(uploadJsonFileBySftpStep)
                .next(downloadJsonFileBySftpStep)
                .build();
    }

    /**
     * tasklet方式 上传file（比较正确的做法是，由前面生成到某路径下的file，然后next step 调用这个step直接进行upload file）
     * @param stepBuilderFactory
     * @return
     */
    @Bean
    public Step uploadJsonFileBySftpStep(StepBuilderFactory stepBuilderFactory) {
        return stepBuilderFactory.get("uploadJsonFileBySftpStep")
                .tasklet(uploadJsonFileBySftpTasklet)
                .build();
    }

    /**
     * tasklet方式 上传file
     * @param stepBuilderFactory
     * @return
     */
    @Bean
    public Step downloadJsonFileBySftpStep(StepBuilderFactory stepBuilderFactory) {
        return stepBuilderFactory.get("downloadJsonFileBySftpStep")
                .tasklet(downloadJsonFileBySftpTasklet)
                .build();
    }
}
