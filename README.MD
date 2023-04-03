# SpringCloud2022

> create by nohi 20230329
Just demo for Spring Cloud

## 基础版本

* springboot

  ```
  <parent>
      <groupId>org.springframework.boot</groupId>
      <artifactId>spring-boot-starter-parent</artifactId>
      <version>3.0.1</version>
      <relativePath/> <!-- lookup parent from repository -->
    </parent>
  ```

* springcloud

  ```
  <spring.cloud-version>2022.0.1</spring.cloud-version>
  ```

## Eureka
* 增加安全
  * 服务端 
    ```xml
     <!-- 安全 -->
    <dependency>
      <groupId>org.springframework.boot</groupId>
      <artifactId>spring-boot-starter-security</artifactId>
    </dependency>
    ```
    ```yaml
    spring:
      security:
          user:
            name: ${EUREKA_USER:admin}
            password: ${EUREKA_PASS:admin}
    eureka:
      client:
        service-url:
          defaultZone: http://${spring.security.user.name}:${spring.security.user.password}@${eureka.instance.hostname}:${server.port}/eureka/
    ```
    ```java
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf()
                .disable()
                .authorizeHttpRequests()
                .anyRequest()
                .authenticated()
                .and()
                .httpBasic()
                .and()
                .formLogin();
        return http.build();
    }
    ```
  * 客户端
    * 不在需要@EnableDiscoveryClient、@EnableEurekaClient, 注册已经删除。SpringBoot自动配置，自动发现注册中心

## Knife4j

> [参考](https://doc.xiaominfo.com/docs/quick-start)

### 说明

> :information_source: 说明
> Spring Boot 3 只支持OpenAPI3规范
> Knife4j提供的starter已经引用springdoc-openapi的jar，开发者需注意避免jar包冲突
> JDK版本必须 >= 17
> 详细Demo请参考knife4j-spring-boot3-demo

### 依赖

```xml
<dependency>
    <groupId>com.github.xiaoymin</groupId>
    <artifactId>knife4j-openapi3-jakarta-spring-boot-starter</artifactId>
    <version>4.1.0</version>
</dependency>
```

### 配置

```
# springdoc-openapi项目配置
springdoc:
  swagger-ui:
    path: /swagger-ui.html
    tags-sorter: alpha
    operations-sorter: alpha
  api-docs:
    path: /v3/api-docs
  group-configs:
    - group: 'default'
      paths-to-match: '/**'
      # 扫描路径
      packages-to-scan: nohi.ms
# knife4j的增强配置，不需要增强可以不配
knife4j:
  enable: true
  setting:
    language: zh_cn
```

### 使用(OpenAPI3)

> @Tag  @Operation

```java
@RestController
@RequestMapping("body")
@Tag(name = "body参数")
public class BodyController {

   @Operation(summary = "普通body请求")
   @PostMapping("/body")
   public ResponseEntity<FileResp> body(@RequestBody FileResp fileResp){
       return ResponseEntity.ok(fileResp);
   }

   @Operation(summary = "普通body请求+Param+Header+Path")
   @Parameters({
           @Parameter(name = "id",description = "文件id",in = ParameterIn.PATH),
           @Parameter(name = "token",description = "请求token",required = true,in = ParameterIn.HEADER),
           @Parameter(name = "name",description = "文件名称",required = true,in=ParameterIn.QUERY)
   })
   @PostMapping("/bodyParamHeaderPath/{id}")
   public ResponseEntity<FileResp> bodyParamHeaderPath(@PathVariable("id") String id,@RequestHeader("token") String token, @RequestParam("name")String name,@RequestBody FileResp fileResp){
       fileResp.setName(fileResp.getName()+",receiveName:"+name+",token:"+token+",pathID:"+id);
       return ResponseEntity.ok(fileResp);
   }
}
```

