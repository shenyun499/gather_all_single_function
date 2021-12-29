# 一、Spring Batch 介绍
## 概念
Spring Batch 是一个基于Spring的轻量级的批处理框架。提供了大量可重用组件，包括日志、追踪、事务、任务作业统计、任务重启、跳过、重复、资源管理。  
Spring Batch 是一个批处理应用框架，不是调度框架，需要与调度框架合作来构建完成批处理任务（比如xx-job/spring scheduler)，它只关注批处理相关问题，如事务、并发、监控、执行等。
## 特性
1、工作流状态机：由可以在不同状态间转换的步骤构成工作流。
2、支持事务处理：基于数据块，分批处理。
3、声明式IO：提供了非常易用的输入输出支持，使开发者关注于业务逻辑。
4、错误处理：框架支持异常跳过和记录日志。
5、可扩展机制：可以与第三方框架比较好的集成。
6、基于Spring框架之上，因此可以使用Spring的一些特性，比如IOC、Test...

## 简单的介绍一下运行原理
其中最主要的是 JobLauncher、Job、Step(Chunk,Tasklet)  
Step：Chunk(ItemReader、ItemProcessor、ItemWriter)、Tasklet  

JobLauncher：是任务启动器，用来启动job，是整个程序的入口。  
Job：代表一个具体的任务，Step代表一个步骤，一个Job可以由几个Step组成。  
Step：Step实现方式有两种，分别是Chunk和Tasklet，Chunk包含三个部分，分别是ItemReader、ItemProcessor、ItemWriter
ItemReader：数据读取，可以从db、各种file等读取需要的数据进行后续处理。这个由很多实现,可以参考 [Item Readers](https://docs.spring.io/spring-batch/docs/4.3.x/reference/html/appendix.html#listOfReadersAndWriters)
ItemProcessor：对数据进行加工，比如读取的数据需要进行过滤，或者类型对象转换等等，然后交给ItemWriter处理。
ItemWriter：数据写出，可以写出到db、各种file等。这个也由很多实现，可以参考：[Item Writers](https://docs.spring.io/spring-batch/docs/4.3.x/reference/html/appendix.html#itemWritersAppendix)

incrementer(new RunIdIncrementer())：jobParameter 为null时，会重新建立一个，否则在原基础上加1
faultTolerant()：启用跳过skip功能
Skip：遇到异常，跳过处理，可以用系统指定的，也可以自定义
SkipLimit：跳过的最大项数，超过这个数将会导致整个执行失败
SkipPolicy：自定义跳过逻辑

# 二、了解当前项目
## 当前项目配置
目前采用嵌入式数据库 h2，jpa与数据库交互，都是为了方便，拿来就可以start，import导入了一些记录，需要更多test记录可以自行导入。

## 当前项目启动流程
启动方法，pers.xue.batch.SpringBatchApplication.runJob中，调用
JobExecution run = jobLauncher.run(applicationContext.getBean("readDBAndWriteFileJob", Job.class), jobParameters);
方法，指定相应的job的bean name，即可

# 三、当前项目实现的具体功能
## 1、读取 db records 两种方式
pers.xue.batch.job.ReadDB class  
1、readDBJob  
普通的读，通过序列器，并且通过listener判断读取是否完毕，适用于非jpa  
2、readDBJob2  
通过jpa repository指定方法和参数读取记录，这个过程是reader
两个仅仅是读取，没有做writer扩展功能  

## 2、从db读取记录，然后写入txt 文件中
pers.xue.batch.job.ReadDBAndWriteFile class
1、通过jpa repository指定方法和参数读取记录，这个过程是reader  
2、通过FlatFileItemWriter指定bean 并设置field name和写入path将文件写入，这个过程是writer  