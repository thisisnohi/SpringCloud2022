package nohi.web.dto.cont;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author NOHI
 * @program: nohi-web
 * @description:
 * @create 2020-06-02 10:29
 **/
@Data
@ApiModel( description = "合同返回")
public class ContractDataResp {
    @ApiModelProperty(value = "签约序号")
    private String qyxh; // 签约序号
    @ApiModelProperty(value = "房屋序号")
    private String fwxh; // 姓名
    @ApiModelProperty(value = "接收状态")
    private String jszt;
    @ApiModelProperty(value = "接收状态")
    private String msg;
}
