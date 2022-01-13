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
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author huangzhixue
 * @date 2022/1/12 3:59 下午
 * @Description
 */
@RunWith(SpringRunner.class)
//@ContextConfiguration(classes = { SpringBatchApplication.class })
@SpringBootTest
@ActiveProfiles(value = "test")
public class ReadDBTest {

    private JobLauncherTestUtils jobLauncherTestUtils;

    @Autowired
    private ReadDB config;

    @Autowired
    private JobLauncher launcher;

    @Autowired
    private JobRepository repository;

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private Step readDBStep;

    public ReadDBTest() {
    }

    @Before
    public void setup() {
        jobLauncherTestUtils = new JobLauncherTestUtils();
        jobLauncherTestUtils.setJobLauncher(launcher);
        jobLauncherTestUtils.setJobRepository(repository);
    }

    // Test step
    @Test
    public void testLaunchJobWithJobLauncher() throws Exception {
        final JobExecution result = jobLauncherTestUtils.getJobLauncher().run(
                config.readDBJob(jobBuilderFactory, readDBStep),
                jobLauncherTestUtils.getUniqueJobParameters());
        Assert.assertNotNull(result);
        Assert.assertEquals(BatchStatus.COMPLETED, result.getStatus());
    }

    // Test step
    @Test
    public void testSomeStep() {
        jobLauncherTestUtils.setJob(config.readDBJob(jobBuilderFactory, readDBStep));
        JobExecution jobExecution = jobLauncherTestUtils.launchStep("readDBStep");
        Assert.assertEquals(BatchStatus.COMPLETED, jobExecution.getStatus());
    }
}
