package pers.xue.jpa;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
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
public class SpringBatchApplication {
    public static void main(String[] args) {
        ApplicationContext applicationContext = SpringApplication.run(SpringBatchApplication.class, args);

    }
    private static void run(ApplicationContext applicationContext) throws JobInstanceAlreadyCompleteException, JobExecutionAlreadyRunningException, JobParametersInvalidException, JobRestartException {
        JobLauncher jobLauncher = applicationContext.getBean(JobLauncher.class);
        JobParameters jobParameters = new JobParametersBuilder().addDate("test", new Date()).toJobParameters();
        jobLauncher.run(applicationContext.getBean("", Job.class), jobParameters);
    }
}