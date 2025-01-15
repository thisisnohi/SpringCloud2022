package nohi.web.dto.res;

/**
 * RsaReqVo
 *
 * @author NOHI
 * @date 2022/9/3 13:50
 **/
public class RsaReqVo {
    private String traceId;
    private String acctNo;
    private String acctName;

    public String getTraceId() {
        return traceId;
    }

    public void setTraceId(String traceId) {
        this.traceId = traceId;
    }

    public String getAcctNo() {
        return acctNo;
    }

    public void setAcctNo(String acctNo) {
        this.acctNo = acctNo;
    }

    public String getAcctName() {
        return acctName;
    }

    public void setAcctName(String acctName) {
        this.acctName = acctName;
    }
}
