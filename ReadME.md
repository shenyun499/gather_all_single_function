# spring jpa 多数据源整合

在Spring Boot中配置多个数据源并实现自动切换EntityManager，这里我编写了一个RoutingEntityManagerFactory和AOP（面向切面编程）的方式来实现。
这里我配置了两个数据源：primary和secondary，其中primary主数据源用来写入数据，secondary从数据源用来读取数据。
原文链接：https://blog.csdn.net/somken/article/details/136913001
一、pom 文件导入JPA依赖
starter -- web/jpa/aop

二、在application.yml中数据源信息
jdbc:mysql://localhost:3306/primary_db
jdbc:mysql://localhost:3306/secondary_db

三、在数据库创建两个库，并且导入数据
看resource下面的sql文件

四、创建动态数据源
1、主数据源配置
PrimaryDataSourceConfig
2、次数据源配置
SecondaryDataSourceConfig

五、全局EntityManager配置
1、创建EntityManager线程工具类--EntityManagerContextHolder是一个用来保存当前线程的EntityManager名称的工具类。
2、创建数RoutingEntityManagerFactory路由(管理多个EntityManager)
3、创建EntityManagerFactory全局配置类

六、使用AOP实现EntityManager自动切换
1、创建AOP切面注入EntityManager类型
通过AOP在方法执行前设置EntityManagerFactory，并在方法执行后清除。
2、创建自定义注解用于标注所使用的的
DataSourceSwitch是一个自定义注解，用来标识需要切换数据源的方法。

七、实体类创建，service层创建，service层实现类实现，编写测试代码

# mybatis
springboot多数据源：
一、在yml文件配置双数据源
二、创建分包mapper
com.wwg.test1.dao: 创建Mapper，对应数据源1
com.wwg.test2.dao: 创建Mapper，对应数据源2
三、@MapperScan注册到对应的mapper包
配置@MapperScan(basePackages = “com.wwg.test1"指定包
DataSource1Config
四、
第一种方式是在service层的@TransactionManager中使用transactionManager指定DataSourceConfig中配置的事务
