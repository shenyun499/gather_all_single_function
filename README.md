# Spring Batch 概述

# 目前配置
目前采用嵌入式数据库 h2，jpa与数据库交互，都是为了方便，拿来就可以start，import导入了一些记录，需要更多test记录可以自行导入。

# 启动流程
启动方法，pers.xue.batch.SpringBatchApplication.runJob中，调用
JobExecution run = jobLauncher.run(applicationContext.getBean("readDBAndWriteFileJob", Job.class), jobParameters);
方法，指定相应的job的bean name，即可

# 现有功能
## 1、读取 db records 两种方式
pers.xue.batch.job.ReadDB class  
1、readDBJob  
普通的读，通过序列器，并且通过listener判断读取是否完毕，适用于非jpa  
2、readDBJob2  
通过jpa repository指定方法和参数读取记录，这个过程是reader
两个仅仅是读取，没有做writer扩展功能  

## 2、pers.xue.batch.job.ReadDBAndWriteFile class
这个类的作用：从db读取记录，然后写入txt 文件中  
通过jpa repository指定方法和参数读取记录，这个过程是reader  
通过FlatFileItemWriter指定bean 并设置field name和写入path将文件写入，这个过程是writer  
