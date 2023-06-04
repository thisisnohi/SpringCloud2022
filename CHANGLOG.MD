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
## 20200807
* 熔断降级 sentinel 使用。
  （hystrix 2018 年 netflix 已经不维护。spring cloud 2021 默认已经移除 hystrix）
   参见：SentinelResourceController
* 修复优先加载bootstram.yml

  ```yaml
    <!-- 优先加载bootstrap.yml -->
    <dependency>
      <groupId>org.springframework.cloud</groupId>
      <artifactId>spring-cloud-starter-bootstrap</artifactId>
    </dependency>
   ```
* 集成 SpringBootAdmin

## 20220808
* 增加gateway功能
* 全局过滤器，请求url必须凌驾uname参数
* 路由：http://127.0.0.1:7006/apps?sleep=2&uname=11
* 路由：http://127.0.0.1:7006/user/lists-sleep?sleep=2&uname=11

## 20220908
* 增加sys-user dev profile
* 增加nohi-web工程，http挡板程序

## 20230328
* 升级SpringBoot 3.0
* 升级SpringCloud 2022.0.x aka Kilburn: 2022.0.3
* 计划增加部署K8s内容

## 20230604
* 升级SpringBoot 3.07
* 升级SprignCloud 2023.0.3
