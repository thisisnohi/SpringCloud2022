# CHANGLOG

## 20220725
* 增加动态切换日志级别方法 {@link LoggerController}
* 修改swagger2(SwaggerConfig) -> knife4j(Knife4jConfig): 访问地址 http://127.0.0.1:8001/doc.html
    * pom.xml 
  
  ```xml 
  修改前
  <dependency>
    <groupId>io.springfox</groupId>
    <artifactId>springfox-boot-starter</artifactId>
    <version>3.0.0</version>
  </dependency>

  修改后
  <dependency>
    <groupId>com.github.xiaoymin</groupId>
    <artifactId>knife4j-spring-boot-starter</artifactId>
    <version>3.0.3</version>
  </dependency>
  ```
## 20220805
* 增加resttemplate调用
  * 代码：sys-user -> EurekaController.restUserLists()
  * RestTemplateConfig 增加 @LoadBalanced
    ``` 
    @Bean
    @LoadBalanced
    public RestTemplate restTemplate
     ```
