package nohi.zhst;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.http.ContentType;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.google.common.base.Joiner;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

/**
 * <h3>nohi-web-native</h3>
 *
 * @author NOHI
 * @description <p>小牛</p>
 * @date 2023/08/14 14:14
 **/
@Slf4j
public class XiaoNiuApi {

    @Test
    public void testCustomerQuery() {

        /**
         * appid 应用ID（平台提供）
         * appsecret 应用秘钥（平台提供）
         * version 版本号，默认1.0.0
         * signtype 加密方法，md5 / sha256
         * timestamp 当前时间戳
         * nonce 随机数，不小于10位
         * content 请求内容
         * sign 加签字符串
         */
        // 应用秘钥
        String appsecret = "082d5733fc44443e992071b2bc916620";

        /** 传参 **/
        HashMap<Object, Object> bodyMap = new HashMap<>(2);
        bodyMap.put("pageSize", 2);
        bodyMap.put("pageNum", 1);

        /** 参数排序 **/
        // 请求内容字典排序，转换为String
        String content = JSON.toJSONString(bodyMap, SerializerFeature.MapSortField, SerializerFeature.WriteMapNullValue);
        log.info("请求内容转换为JSON字符串，字典排序：{}", content);

        // 构造请求验签参数
        Map<String, Object> map = new TreeMap<>();
        map.put("appid", "104027978266054656");
        map.put("version", "1.0.0");
        map.put("signtype", "md5");
        map.put("timestamp", String.valueOf(System.currentTimeMillis()));
        map.put("nonce", RandomUtil.randomNumbers(10));
        map.put("content", content);

        /** 拼接应用密钥 生成sign，防篡改 **/
        String toSignStr = Joiner.on("&")
                .useForNull("")
                .withKeyValueSeparator("=")
                .join(map) + "&appsecret=" + appsecret;
        log.info("所有参数字典排序，待加签String：{}", toSignStr);

        /** 签名 **/
        // md5/sha256 加签（其他方式待提供）
        String sign = DigestUtils.md5Hex(toSignStr).toUpperCase();
        log.info("加签String：{}", sign);
        map.put("sign", sign);
        //post请求
        map.replace("content", bodyMap);

        HttpRequest request = HttpUtil.createPost("https://dev06.xnzn.net/tengyun-api/leopen/customer/query");
        log.info("request=" + JSONUtil.toJsonStr(map));
        request.body(JSONUtil.toJsonStr(map), ContentType.JSON.toString());
//        HttpResponse response = request.execute();
//        log.info(response.body());
    }

    /**
     * 组织查询
     */
    @Test
    public void testOrgQuery() {
        // 应用秘钥
        String appsecret = "082d5733fc44443e992071b2bc916620";

        /** 传参 **/
        HashMap<Object, Object> bodyMap = new HashMap<>(2);
        bodyMap.put("orgIdList", new String[]{});
        bodyMap.put("orgNumList", new String[]{});

        /** 参数排序 **/
        // 请求内容字典排序，转换为String
        String content = JSON.toJSONString(bodyMap, SerializerFeature.MapSortField, SerializerFeature.WriteMapNullValue);
        log.info("请求内容转换为JSON字符串，字典排序：{}", content);

        // 构造请求验签参数
        Map<String, Object> map = new TreeMap<>();
        map.put("appid", "104027978266054656");
        map.put("version", "1.0.0");
        map.put("signtype", "md5");
        map.put("timestamp", String.valueOf(System.currentTimeMillis()));
        map.put("nonce", RandomUtil.randomNumbers(10));
        map.put("content", content);

        /** 拼接应用密钥 生成sign，防篡改 **/
        String toSignStr = Joiner.on("&")
                .useForNull("")
                .withKeyValueSeparator("=")
                .join(map) + "&appsecret=" + appsecret;
        log.info("所有参数字典排序，待加签String：{}", toSignStr);

        /** 签名 **/
        // md5/sha256 加签（其他方式待提供）
        String sign = DigestUtils.md5Hex(toSignStr).toUpperCase();
        log.info("加签String：{}", sign);
        map.put("sign", sign);
        //post请求
        map.replace("content", bodyMap);

        HttpRequest request = HttpUtil.createPost("https://dev06.xnzn.net/tengyun-api/leopen/customer/org/query");
        log.info("request=" + JSONUtil.toJsonStr(map));
        request.body(JSONUtil.toJsonStr(map), ContentType.JSON.toString());
//        HttpResponse response = request.execute();
//        log.info(response.body());
    }
}
