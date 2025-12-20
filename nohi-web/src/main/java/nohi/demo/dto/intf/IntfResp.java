package nohi.demo.dto.intf;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.util.Date;

/**
 * <h3>nohi-web-native</h3>
 *
 * @author NOHI
 * @description <p>响应对象</p>
 * @date 2023/06/26 14:07
 **/
@Schema(name = "响应对象", description = "响应对象,body为具体业务字段")
@Data
public class IntfResp<T> {
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, title = "流水号", description = "流水号，全局唯一", example = "20230618000011110001")
    private String traceId;
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, title = "交易码", description = "交易码：指明具体交易", example = "TX_001")
    private String txCode;
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, title = "请求交易时间", description = "请求交易时间", format = "yyyyMMdd HH:mm:ss", example = "yyyyMMdd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date reqTime;
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, title = "响应交易时间", description = "响应交易时间", format = "yyyyMMdd HH:mm:ss", example = "yyyyMMdd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date respTime;

    @Schema(title = "业务体", description = "业务体")
    private T body;

    /**
     * 根据返回对象
     *
     * @return IntfResp
     */
    public static IntfResp<Object> buildResp(IntfReq req) {
        IntfResp<Object> resp = new IntfResp<Object>();
        BeanUtils.copyProperties(req, resp);
        return resp;
    }

    /**
     * 更新响应时间
     */
    public void initRespTime() {
        this.respTime = new Date();
    }
}
