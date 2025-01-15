package nohi.zhst;

import org.junit.jupiter.api.Test;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

/**
 * <h3>nohi-web-native</h3>
 *
 * @author NOHI
 * @description <p></p>
 * @date 2023/09/28 16:33
 **/
public class TestH5 {

    /**
     * 小牛H5 aes 解密
     */
    @Test
    public void testH5AesDecode() throws NoSuchPaddingException, IllegalBlockSizeException, UnsupportedEncodingException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
        // https://alyecs223.xnzn.net/zhst-xnh5-in/tengyun-api/api/v1/applet/sourcecontrol/getSourceControlForApp?version=1.0&signType=md5&content=U2FsdGVkX18063R1DMvhmsEM3%2F6%2BmPyWGIfV2QSsabnmDPfdpTnFFg%2FQYNlPeHMlpPIsvmV7B6tGGksJAMdU4A%3D%3D&nonceStr=wwteDm&timestamp=1695886991968&sign=51ADE95C4F790E698C5DAAA03D77BAC8
        // version=1.0&signType=md5&content=U2FsdGVkX18063R1DMvhmsEM3%2F6%2BmPyWGIfV2QSsabnmDPfdpTnFFg%2FQYNlPeHMlpPIsvmV7B6tGGksJAMdU4A%3D%3D&nonceStr=wwteDm&timestamp=1695886991968&sign=51ADE95C4F790E698C5DAAA03D77BAC8
        String content = "U2FsdGVkX18063R1DMvhmsEM3%2F6%2BmPyWGIfV2QSsabnmDPfdpTnFFg%2FQYNlPeHMlpPIsvmV7B6tGGksJAMdU4A%3D%3D";
        content = "U2FsdGVkX18063R1DMvhmsEM3/6+mPyWGIfV2QSsabnmDPfdpTnFFg/QYNlPeHMlpPIsvmV7B6tGGksJAMdU4A==";
        EnAndDeCryptionUtils.decrypt(content, "zhst_ht_20230830", "UTF-8");
    }
}
