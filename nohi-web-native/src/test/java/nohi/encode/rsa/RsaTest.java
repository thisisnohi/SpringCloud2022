package nohi.encode.rsa;

import nohi.web.utils.FileUtils;
import nohi.web.utils.RSAUtils;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileInputStream;
import java.security.PrivateKey;
import java.security.PublicKey;

/**
 * @author NOHI
 * @description:
 * @create 2020-06-28 16:16
 **/
public class RsaTest {

    @Test
    public void testRsa1024() throws Exception {
        String courseFile = new File("").getCanonicalPath();
        System.out.println("courseFile:" + courseFile);

        String path = courseFile + "/src/main/resources";
        String priFile = path + "/rsa1024/rsa_private_key_pkcs8.pem";
        String pubFile = path + "/rsa1024/rsa_public_key.pem";

        PublicKey publicKey = RSAUtils.loadPublicKey(new FileInputStream(new File(pubFile)));
        PrivateKey privateKey = RSAUtils.loadPrivateKey(new FileInputStream(new File(priFile)));

        // 加密
        String data = "{\"e\":\"sign_check_fail\",\"c\":2}";
        System.out.println("明文:" + data);
        // 公钥加密
        String encryptStr = RSAUtils.encryptData(data, publicKey);
        System.out.println("加密数据:" + encryptStr);

        // 解密  私钥解密
        String uncrypt = RSAUtils.decryptData(encryptStr, privateKey);
        System.out.println("解密数据:" + encryptStr);

        // 签名 私钥签名
        String sign = RSAUtils.sign(encryptStr, privateKey);
        System.out.println("加签:" + sign);

        // 验签
        boolean result = RSAUtils.verify(encryptStr, publicKey, sign);
        System.out.println("验签:" + result);
    }

    @Test
    public void testRsa加密加签2() throws Exception {
        String courseFile = new File("").getCanonicalPath();
        System.out.println("courseFile:" + courseFile);
        String dir = courseFile + "/src/main/resources";
        String pubFile = dir + "/rsa1024/rsa_public_key.pem";
        String priFile = dir + "/rsa1024/rsa_private_key_pkcs8.pem";
        String loalPubFile = dir + "/rsa1024/rsa_public_key.pem";

        String priKey = FileUtils.readStringfromPath(priFile);
        String pubKey = FileUtils.readStringfromPath(pubFile);
        String localPub = FileUtils.readStringfromPath(loalPubFile);

//        System.out.println("priKey:\n" + priKey);
        System.out.println("pubKey:\n" + pubKey);
        System.out.println("localPub:\n" + localPub);

        pubKey = RSAUtils._2Stri(pubKey);
        priKey = RSAUtils._2Stri(priKey);
        localPub = RSAUtils._2Stri(localPub);

        System.out.println("pubKey:\n" + pubKey);
        System.out.println("priKey:\n" + priKey);

        PrivateKey privateKey = RSAUtils.loadPrivateKey(priKey);
        PublicKey publicKey = RSAUtils.loadPublicKey(pubKey);
        PublicKey locaPublicKey = RSAUtils.loadPublicKey(localPub);
        // 加密
        String data = "{\"htbh\":\"20200629\"}";
        System.out.println(data);
        String encryptStr = RSAUtils.encryptData(data, publicKey);
        System.out.println("加密数据:" + encryptStr);

        // RSA签名
        String sign = RSAUtils.sign(encryptStr, privateKey);
        System.out.println("加签:" + sign);

        // 验签
        boolean result = RSAUtils.verify(encryptStr, locaPublicKey, sign);
        System.out.println("验签结果1:" + result);

        // 解密  私钥解密
        String uncrypt = RSAUtils.decryptData(encryptStr, privateKey);
        System.out.println("解密数据:" + uncrypt);
    }
}
