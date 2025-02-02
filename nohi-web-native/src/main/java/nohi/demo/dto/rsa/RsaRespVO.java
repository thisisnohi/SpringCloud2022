package nohi.demo.dto.rsa;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * Rsa
 *
 * @author NOHI
 * @date 2022/9/3 13:50
 **/
@SuppressWarnings("AlibabaCommentsMustBeJavadocFormat")
@Schema(title = "Rsa响应体")
@Data
public class RsaRespVO {
    // 返回码
    @SuppressWarnings("AlibabaPojoNoDefaultValue")
    private String retCode = "SUC";
    // 返回信息
    @SuppressWarnings("AlibabaPojoNoDefaultValue")
    private String retMsg = "";
    // 耗时
    private Long time;
    private String acctNo;
    private String acctName;
    // 加解密后值
    private String data;
    // 签名
    private String sign;
}
