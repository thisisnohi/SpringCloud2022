package nohi.zhst;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

/**
 * @Description: 加解密公共类
 * @Author: jizhen
 * @CreateDate: 2022/10/18 9:48
 * @UpdateUser: jizhen
 * @UpdateDate: 2022/10/18 9:48
 * @UpdateRemark:
 * @Version: 1.0
 */
@Slf4j
@Component
@SuppressWarnings("all")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class EnAndDeCryptionUtils {

    private static final String KEY_ALGORITHM = "AES";
    private static final String DEFAULT_CIPHER_ALGORITHM = "AES/ECB/PKCS5Padding";// 默认的加密算法

    /**
     * 数据解密
     *
     * @param jsonStr   解密字符串
     * @param encodeKey 解密密钥
     * @return 解密后数据
     * @throws Exception 异常
     */
    public String serviceDecrypt(String jsonStr, String encodeKey) throws Exception {
        if (StringUtils.isBlank(jsonStr)) {
            return "";
        }
        return this.aesDecrypt(jsonStr, encodeKey);
    }

    /**
     * aes公共解密方法
     *
     * @param jsonStr   加密字符串
     * @param publicKey 解密密钥
     * @return 解密后数据
     */
    public String aesDecrypt(String jsonStr, String publicKey) throws Exception {
        String responseStr = "";
        try {
            if (StringUtils.isBlank(jsonStr)) {
                log.info("解密数据为空");
                return responseStr;
            }
            responseStr = decrypt(jsonStr, publicKey, "utf-8");
            log.info("解密结果为: {}", responseStr);
            return responseStr;
        } catch (Exception e) {
            log.error("res解密失败:{}", e.getMessage(), e);
            throw new Exception("res解密失败!");
        }
    }

    /**
     * 数据加密
     *
     * @param jsonStr   加密字符串
     * @param encodeKey 加密密钥
     * @return 加密后数据
     * @throws Exception 异常
     */
    public String serviceEncrypt(String jsonStr, String encodeKey) throws Exception {
        if (StringUtils.isBlank(jsonStr)) {
            return "";
        }
        return this.aesEncrypt(jsonStr, encodeKey);
    }

    /**
     * aes公共加密
     *
     * @param jsonStr   加密字符串
     * @param publicKey 加密密钥
     * @return 加密后数据
     */
    public String aesEncrypt(String jsonStr, String publicKey) throws Exception {
        // 加密响应
        String responseStr = "";
        try {
            if (StringUtils.isBlank(jsonStr)) {
                log.info("加密数据为空");
                return responseStr;
            }
            responseStr = encrypt(jsonStr, publicKey, "utf-8");
            log.info("加密结果为: {}", responseStr);
            return responseStr;
        } catch (Exception e) {
            log.error("AES加密失败:{}", e.getMessage(), e);
            throw new Exception("AES加密失败!");
        }
    }

    /**
     * AES 加密操作
     *
     * @param content 待加密内容
     * @param key     密钥
     * @return 返回Base64转码后的加密数据
     * @throws NoSuchPaddingException
     * @throws NoSuchAlgorithmException
     * @throws UnsupportedEncodingException
     * @throws InvalidKeyException
     * @throws BadPaddingException
     * @throws IllegalBlockSizeException
     */
    public static String encrypt(String content, String key, String charset) throws NoSuchAlgorithmException, NoSuchPaddingException, UnsupportedEncodingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        Cipher cipher = Cipher.getInstance(DEFAULT_CIPHER_ALGORITHM);// 创建密码器
        byte[] byteContent = content.getBytes(charset);
        cipher.init(Cipher.ENCRYPT_MODE, getSecretKey(key));// 初始化为加密模式的密码器
        byte[] result = cipher.doFinal(byteContent);// 加密
        return Base64.encodeBase64String(result);// 通过Base64转码返回
    }

    /**
     * AES 解密操作
     *
     * @param content 待解密内容
     * @param key     密钥
     * @param charset 字符编码
     * @return 返回Base64转码后的解密数据
     * @throws NoSuchPaddingException
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeyException
     * @throws BadPaddingException
     * @throws IllegalBlockSizeException
     * @throws UnsupportedEncodingException
     */
    public static String decrypt(String content, String key, String charset) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, UnsupportedEncodingException {
        // 实例化
        Cipher cipher = Cipher.getInstance(DEFAULT_CIPHER_ALGORITHM);
        // 使用密钥初始化，设置为解密模式
        cipher.init(Cipher.DECRYPT_MODE, getSecretKey(key));
        // 执行操作
        byte[] result = cipher.doFinal(Base64.decodeBase64(content));
        return new String(result, charset);
    }

    /**
     * 生成加密秘钥
     *
     * @return 加密密钥
     * @throws NoSuchAlgorithmException
     */
    private static SecretKeySpec getSecretKey(final String key) throws NoSuchAlgorithmException {
        // 返回生成指定算法密钥生成器的 KeyGenerator 对象
        KeyGenerator kg = KeyGenerator.getInstance(KEY_ALGORITHM);
        // AES 要求密钥长度为 128
        SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
        secureRandom.setSeed(key.getBytes());
        kg.init(128, secureRandom);
        // 生成一个密钥
        SecretKey secretKey = kg.generateKey();
        return new SecretKeySpec(secretKey.getEncoded(), KEY_ALGORITHM);// 转换为AES专用密钥
    }

}
