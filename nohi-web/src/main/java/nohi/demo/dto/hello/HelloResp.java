package nohi.demo.dto.hello;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * <h3>nohi-web-native</h3>
 *
 * @author NOHI
 * @description <p>HelloReq</p>
 * @date 2023/06/26 13:00
 **/
@Data
@Schema(name = "打招呼响应对象")
public class HelloResp {
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, title = "响应码", description = "返回响应码：SUC-成功，其他失败", example = "SUC")
    private String retCode = "SUC";
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, title = "响应信息", description = "返回响应信息", example = "正常返回")
    private String retMsg;
}
