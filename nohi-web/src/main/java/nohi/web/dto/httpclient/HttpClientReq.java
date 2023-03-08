package nohi.web.dto.httpclient;

import lombok.Data;

/**
 * <h3>nohi-web</h3>
 *
 * @author NOHI
 * @description <p>httpclient请求</p>
 * @date 2022/09/16 18:32
 **/
@Data
public class HttpClientReq<T> {
    private String traceId;
    private String traceTime;
    private String txCode;
    private T data;
}
