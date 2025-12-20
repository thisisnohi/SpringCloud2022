package nohi.demo.common.utils;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.TimeZone;

/**
 * <h3>SpringBootNative</h3>
 *
 * @author NOHI
 * @description <p>JSONObject</p>
 * @date 2025/07/27 10:46
 **/
public class JSONObjectUtils {
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    static {
        /** 初始化配置 **/
        // 忽略null值
        OBJECT_MAPPER.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        // 忽略未知属性
        OBJECT_MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        // 空对象不报错
        OBJECT_MAPPER.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        // 日期不写为时间戳
        OBJECT_MAPPER.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        OBJECT_MAPPER.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
        OBJECT_MAPPER.setTimeZone(TimeZone.getTimeZone("GMT+8"));
        // 支持Java8时间类型
        OBJECT_MAPPER.registerModule(new JavaTimeModule());
    }

    /**
     * 对象转JSON字符串
     */
    public static String toJsonString(Object obj) {
        try {
            return OBJECT_MAPPER.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("对象转JSON字符串失败", e);
        }
    }

    /**
     * JSON字符串转对象
     */
    public static <T> T fromJson(String json, Class<T> clazz) {
        try {
            return OBJECT_MAPPER.readValue(json, clazz);
        } catch (IOException e) {
            throw new RuntimeException("JSON字符串转对象失败", e);
        }
    }

    /**
     * JSON字符串转复杂对象（如List、Map等）
     */
    public static <T> T fromJson(String json, TypeReference<T> typeReference) {
        try {
            return OBJECT_MAPPER.readValue(json, typeReference);
        } catch (IOException e) {
            throw new RuntimeException("JSON字符串转复杂对象失败", e);
        }
    }

    /**
     * 对象转字节数组
     */
    public static byte[] toBytes(Object obj) {
        try {
            return OBJECT_MAPPER.writeValueAsBytes(obj);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("对象转字节数组失败", e);
        }
    }

    /**
     * 字节数组转对象
     */
    public static <T> T fromBytes(byte[] bytes, Class<T> clazz) {
        try {
            return OBJECT_MAPPER.readValue(bytes, clazz);
        } catch (IOException e) {
            throw new RuntimeException("字节数组转对象失败", e);
        }
    }

    /**
     * 对象深度拷贝
     */
    public static <T> T deepCopy(T obj, Class<T> clazz) {
        return fromJson(toJsonString(obj), clazz);
    }

    /**
     * 格式化JSON字符串（美化输出）
     */
    public static String formatJson(String json) {
        try {
            Object obj = OBJECT_MAPPER.readValue(json, Object.class);
            return OBJECT_MAPPER.writerWithDefaultPrettyPrinter().writeValueAsString(obj);
        } catch (IOException e) {
            throw new RuntimeException("JSON格式化失败", e);
        }
    }

    /**
     * 获取ObjectMapper实例（用于特殊需求）
     */
    public static ObjectMapper getObjectMapper() {
        return OBJECT_MAPPER;
    }

}
