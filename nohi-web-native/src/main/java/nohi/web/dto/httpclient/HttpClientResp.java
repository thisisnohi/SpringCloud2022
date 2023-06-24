package nohi.web.dto.httpclient;

import lombok.Data;

/**
 * <h3>nohi-web</h3>
 *
 * @author NOHI
 * @description <p>httpclient响应</p>
 * @date 2022/09/16 18:32
 **/
@Data
public class HttpClientResp<T> {

    public static final String RET_SUC = "SUC";
    public static final String RET_FAIL = "FAIL";

    private String traceId;
    private String traceTime;
    private String txCode;
    private String retCode;
    private String retMsg;
    private T data;
}
