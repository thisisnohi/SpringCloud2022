package nohi.demo.dto.rsa;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * RsaReqVo
 *
 * @author NOHI
 * @date 2022/9/3 13:50
 **/
@Data
@Schema(title = "Rsa请求体")
public class RsaReqVo {
    @Schema(title = "流水号2", description = "流水号3", example = "20230618000011110001")
    private String traceId;
    @Schema(title = "账号", example = "6225888811112222")
    private String acctNo;
    @Schema(title = "账户名", example = "测试账号")
    private String acctName;
}
