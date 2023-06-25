package nohi.demo.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author NOHI
 * @program: nohi-web
 * @description:
 * @create 2020-06-02 12:42
 **/
public class JsonUtils {
    static ObjectMapper objectMapper = new ObjectMapper();

    public static String toString(Object obj){
        try {
//            return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(obj);
            return objectMapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }
}
