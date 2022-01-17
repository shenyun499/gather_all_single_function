package pers.xue.batch.job;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import pers.xue.batch.SpringBatchApplication;

/**
 * @author huangzhixue
 * @date 2022/1/12 3:59 下午
 * @Description
 */
@SpringBootTest
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = { SpringBatchApplication.class })
@ActiveProfiles(value = "test")
public class ReadDBTest {

    private JobLauncherTestUtils jobLauncherTestUtils;

    @Autowired
    private JobLauncher launcher;

    @Autowired
    private JobRepository repository;

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private ReadDB config;

    @Autowired
    private Step readDBStep;

    public ReadDBTest() {
    }

    @Before
    public void setup() {
        jobLauncherTestUtils = new JobLauncherTestUtils();
        // job 为具体job，每个不同test job都不一样
        jobLauncherTestUtils.setJob(config.readDBJob(jobBuilderFactory, readDBStep));
        jobLauncherTestUtils.setJobLauncher(launcher);
        jobLauncherTestUtils.setJobRepository(repository);
    }

    // Test job
    @Test
    public void testReadDBJob() throws Exception {
        JobExecution result = jobLauncherTestUtils.launchJob();
        Assert.assertNotNull(result);
        Assert.assertEquals(BatchStatus.COMPLETED, result.getStatus());
    }

    // Test step
    @Test
    public void testReadDBStep() {
        // readDBStep为单元测试具体名称
        JobExecution result = jobLauncherTestUtils.launchStep("readDBStep");
        Assert.assertNotNull(result);
        Assert.assertEquals(BatchStatus.COMPLETED, result.getStatus());
    }
}
