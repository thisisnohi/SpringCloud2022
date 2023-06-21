# SpringBoot Native
> create by nohi 20230618

SpringBoot Native with SpringBoot3

## 过程
> 参考：https://blog.csdn.net/BASK2311/article/details/128830624

*  [Spring官网构建应用](https://start.spring.io/)
  * Dependencies 添加 Srping Web 和 GraalVM Native Support
* mvn native:build来构建spring boot3应用程序
## 经过尝试：
* 第一步：mvn -Pnative -DskipTests spring-boot:build-image
* 第二步：mvn native:build 可以正常打包
## 最终
* mvn clean package -Pnative

