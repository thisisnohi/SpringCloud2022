package nohi.web.dto.res;


/**
 * Rsa
 *
 * @author NOHI
 * @date 2022/9/3 13:50
 **/
public class RsaRespVO {
    // 返回码
    private String retCode = "SUC";
    // 返回信息
    private String retMsg = "";
    // 耗时
    private long time;
    private String acctNo;
    private String acctName;
    // 加解密后值
    private String data;
    // 签名
    private String sign;

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

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
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

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }
}
