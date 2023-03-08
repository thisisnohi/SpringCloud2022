package nohi.test.nj;

import lombok.extern.slf4j.Slf4j;
import nohi.socket.njserver.NjMsgUtils;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.Socket;

/**
 * @author NOHI
 * 2021-10-11 19:22
 **/
@Slf4j
public class TestNjClient {


    @Test
    public void testLen() {
        int headLen = 359;

        int height = headLen / 256;
        int low = headLen % 256;
        System.out.println(height + "-" + low);

        String hx = Integer.toHexString(headLen);
        System.out.println("headLen:" + headLen + ",hx:" + hx);
        hx = StringUtils.leftPad(hx, 4, "0");
        System.out.println("headLen:" + headLen + ",hx:" + hx);

        // 67
        byte[] bs = NjMsgUtils.parseHexStr2Byte(hx);
        System.out.println("bs:" + bs);
        for (byte b : bs) {
            System.out.println(b);
        }

        String ss = NjMsgUtils.parseByte2HexStr(bs);
        System.out.println("ss:" + ss);

    }

    @Test
    public void testnj() throws UnsupportedEncodingException {
        String charSet = "UTF-8";
        String head = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><head><version>1.0.0</version><ref>I9000577910163</ref><sysCode>O95533</sysCode><busCode>512301</busCode><tradeSrc>I</tradeSrc><sender>I90005</sender><reciver>O95533</reciver><date>20210929</date><time>101129</time><reSnd>N</reSnd><rst><tradeCode>99</tradeCode><busiCode>9999</busiCode><info></info></rst><by></by></head>";
        String body = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><data><filename>NJCB_95533_512301_320100_YYFZF_20210929101128.txt.gz</filename><fnumber>6</fnumber><jbr>2824</jbr></data>";
        int headLen = head.getBytes(charSet).length;
        int bodyLen = body.getBytes(charSet).length;

        String ip = "127.0.0.1";
        int port = 9002;
        int timeout = 60 * 1000;

        OutputStream os = null;
        InputStream is = null;
        String temp = null;
        try {
            System.out.println("IP:" + ip + " , port:" + port);
            Socket client = new Socket(ip, port);
            //设置超时
            client.setSoTimeout(timeout);
            os = client.getOutputStream();
            is = client.getInputStream();
//            os = new FileOutputStream("/Users/nohi/Downloads/1.bin");

            //
            System.out.println("headLen:" + headLen);
            System.out.println("bodyLen:" + bodyLen);

            NjMsgUtils.writeAndFlush(os, NjMsgUtils.msgLen2HexBytes(headLen, 4));
            NjMsgUtils.writeAndFlush(os, NjMsgUtils.msgLen2HexBytes(bodyLen, 8));
            NjMsgUtils.writeAndFlush(os, NjMsgUtils.msgLen2HexBytes(0, 2));
            NjMsgUtils.writeAndFlush(os, head, charSet);
            NjMsgUtils.writeAndFlush(os, body, charSet);

            System.out.println("==============获取响应======================");
            log.debug("==============获取响应======================");
            // 读取指定字节
            byte[] headLenBytes = NjMsgUtils.readBytesFromInputStream(is, 2);
            byte[] bodyLenBytes = NjMsgUtils.readBytesFromInputStream(is, 4);
            byte[] encode = NjMsgUtils.readBytesFromInputStream(is, 1);
            log.debug("headLenBytes[{}] bodyLenBytes[{}]", NjMsgUtils.parseByte2HexStr(headLenBytes), NjMsgUtils.parseByte2HexStr(bodyLenBytes), new String(encode));
            // 十六进制转十进制
            int respHeadLen = Integer.parseInt(NjMsgUtils.parseByte2HexStr(headLenBytes), 16);
            int respBodyLen = Integer.valueOf(NjMsgUtils.parseByte2HexStr(bodyLenBytes), 16);
            log.debug("respHeadLen[{}] respBodyLen[{}]", respHeadLen, respBodyLen);
            // 读取报文
            byte[] reapHeadBytes = NjMsgUtils.readBytesFromInputStream(is, respHeadLen);
            byte[] repsBodyBytes = NjMsgUtils.readBytesFromInputStream(is, respBodyLen);
            String respHead = new String(reapHeadBytes);
            String respBody = new String(repsBodyBytes);
            log.debug("respHead[{}]\nrespBody[{}]", respHead, respBody);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            IOUtils.closeQuietly(os);
            IOUtils.closeQuietly(is);
        }

    }


}
