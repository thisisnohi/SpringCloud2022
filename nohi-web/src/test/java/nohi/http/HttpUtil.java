package nohi.http;


import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.nio.charset.Charset;

/**
 * @Description
 * @Author yangfeng
 * @Date 2020/12/15 17:06
 */
public class HttpUtil {
    private static Logger log = LoggerFactory.getLogger(HttpUtil.class);
    /**
     * org.apache.http.client.HttpClient;
     * 创建一个httpclient对象
     */
    private static HttpClient httpclient = HttpClients.createDefault();

    /**
     * @return
     * @Author Administrator
     * @Date 2018年11月11日
     */
    public static void main(String[] args) {
        try {
            // 创建http的发送方式对象，是GET还是POST
            String url = "http://localhost:8080/jeecg-boot/file/upload";
            File file = new File("e:/tomcat/ZL_000000000000_530000000000_0025-401.json");
            HttpPost httppost = new HttpPost(url);
            log.info(" URL: " + url);
            httppost.addHeader("Charset", "utf-8");
            // 创建要发送的实体，就是key-value的这种结构，借助于这个类，可以实现文件和参数同时上传
            MultipartEntityBuilder fileEntity = MultipartEntityBuilder.create();
            // 设置编码
            Charset charset = Charset.forName("utf-8");
            fileEntity.setCharset(charset);
            // 追加要发送的文本信息并设置编码格式
            fileEntity.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
            fileEntity.addPart("file", new FileBody(file, ContentType.APPLICATION_OCTET_STREAM));
            HttpEntity httpEntity = fileEntity.build();
            httppost.setEntity(httpEntity);
            // 执行httppost对象并获得response
            HttpResponse response = httpclient.execute(httppost);
            // 状态码
            int statusCode = response.getStatusLine().getStatusCode();
            HttpEntity resEntity = response.getEntity();
            // 获得返回来的信息，转化为字符串string
            String respopnse = EntityUtils.toString(resEntity);
            log.info("respopnse body: " + respopnse);
        } catch (Exception e) {
            log.error("doPost occur a exception", e);
        }
    }
}
