package nohi.socket.njserver;

import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * @author NOHI
 * 2021-10-11 22:53
 **/
public class NjMsgUtils {

    public static void writeAndFlush(OutputStream os, byte[] bs) throws IOException {
        os.write(bs);
        os.flush();
    }

    public static void writeAndFlush(OutputStream os, String msg, String charSet) throws IOException {
        System.out.println("msg:" + msg);
        os.write(msg.getBytes(charSet));
        os.flush();
    }

    /**
     * 消息长度转换为指定长度字符串
     *
     * @param msgLength 消息长度
     * @param length    返回最大长度
     * @return 返回
     */
    public static byte[] msgLen2HexBytes(int msgLength, int length) {
        String hx = Integer.toHexString(msgLength);
        hx = StringUtils.leftPad(hx, length, "0");
        return parseHexStr2Byte(hx);
    }

    /**
     * 将16进制转换为二进制
     *
     * @param hexStr 十六进制
     * @return 返回
     */
    public static byte[] parseHexStr2Byte(String hexStr) {
        if (hexStr.length() < 1) {
            return null;
        }
        int tmp = 2;
        byte[] result = new byte[hexStr.length() / 2];
        for (int i = 0; i < hexStr.length() / tmp; i++) {
            int high = Integer.parseInt(hexStr.substring(i * 2, i * 2 + 1), 16);
            int low = Integer.parseInt(hexStr.substring(i * 2 + 1, i * 2 + 2), 16);
            result[i] = (byte) (high * 16 + low);
        }
        return result;
    }

    /**
     * 将二进制转换成16进制
     *
     * @param buf 字节数组
     * @return 返回
     */
    public static String parseByte2HexStr(byte[] buf) {
        StringBuilder sb = new StringBuilder();
        for (byte b : buf) {
            String hex = Integer.toHexString(b & 0xFF);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            sb.append(hex.toUpperCase());
        }
        return sb.toString();
    }

    public static byte[] readBytesFromInputStream(InputStream brRight, int length) throws IOException {
        int readSize;
        byte[] bytes;
        bytes = new byte[length];

        long lengthTmp = length;
        // start from zero
        long index = 0;
        while ((readSize = brRight.read(bytes, (int) index, (int) lengthTmp)) != -1) {
            lengthTmp -= readSize;
            if (lengthTmp == 0) {
                break;
            }
            index = index + readSize;
        }
        return bytes;
    }
}
