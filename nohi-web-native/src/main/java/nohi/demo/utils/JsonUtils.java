package nohi.demo.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;

/**
 * @author NOHI
 * @program: nohi-web
 * @description:
 * @create 2020-06-02 12:42
 **/
@Slf4j
public class JsonUtils {
    static ObjectMapper objectMapper = new ObjectMapper();

    public static String toString(Object obj) {
        if (null == obj) {
            return "";
        }
        try {
            return objectMapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            log.error("转JSON失败:{}", e.getMessage(), e);
        }
        return obj.toString();
    }
}
