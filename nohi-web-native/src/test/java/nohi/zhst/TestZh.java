package nohi.zhst;

import COM.CCB.EnDecryptAlgorithm.MCipherDecryptor;
import cn.hutool.socket.protocol.MsgDecoder;
import com.alibaba.fastjson.parser.Feature;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.TypeReference;
import org.junit.jupiter.api.Test;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.ShortBufferException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * <h3>nohi-web-native</h3>
 *
 * @author NOHI
 * @description <p></p>
 * @date 2023/09/28 16:33
 **/
public class TestZh {


    @Test
    public void testEncode() throws Exception {
        String msg = "2ZjkvGDjJh7NQA1azOLG8T/qxfPMrzIkCusBVwYDsGEoNXYB6xUz5Hw2djZiMbwidsIi6XnDcZ1O\\ne,9tgiYXUyieBtMcmZskA77ULmsFlhB4UxNOz,pFXPSUr1Lsdq5xheQ/v1dvNI1fl0WC5xqTiXfo\\n1KFyYJUqAoSmCW16G9PaUsINMx,OclYJIjtXUZS1WBoBzYMK6UePmq6ZDFtpDTDG5K4adMQd,c57\\nwnG7,1Zpa7FZoypXxERUuHIJuEff9F/cUs,mwHw13ZQV5YXwAM,A0ilwQbRNNAQzWw,Hvq7FJ2/a\\nVmR8eP3QF,spHpSEeXlfAyOTXssuyZhgEsU6oQ";
        String key = "s33psFEWPFOS4bDYQFDgkbWTA6VGOHm7";
        System.out.println("key:" + key);
        System.out.println("msg:" + msg);
        String decode = new MCipherDecryptor(key).doDecrypt(msg);
        System.out.println("decode:" + decode);
    }
}
