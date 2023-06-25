package nohi.web.dto.httpclient;


/**
 * <h3>nohi-web</h3>
 *
 * @author NOHI
 * @description <p>httpclient响应</p>
 * @date 2022/09/16 18:32
 **/
public class HttpClientResp<T> {

    public static final String RET_SUC = "SUC";
    public static final String RET_FAIL = "FAIL";

    private String traceId;
    private String traceTime;
    private String txCode;
    private String retCode;
    private String retMsg;
    private T data;

    public String getTraceId() {
        return traceId;
    }

    public void setTraceId(String traceId) {
        this.traceId = traceId;
    }

    public String getTraceTime() {
        return traceTime;
    }

    public void setTraceTime(String traceTime) {
        this.traceTime = traceTime;
    }

    public String getTxCode() {
        return txCode;
    }

    public void setTxCode(String txCode) {
        this.txCode = txCode;
    }

    public String getRetCode() {
        return retCode;
    }

    public void setRetCode(String retCode) {
        this.retCode = retCode;
    }

    public String getRetMsg() {
        return retMsg;
    }

    public void setRetMsg(String retMsg) {
        this.retMsg = retMsg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
