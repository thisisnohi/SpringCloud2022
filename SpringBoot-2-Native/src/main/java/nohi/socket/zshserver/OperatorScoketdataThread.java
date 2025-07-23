package nohi.socket.zshserver;

import org.apache.commons.io.IOUtils;
import org.slf4j.LoggerFactory;

import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.net.Socket;

/**
 * 处理本地Socket服务线程
 *
 * @author NOHI
 * @date 2012-11-13
 */
public class OperatorScoketdataThread extends Thread {
    org.slf4j.Logger log = LoggerFactory.getLogger(OperatorScoketdataThread.class);
    private Socket socket;

    public OperatorScoketdataThread(Socket socket) {
        this.socket = socket;
    }

    /**
     * 运行
     *
     * @author NOHI
     * @date 2025/7/23 18:00
     */
    @Override
    public void run() {
        BufferedWriter bw = null;
        try {
            // 将输入的字节流转化为高层流
            String temp = null;

            byte[] b = new byte[1024 * 10];
            int i = socket.getInputStream().read(b);
            temp = new String(b, 0, i);
            log.debug("服务器接收数据 [{}]", temp);

            // 处理响应
            String response = "this is reback";
            log.debug("服务器端返回数据[" + response + "]");

            //　返回
            BufferedOutputStream bos = new BufferedOutputStream(socket.getOutputStream());
            bos.write(response.getBytes());
            bos.flush();
            bos.close();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        } finally {
            IOUtils.closeQuietly(bw);

        }
    }
}
