package nohi.test;


import org.junit.jupiter.api.Test;

import java.io.*;
import java.net.Socket;


/**
 * 测试类,
 *
 * @author NOHI
 * @date: 2012-12-5
 */
public class _01SocketClient {

    /**
     * 测试之前得启动服务端socket
     */
    @Test
    public void socketClientSendMessage() {

        String ip = "127.0.0.1";
        int port = 9001;
        int timeout = 60 * 1000;
        String msg = "this is from nohi";

        OutputStream os = null;
        BufferedReader in = null;
        String temp = null;
        try {
            System.out.println("IP:" + ip + " , port:" + port);
            Socket client = new Socket(ip, port);

            //设置超时
            client.setSoTimeout(timeout);

            os = client.getOutputStream();
            PrintWriter pw = new PrintWriter(os);
            pw.write(msg);
            pw.flush();

            in = new BufferedReader(new InputStreamReader(client.getInputStream()));
            temp = in.readLine();
            System.out.println("getMessage: " + temp);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (os != null) {
                    os.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (IOException e) {
            }
        }
    }
}
