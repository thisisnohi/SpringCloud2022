package nohi.web.utils;

/**
 * @author NOHI
 * 2022-04-28 14:27
 **/
public class HttpClientConfig {
    /**
     * 从连接池获取连接超时时间
     */
    static int httpConnectTimeout = 10000;
    /**
     * 连接后，获取响应时间
     */
    static int httpSocketTimeout = 30000;


    /**
     * 连接超时时间
     */
    static int httpPoolTimeToLive = 100;
    /**
     * 连接池最大连接
     */
    static int httpPoolMaxPoolSize = 20;

    /**
     * 单路由最大连接数
     */
    static int httpPoolMaxPerRoute = 20;

    /**
     * 线程池监控时间间隔
     */
    static int httpPoolMonitorInterval = 5000;
    /**
     * 空闲连接时间，超过则关闭连接
     */
    static int httpPoolIdelTimeout = 2000;
    static String charSet = "UTF-8";

    static {
        /*
         * 通过配置文件获取
         */
        /*
        httpConnectTimeout = Integer.parseInt(PropertieUtils.getInstance().getDefaultValue("httpConnectTimeout", "3000"));
        httpSocketTimeout = Integer.parseInt(PropertieUtils.getInstance().getDefaultValue("httpSocketTimeout", "30000"));
        httpMaxPoolSize = Integer.parseInt(PropertieUtils.getInstance().getDefaultValue("httpMaxPoolSize", "100"));
        httpMonitorInterval = Integer.parseInt(PropertieUtils.getInstance().getDefaultValue("httpMonitorInterval", "10000"));
        httpIdelTimeout = Integer.parseInt(PropertieUtils.getInstance().getDefaultValue("httpIdelTimeout", "5000"));
        */
    }
}
