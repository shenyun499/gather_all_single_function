package pers.xue.batch.job;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import pers.xue.batch.SpringBatchApplication;
import pers.xue.batch.repository.CommonRepository;

import javax.sql.DataSource;

/**
 * @author huangzhixue
 * @date 2022/1/6 11:46 上午
 * @Description
 */
@SpringBatchTest
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = SpringBatchApplication.class)
public class ReadDBTest {
    @Autowired
    private JobLauncherTestUtils jobLauncherTestUtils;

    private CommonRepository commonRepository;

    @Test
    public void testJob() throws Exception {
        commonRepository.findByContent("神韵学Spring Batch");

        JobExecution jobExecution = jobLauncherTestUtils.launchStep("readDBStep");


        Assert.assertEquals("COMPLETED", jobExecution.getExitStatus().getExitCode());
    }
}
