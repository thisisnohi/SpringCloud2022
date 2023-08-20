package nohi.socket.njserver;

import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.net.Socket;

/**
 * 处理本地Socket服务线程
 *
 * @author NOHI
 * @date 2012-11-13
 */
@Slf4j
public class NjOperatorScoketdataThread extends Thread {
    private static String CHARSET = "UTF-8";
    private Socket socket;

    public NjOperatorScoketdataThread(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try (InputStream is = socket.getInputStream();
             OutputStream os = socket.getOutputStream();
        ) {
            // 读取指定字节
            byte[] headLenBytes = NjMsgUtils.readBytesFromInputStream(is, 2);
            byte[] bodyLenBytes = NjMsgUtils.readBytesFromInputStream(is, 4);
            byte[] encode = NjMsgUtils.readBytesFromInputStream(is, 1);
            log.debug("headLenBytes[{}] bodyLenBytes[{}]", NjMsgUtils.parseByte2HexStr(headLenBytes), NjMsgUtils.parseByte2HexStr(bodyLenBytes), new String(encode));
            // 十六进制转十进制
            int headLen = Integer.parseInt(NjMsgUtils.parseByte2HexStr(headLenBytes), 16);
            int bodyLen = Integer.valueOf(NjMsgUtils.parseByte2HexStr(bodyLenBytes), 16);
            log.debug("headLen[{}] bodyLen[{}]", headLen, bodyLen);
            // 读取报文
            byte[] headBytes = NjMsgUtils.readBytesFromInputStream(is, headLen);
            byte[] bodyBytes = NjMsgUtils.readBytesFromInputStream(is, bodyLen);
            String head = new String(headBytes);
            String body = new String(bodyBytes);
            log.debug("headLen[{}]\nbodyLen[{}]", head, body);


            String respHead = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><head><version>RESP</version><ref>I9000577910163</ref><sysCode>O95533</sysCode><busCode>512301</busCode><tradeSrc>I</tradeSrc><sender>I90005</sender><reciver>O95533</reciver><date>20210929</date><time>101129</time><reSnd>N</reSnd><rst><tradeCode>99</tradeCode><busiCode>9999</busiCode><info></info></rst><by></by></head>";
            String respBody = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><data><filename>RESP</filename><fnumber>6</fnumber><jbr>2824</jbr></data>";
            int respHeadLen = respHead.getBytes(CHARSET).length;
            int respBodyLen = respBody.getBytes(CHARSET).length;
            // 回写报文
            NjMsgUtils.writeAndFlush(os, NjMsgUtils.msgLen2HexBytes(respHeadLen, 4));
            NjMsgUtils.writeAndFlush(os, NjMsgUtils.msgLen2HexBytes(respBodyLen, 8));
            NjMsgUtils.writeAndFlush(os, NjMsgUtils.msgLen2HexBytes(0, 2));
            NjMsgUtils.writeAndFlush(os, respHead, CHARSET);
            NjMsgUtils.writeAndFlush(os, respBody, CHARSET);
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage(), e);
        }
    }
}
