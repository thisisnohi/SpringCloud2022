//package nohi.zhst;
//
//import cn.hutool.core.codec.Base64;
//import cn.hutool.core.util.StrUtil;
//import cn.hutool.crypto.Mode;
//import cn.hutool.crypto.Padding;
//import cn.hutool.crypto.symmetric.AES;
//import lombok.extern.slf4j.Slf4j;
//import net.xnzn.core.common.config.sys.EncryptProperties;
//import org.springframework.stereotype.Component;
//
//import javax.annotation.Resource;
//import javax.crypto.spec.IvParameterSpec;
//import javax.crypto.spec.SecretKeySpec;
//import java.nio.charset.StandardCharsets;
//
///**
// * @description:AES加解密
// * @author: twd
// * @create: 2022/7/28 14:24
// **/
//@Slf4j
//@Component
//public class AesEncryptUtil {
//
//    private static final String KEY_ALGORITHM = "AES";
//    @Resource
//    private EncryptProperties encryptProperties;
//
//    public static AesEncryptUtil getInstance() {
//        return SpringContextHolder.getBean(AesEncryptUtil.class);
//    }
//
//    /**
//     * 加密
//     *
//     * @param encryptStr
//     * @return
//     */
//    public String aesEncrypt(String encryptStr) {
//        if (StrUtil.isBlank(encryptStr)) {
//            return encryptStr;
//        }
//        AES aes = new AES(Mode.CBC, Padding.ZeroPadding,
//                new SecretKeySpec(encryptProperties.getKey().getBytes(), KEY_ALGORITHM),
//                new IvParameterSpec(encryptProperties.getKey().getBytes()));
//        return aes.encryptBase64(encryptStr);
//    }
//
//    /**
//     * 解密
//     *
//     * @param decodeStr
//     * @return
//     */
//    public String aesDecode(String decodeStr) {
//        if (StrUtil.isBlank(decodeStr)) {
//            return decodeStr;
//        }
//        AES aes = new AES(Mode.CBC, Padding.ZeroPadding,
//                new SecretKeySpec(encryptProperties.getKey().getBytes(), KEY_ALGORITHM),
//                new IvParameterSpec(encryptProperties.getKey().getBytes()));
//        byte[] resultByte;
//        try {
//            resultByte = aes.decrypt(Base64.decode(decodeStr.getBytes(StandardCharsets.UTF_8)));
//        } catch (Exception e) {
//            log.info("字段解密异常:" + e.getMessage());
//            return decodeStr;
//        }
//        return new String(resultByte, StandardCharsets.UTF_8).trim();
//    }
//}
