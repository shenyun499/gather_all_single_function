# security oauth2
这个案例是将资源服务器和授权服务器放在了同一个项目中，正常是分开的，有单独的授权服务器进行管理  
1、引入security oauth2依赖  
groupId: org.springframework.boot  
artifactId: spring-boot-starter-security  

groupId: org.springframework.security.oauth.boot  
artifactId: spring-security-oauth2-autoconfigure  
version: 2.5.0
</dependency>  

2、配置security  

3、配置授权服务器  

4、配置资源服务器  

5、自定义用户登陆  

6、授权码模式测试  
下面url包含授权码模式，client_id，重定向路径，都需要和授权服务器配置的信息一致，否则无法跳转  
（1）、浏览器输入，localhost:8080/oauth/authorize?response_type=code&client_id=admin&redirect_uri=http://www.baidu.com&scope=all  
（2）、登陆页面，输入admin/123456，信息在自定义用户登陆上配置了，UserService  
（3）、点击授权确认
（4）、返回百度，后面跟着授权码，https://www.baidu.com/?code=0xZVoL，这里0xZVoL就是授权码  
（5）、打开postman，输入post请求，localhost:8080/oauth/token, 选择authorization-Basic Auth, 输入admin/112233,   
body 里面，写grant_type: authorization_code, code: 0xZVoL, client_id: admin, redirect_uri: http://www.baidu.com, scope: all  
调用返回了access_token，失效时间  
（6）、继续用postman, 输入post请求，localhost:8080/user/getCurrentUser , 调用类型选择Bearer Token，Token输入access_token的值  
调通了，正规步骤结束  

