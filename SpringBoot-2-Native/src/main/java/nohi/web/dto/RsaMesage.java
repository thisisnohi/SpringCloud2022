package nohi.web.dto;

/**
 * @author NOHI
 * @program: nohi-web
 * @description:
 * @create 2020-06-29 17:06
 **/
public class RsaMesage {
    private String data;
    private String sign;

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
