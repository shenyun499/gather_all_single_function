package pers.xue.batch.tasklet;

import com.jcraft.jsch.ChannelSftp;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.integration.file.remote.session.CachingSessionFactory;
import org.springframework.integration.file.remote.session.Session;
import org.springframework.integration.file.remote.session.SessionFactory;
import org.springframework.integration.sftp.config.SftpOutboundChannelAdapterParser;
import org.springframework.integration.sftp.inbound.SftpInboundFileSynchronizer;
import org.springframework.integration.sftp.session.DefaultSftpSessionFactory;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileInputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * @author huangzhixue
 * @date 2022/2/21 2:37 下午
 * @Description
 */
@Slf4j
@Component
public class UploadJsonFileBySftpTasklet implements Tasklet {

    @Override
    public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) {

        String remotePath = "/sftp/mysftp/upload";
        String localPath = "/Users/huangzhixue/IdeaProjects/gather_all_single_function/src/main/resources/sftp/upload/common-entity-20220221.json";
        String uploadTime = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        String filename = "test-" + uploadTime;
        DefaultSftpSessionFactory sftpFactory = new DefaultSftpSessionFactory(true);
        sftpFactory.setAllowUnknownKeys(true);

        SessionFactory<ChannelSftp.LsEntry> sessionFactory = new CachingSessionFactory<>(sftpFactory);
        SftpInboundFileSynchronizer sftpInboundFileSynchronizer = new SftpInboundFileSynchronizer(sessionFactory);

        //sftpInboundFileSynchronizer.synchronizeToLocalDirectory();

        Session session = null;
        try {
            session = sftpFactory.getSession();
            if(session.isOpen()) {
                session.write(new FileInputStream(localPath), remotePath + File.separator + filename);
                log.info("write successfully");
            }
        }
        catch (Exception e) {
            log.warn("Failed uploading to repository",e);
        }
        finally {
            if (session!=null && session.isOpen()) {
                session.close();
                return RepeatStatus.FINISHED;
            }
        }
        return RepeatStatus.FINISHED;
    }
}
