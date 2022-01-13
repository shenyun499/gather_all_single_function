package pers.xue.batch.job;

/**
 * @author huangzhixue
 * @date 2022/1/6 11:46 上午
 * @Description
 *
 * 方式一、通过bean注入(尝试失败，至今不知道怎么实现，方式二是可以的，见ReadDBTest)
 * 方式二、通过注入真实的bean -- configuration
 *
 * 注解可以用@SpringBootTest这个功能更强大，它包含了@ContextConfiguration，默认是启动整个boot，后者可以指定配置文件，指定组件被加载
 * 这里两个都可以用
 */
//@SpringBatchTest
//@RunWith(SpringRunner.class)
//@ContextConfiguration(classes = SpringBatchApplication.class)
public class SpringBatchTestExplain {
/*    @Autowired
    @Qualifier("myJobLauncherTestUtils")
    private JobLauncherTestUtils jobLauncherTestUtils;

    private CommonRepository commonRepository;

    @Test
    public void testJob() {
        commonRepository.findByContent("神韵学Spring Batch");

        JobExecution jobExecution = jobLauncherTestUtils.launchStep("readDBStep");

        assertEquals("COMPLETED", jobExecution.getExitStatus().getExitCode());
    }

    @Bean
    public JobLauncherTestUtils myJobLauncherTestUtils() {
        return new JobLauncherTestUtils() {
            @Override
            @Autowired
            public void setJob(@Qualifier("readDBJob") Job job) {
                super.setJob(job);
            }
        };
    }*/
}
