# 一、Spring Batch 介绍
有个看了比较好的推荐文章：[https://www.cnblogs.com/rookiemzl/p/9788002.html](https://www.cnblogs.com/rookiemzl/p/9788002.html)
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

chunkSize(Integer)：指定块大小，当读到的次数等于块时，就会执行一次commit，写入，然后继续第二个块，最后执行完，commit count=块数+最终状态修改commit  
比如需要读取一条记录，并且chunkSize=1。  
说明块大小为1，读取itemreader1, 满足 1 的块大小要求，因此整个块被传递给 Writer，然后应计为1 个 commit（包装在一个事务中），然后做最终状态提交commit+1, 最终commit count=2.  

incrementer(new RunIdIncrementer())：jobParameter 为null时，会重新建立一个，否则在原基础上加1  
faultTolerant()：启用跳过skip功能/重试功能 即开启容错机制 
注意容错机制是将出现异常的记录排除出缓存，然后重新执行 
比如有记录1234，chunkSize=4，即process要执行四次，skip在处理1完成，在处理2出现异常，会重新process 134的数据，其实就是将2移出内存，直到执行结束或者到达最大重试次数 
所以可能出错，可以设置chunkSize=1,或者catch住异常，或者将不能重试的catch住 
skip(excetion)：遇到异常(符合跳过的异常)，跳过处理，可以用系统指定的，也可以自定义.比如遇到数据校验异常跳过这个异常，skip(DataValidationException.class)  
skipLimit(Integer)：允许跳过的最大次数，超过这个数将会导致整个执行失败  
skipPolicy(Bean)：自定义跳过逻辑  

retry(ConnectTimeoutException.class)：遇到异常(符合重试的异常)，重新执行这个step  
retryLimit(Integer)：允许重试的最大次数，超过这个数将结束重试执行程序。  
skip与retry的区别：retry不能对reader抛出的异常进行retry，只能对process或者write抛出的异常进行retry；skip可以对reader或者process或者write抛出的异常进行skip。  
  
taskExecutor(TaskExecutor): 配置一下线程，采用多线程方式运行，这里的多线程指的是chunk多线程，比如chunk=100, 说明块大小为100，那么将开启多个线程分别处理块（单独的执行线程中读取，处理和写入每个块），而不是step  
throttleLimit(Integer)：采用几个线程来处理，和taskExecutor配合使用，默认是4个，一般设置为core thread num  
注意：如果设置为1的时候显示会有两个线程，不懂原理！目前已经在stack over上提问了   
chunk模式下，比如一个step要去读数据库所有的记录，数据库有100条记录，chunk=100,然后throttleLimit配置2，就会有两个线程随机读取数据库的记录，直至读完，两个线程读到的数据不一定都是50，反正加起来等于100，然后分成两个writer写入（本来一次的chunk=100，因为两个线程读了）

[FlatFileItemReader](https://docs.spring.io/spring-batch/docs/4.3.x/reference/html/readersAndWriters.html#flatFileItemReader)
reader不该返回null，因为返回null就相当于整个项目结束，不管后面是否还有记录，过滤应该放到processor去处理而不是reader,当然可以设置多线程读，算是一种解决方案(不推荐)..  


读取一个文件需要设置LineMapper和Resource(这里就不讲Resource)  
一、LineMapper意思是一行映射成一个Object，默认实现是DefaultLineMapper，还有复杂映射PatternMatchingCompositeLineMapper，它需要设置两个属性才支持工作，一个是LineTokenizer，另一个是FieldSetMapper      
二、LineTokenizer这个是设置读取文件的分隔符或者是行号，要设置读取对象名称，读取规则映射成FieldSet  
LineTokenizer的实现有三种，DelimitedLineTokenizer(根据设置分隔符去识别，默认分隔符是逗号)，FixedLengthTokenizer(根据固定长度读，比如读1-3为一个字段属性)，PatternMatchingCompositeLineTokenizer(复杂匹配，可用于文件格式不单一的读取)三种
三、FieldSetMapper是将数据一行一行的映射成Object  
FieldSetMapper的实现形式有三种（详细见ReadTxtFileAndWriteDB，1、2种都举了例子）  
1、[通过名称映射](https://docs.spring.io/spring-batch/docs/4.3.x/reference/html/readersAndWriters.html#mappingFieldsByName)  
但是LineTokenizer中需要设置 tokenizer.setNames(new String[] {"ID", "lastName", "firstName", "position", "birthYear", "debutYear"});  
然后在FieldSetMapper中通过名称读取
2、[通过bean映射](https://docs.spring.io/spring-batch/docs/4.3.x/reference/html/readersAndWriters.html#beanWrapperFieldSetMapper)  
需要通过bean的方式注入一个Object bean，LineTokenizer也需要设置names，BeanWrapperFieldSetMapper  
3、[通过固定长度映射](https://docs.spring.io/spring-batch/docs/4.3.x/reference/html/readersAndWriters.html#fixedLengthFileFormats)  
其实这个属于（LineTokenizer，但是还是把它归到这里来吧..）需要用到FixedLengthTokenizer，并且需要设置names和Range，range默认是1开始。  
tokenizer.setNames("ISIN", "Quantity", "Price", "Customer");  
tokenizer.setColumns(new Range(1, 12), new Range(13, 15), new Range(16, 20), new Range(21, 29));  
上面的一个Range映射一个name，如1，12映射ISIN，后面可以通过任意FieldSetMapper的实现1、2去实现


# 二、了解当前项目
## 当前项目配置
目前采用嵌入式数据库 h2，jpa与数据库交互，都是为了方便，拿来就可以start，import导入了一些记录，需要更多test记录可以自行导入。

## 当前项目启动流程
启动方法，pers.xue.batch.SpringBatchApplication.runJob中，调用  
JobExecution run = jobLauncher.run(applicationContext.getBean("readDBAndWriteFileJob", Job.class), jobParameters);  
方法，指定相应的job的bean name，即可  
新的启动改变 job.key -- dev环境， 里面的value是指当前job bean name  

# 三、当前项目实现的具体功能
## 1、读取 db records 两种方式
pers.xue.batch.job.ReadDB class    
1、readDBJob  
普通的读，通过序列器，并且通过listener判断读取是否完毕，适用于非jpa  
2、readDBJob2  
通过jpa repository指定方法和参数读取记录，这个过程是reader
两个仅仅是读取，没有做writer扩展功能  
repository：这个只支持分页读，并且默认pageSize是10，也就是会按照每页10条记录读一次，直到读完所有符合查询的记录  

## 2、repository 方式读写db
pers.xue.batch.job.ReadDBAndWriteDBByRepository  
如果writer不设置method，默认将会调用saveAll  

## 3、从db读取记录，然后写入txt 文件中
pers.xue.batch.job.ReadDBAndWriteTxtFile class  
1、通过jpa repository指定方法和参数读取记录，这个过程是reader  
2、通过FlatFileItemWriter指定bean 并设置field name和写入path将文件写入，这个过程是writer  
FlatFileItemWriter的headerCallback和footerCallback是写标题和结尾的  

## 4、从db读取记录，然后写入json 文件中
pers.xue.batch.job.ReadDBAndWriteJsonFile class  
1、通过jpa repository指定方法和参数读取记录，这个过程是reader  
2、通过JsonFileItemWriter指定bean 并自定义转换json的格式和指定写入path将文件写入，这个过程是writer  

## 5、从json file读取记录，并写入db中
pers.xue.batch.job.ReadJsonFileAndWriteDB class  
通过JsonItemReader 读取，然后通过自定义的writer写入db（可以用Jpa/HibernateItemWriter, 但是个人认为自己实现）

## 6、从txt file读取记录，并写入db中
pers.xue.batch.job.ReadTxtFileAndWriteDB  
读取文件有两部曲，第一是将文件内容读到缓冲，然后缓冲映射成实体类对象，这里要借助下面两个对象  
DelimitedLineTokenizer: 与文件的映射读取  
FieldSetMapper：已经读到值，这个是与对象的映射，将值填充到对象中  
字段映射成Object，官方介绍了三种方式  
1、通过索引，这样子比较简单，但是需要确保字段索引的位置，这样LineTokenizer，就可以配置为lineMapper.setLineTokenizer(new DelimitedLineTokenizer())  
2、通过字段名称，但是需要配置LineTokenizer，tokenizer.setNames(new String[] {"id", "content"});  
3、通过注入bean，通过BeanWrapperFieldSetMapper去set注入需要解析的object bean名称  
官网对文件有两种解析，意思提供 固定分隔符/固定长度 解析file  
默认的DelimitedLineTokenizer就是固定分隔符为逗号，是其它需要自己设定，如果不是分隔符，而是固定长度，那么需要使用tokenizer.setColumns(new Range[]{new Range(1,n), new Range(n, m)})  

## 7、读取db读取记录，生成带header、tail的dat文件
pers.xue.batch.job.ReadDBAndWriteDatFile.readDBAndWriteDatFileJob  
使用|做分隔符，文件后缀为dat  
header为header+当天日期，比如header20220509  
tail为tail+记录数，比如tail200  
tips: 如果这个需求在tasklet中来实现是最快的，直接读取全部，然后各种写入即可，我也提供了这种写法  
pers.xue.batch.job.ReadDBAndWriteDatFile.readDBAndWriteDatFileStep2  

## 8、读取前面生成的dat文件写入db，会识别header tail，识别正文间隙是'｜'
pers.xue.batch.job.ReadMultiFormattedDatFile  
注意，这里的fieldSetMap用了三个，但是其中header和tail返回的null，仅仅是打印了log，而且用了多线程才不会结束只读一行记录
不然只会读取一行记录，因为reader本不该返回null，如果返回null标志着结束，后面还有记录也不会进行读取，而是在processor中处理，所以用了多线程去读，这样同时读了1/2行或者更多，就不会结束了  

## 9、以索引为下标生成txt文件，带header tail，1开头，比如1-3生成字段1， 4-5生成字段2  
  
## 10、读取以索引为下标的txt文件，带header tail，1开头，比如1-3读取字段1， 4-5读取字段2

## 11、通过继承 tasklet 写多个不同的Object对象 json/file
pers.xue.batch.job.GenerateMultiTxtFileByTasklet  

## 12、从csv file读取记录，通过processor处理，用repository方式写入db中
pers.xue.batch.job.ReadCsvFileAndWriteDB  
使用的是默认固定分隔符逗号，使用字段名称映射实现。 

## 13、仅仅是读取db，不做写入操作 tasklet
pers.xue.batch.job.ReadDBByTasklet  
面向块的处理并不是在一个步骤中进行处理的唯一方法。如果Step必须包含一个简单的存储过程调用，该怎么办?  
您可以将调用实现为ItemReader，并在过程完成后返回null。然而，这样做有点不自然，因为需要一个无操作的ItemWriter。  
Spring Batch为这个场景提供了TaskletStep  

## 14、根据sftp上传json file到服务器。根据sftp下载json file到服务器（代码已经自测，因为个人使用了腾讯云）
pers.xue.batch.job.UploadAndDownloadJsonFileBySftp  

## 15、条件判断
可以根据条件判断下一个step是否需要继续执行  
pers.xue.batch.job.DeciderUsage  

16开始都是优化相关：  
## 16、分发流，多个步骤step并行执行
pers.xue.batch.job.SplitFlowExample  
https://docs.spring.io/spring-batch/docs/4.3.x/reference/html/step.html#split-flows  

## 17、顺序流，多个步骤顺序执行，前面失败后面都不会被执行
https://docs.spring.io/spring-batch/docs/4.3.x/reference/html/step.html#SequentialFlow  

## 18、文件分区处理，读多个csv文件并插入数据库
pers.xue.batch.job.PartitionMultiFile#partitionMultiFileJob  
如果前面都是通过各种的reader去读取文件，但是如果遇到resource不存在或者其它比较难处理，建议都是通过PartitionMultiFile来先读  
这样Step有resource时才会处理，listener也会正常运行  
MultiResourceItemReader也可以实现，但是不是分区，是一个step读取多个file  

## 19、数据分区处理，将数据库的数据分区分页读取
根据数据库总数量，平均分给多少个线程处理  


## 分区
https://www.jdon.com/springboot/spring-batch-partition.html  
Multi-threaded Step ，chunk并发，一个step中对chunk进行并发读取  
Parallel Steps，多个Step并行处理（也属于分区的一种方式）  
Partitioning，分区，（数据分区、分区处理）一般是文件读取分区，一个区   处理一个文件，比如一个目录有三个文件，同时处理就可以定义三个区  
https://blog.csdn.net/TreeShu321/article/details/110679574  


# 三、测试类怎么写
pers.xue.batch.job.ReadDBTest，参考ReadDBTest这个class  
需要@RunWith(SpringRunner.class)和@ContextConfiguration(classes = { 配置class })或者@SpringBootTest，或者两者一起使用  
然后通过JobLauncherTestUtils，最重要的是这个job怎么返回，我那里是注入了整个job的配置类ReadDB，然后通过config.readDBJob(jobBuilderFactory, readDBStep)获得这个Job，然后测试Job/Step  
@ConditionalOnProperty(name = "job.key", havingValue = "readDBJob")这个配置在@Configuration一起，那当有name这个key时，并且值为havingValue时，这个配置类生效


# 四、文件处理
一般会将从远程通过sftp安全将文件下载到本地xx/in目录下面，然后通过batch 处理这个文件，处理成功会将文件放到done，失败放到error目录下  
要提交文件到远程，需要batch先生成文件到本地pending_submit目录下，然后提交到远程成功后，将文件移动到submitted目录  
