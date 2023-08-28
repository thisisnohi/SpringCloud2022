package nohi.demo;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.concurrent.TimeUnit;

/**
 * <h3>nohi-web-native</h3>
 *
 * @author NOHI
 * @description <p>tcpclient</p>
 * @date 2023/08/25 10:58
 **/
@Slf4j
public class TcpClient {

    @Test
    public void tcpclient(){
        String host = "127.0.0.1";
        int port = 8888;

        String msg1 = "1234567890";
        String msg2 = "abcd";
        String msg3 = "12345";
        String lenStr = "0019";

        try (Socket socket = new Socket(host, port);
             OutputStream os = socket.getOutputStream();
             InputStream is = socket.getInputStream();

        ) {
            // 发送消息
            byte[] data = (lenStr + msg1).getBytes();
            log.info("发送消息1[{}]", data.length);
            os.write(data);
            os.flush();

            TimeUnit.SECONDS.sleep(1);

            data = msg2.getBytes();
            log.info("发送消息2[{}]", data.length);
            os.write(data);
            os.flush();

            data = msg3.getBytes();
            log.info("发送消息3[{}]", data.length);
            os.write(data);
            os.flush();

//            socket.shutdownOutput();

            StringBuffer sb = new StringBuffer();
            byte[] bs = new byte[1024];
            int size = 0;

            while ((size = is.read(bs)) > -1) {
                sb.append(new String(bs, 0, size));
            }
            log.info("响应:{}", sb.toString());

        } catch (Exception e) {
            log.error("异常:{}", e.getMessage());
        }


    }
}
