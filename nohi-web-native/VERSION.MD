# VERSION

## 时间流水
* 20221024
  * 增加SpringBoot Event
* 20230625
  * 增加lombok
* 20230626 
  * 增加knifedemo
  * 增加springboot validate
* 20230629
  * fastjson异常： 

  ```xml
    com.oracle.svm.core.jdk.UnsupportedFeatureError: No classes have been predefined during the image build to load from bytecodes at runtime.] with root cause
  
     <dependency>
      <groupId>com.alibaba.fastjson2</groupId>
      <artifactId>fastjson2</artifactId>
      <version>2.0.21.graal</version>
    </dependency>
  ```
  * fastjson2 2.0.21.grall后，json返回空

* 20230707
  * 根据fastjson2 git 项目 [example-graalvm-native] 2.0.35-SNAPSHOT 修改依赖 
    * 本地instanll 2.0.35-SNAPSHOT fastjson2
    * fastjosn-2.0.35-SNAPSHOT example-graalvm-native 打包native后可以使用 JSONObject.toJSONString(mediaContent)
  * 本工程 依赖 fastjosn-2.0.35-SNAPSHOT
    * 结果：转json仍为空

* 20230708
  * 使用追踪代理（Tracing Agent）

```shell

pom.xml 
        <buildArgs>
            --no-fallback
            -Dspring.native.remove-yaml-support=true
            -Dspring.spel.ignore=true
            --trace-class-initialization=org.apache.commons.logging.LogFactory
            --initialize-at-build-time=org.apache.commons.logging.LogFactory
            --initialize-at-build-time=java.util.jar.JarFile
            --trace-class-initialization=java.util.jar.JarFile
            --initialize-at-run-time=java.util.jar.JarFile
        </buildArgs>

-- 1 生成jar包
mvn -Dmaven.test.skip=true clean package

-- 2.得到jar包之后，执行命令运行并开始收集元数据
java -jar -agentlib:native-image-agent=config-output-dir=META-INF/native-image target/nohi-web-native-2.0.0-SNAPSHOT.jar
java -Dspring.aot.enabled=true \
    -agentlib:native-image-agent=config-output-dir=src/main/resources/META-INF/native-image \
    -jar target/nohi-web-native-2.0.0-SNAPSHOT.jar
  * 接着程序就开始运行了，我们可能需要打开网页或者测试工具将所有代码都跑完，这个是最蛋疼的，
  * 跑不完生成的镜像可能也可以运行，但指不定你哪天就报错了，或者运行到某一块有特别反射的地方
  * 也报错，就尴尬了

-- 3.拷贝 META-INF 到 工程resources目录下

-- 4.native打包
mvn -Pnative -Dmaven.test.skip=true clean package
```
   **结果失败**

  * 使用 JSON.register objectWriter ObjectReaders

```java
public void registerRsaRespItemVO() {
    log.info("===========================registerRsaRespItemVO======");
    JSON.register(RsaRespItemVO.class
            , ObjectWriters.objectWriter(RsaRespItemVO.class
                    , ObjectWriters.fieldWriter("acctNo", RsaRespItemVO::getAcctNo)
                    , ObjectWriters.fieldWriter("acctName", RsaRespItemVO::getAcctName)
                    , ObjectWriters.fieldWriter("dateTime", RsaRespItemVO::getDateTime)
                    , ObjectWriters.fieldWriter("amt", BigDecimal.class, RsaRespItemVO::getAmt)
                    , ObjectWriters.fieldWriter("balance", BigDecimal.class, RsaRespItemVO::getBalance)
            )
    );
    JSON.register(RsaRespItemVO.class, ObjectReaders.of(RsaRespItemVO::new
            , fieldReaderString("acctNo", RsaRespItemVO::setAcctName)
            , fieldReaderString("acctName", RsaRespItemVO::setAcctName)
            , fieldReaderString("dateTime", RsaRespItemVO::setDateTime)
            , fieldReader("amt", BigDecimal.class, RsaRespItemVO::setAmt)
            , fieldReader("balance", BigDecimal.class, RsaRespItemVO::setBalance)
    ));
}
```

* 20230901
  * 增加reactor netty TcpServer 示例
  * 解决TcpServer 读取指定长度报文问题

* 20240411 
  * 增加日志脱敏
    * 方式一： 依赖：logback-desensitization， appender改为：pers.liuchengyin.logbackadvice.LcyConsoleAppender
    * 方式二：实现： MaskingPatternLayout 修改appender.encoder.layout
