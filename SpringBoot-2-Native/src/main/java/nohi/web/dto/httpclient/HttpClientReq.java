package nohi.web.dto.httpclient;


/**
 * <h3>nohi-web</h3>
 *
 * @author NOHI
 * @description <p>httpclient请求</p>
 * @date 2022/09/16 18:32
 **/
public class HttpClientReq<T> {
    private String traceId;
    private String traceTime;
    private String txCode;
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

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
