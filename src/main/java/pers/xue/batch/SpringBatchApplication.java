package pers.xue.batch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.*;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.ApplicationContext;

import java.util.Date;

@SpringBootApplication
@EntityScan
@EnableBatchProcessing
public class SpringBatchApplication {
    private static final Logger log = LoggerFactory.getLogger(SpringBatchApplication.class);

    public static void main(String[] args) {
        ApplicationContext applicationContext = SpringApplication.run(SpringBatchApplication.class, args);
        runJob(applicationContext);
    }
    private static void runJob(ApplicationContext applicationContext) {
        String jobName = applicationContext.getEnvironment().getProperty("job.key");
        JobLauncher jobLauncher = applicationContext.getBean(JobLauncher.class);
        JobParameters jobParameters = new JobParametersBuilder().addDate("test", new Date()).toJobParameters();

        ExitStatus exitCode = null;
        try {
            // read db job
            //JobExecution run = jobLauncher.run(applicationContext.getBean("readDBJob", Job.class), jobParameters);

            // read db job 2
            //JobExecution run = jobLauncher.run(applicationContext.getBean("readDBJob2", Job.class), jobParameters);

            // read db and write txt file job
            //JobExecution run = jobLauncher.run(applicationContext.getBean("readDBAndWriteFileJob", Job.class), jobParameters);

            // read db and write json file job
            //JobExecution run = jobLauncher.run(applicationContext.getBean("readDBAndWriteJsonFileJob", Job.class), jobParameters);

            JobExecution run = jobLauncher.run(applicationContext.getBean(jobName, Job.class), jobParameters);
            exitCode = run.getExitStatus();
        } catch (JobExecutionAlreadyRunningException | JobRestartException
                | JobInstanceAlreadyCompleteException | JobParametersInvalidException e) {
            log.error("Job {} failed:\n{}", jobName, e);
        }
        log.info("Job {} run end, exitCode: {}", jobName, exitCode);
        System.exit(1);
    }
}