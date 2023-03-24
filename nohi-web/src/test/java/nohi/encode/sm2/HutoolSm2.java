package nohi.encode.sm2;

import cn.hutool.core.util.HexUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.SmUtil;
import cn.hutool.crypto.asymmetric.KeyType;
import cn.hutool.crypto.asymmetric.SM2;
import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;
import java.security.KeyPair;
import java.util.Base64;

/**
 * <h3>SpringCloud2022</h3>
 *
 * @author NOHI
 * @description <p>hutool sm2</p>
 * @date 2023/03/23 17:06
 **/
public class HutoolSm2 {

    @Test
    public void testsm2() {
        String text = "人最宝贵的是生命.生命对每个人只有一次.人的一生应当这样度过：当他回首往事的时候,不会因为虚度年华而悔恨,也不会因为碌碌无为而羞耻.这样,在临死的时候,他能够说：“我已把自己的整个的生命和全部的精力献给了世界上最壮丽的事业---------为人类的解放而斗争.”";
        System.out.println("原文：" + text);

        KeyPair pair = SecureUtil.generateKeyPair("SM2");
        byte[] privateKey = pair.getPrivate().getEncoded();
        byte[] publicKey = pair.getPublic().getEncoded();
        System.out.println("公钥：\n" + bytesToBase64(publicKey));
        System.out.println("私钥：\n" + bytesToBase64(privateKey));

        SM2 sm2 = SmUtil.sm2(privateKey, publicKey);
        // 公钥加密，私钥解密
        String encryptStr = sm2.encryptBcd(text, KeyType.PublicKey);
        System.out.println("加密后：" + encryptStr);

        String decryptStr = StrUtil.utf8Str(sm2.decryptFromBcd(encryptStr, KeyType.PrivateKey));
        System.out.println("解密后：" + decryptStr);
        //加签
        String sign = sm2.signHex(HexUtil.encodeHexStr(text));
        System.out.println("签名：" + sign);
        //验签
        boolean verify = sm2.verifyHex(HexUtil.encodeHexStr(text), sign);
        System.out.println("验签：" + verify);
    }


    /**
     * 字节数组转Base64编码
     *
     * @param bytes 字节数组
     * @return Base64编码
     */
    private static String bytesToBase64(byte[] bytes) {
        byte[] encodedBytes = Base64.getEncoder().encode(bytes);
        return new String(encodedBytes, StandardCharsets.UTF_8);
    }

    /**
     * Base64编码转字节数组
     *
     * @param base64Str Base64编码
     * @return 字节数组
     */
    private static byte[] base64ToBytes(String base64Str) {
        byte[] bytes = base64Str.getBytes(StandardCharsets.UTF_8);
        return Base64.getDecoder().decode(bytes);
    }
}
