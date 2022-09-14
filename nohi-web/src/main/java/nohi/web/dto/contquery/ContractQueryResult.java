package nohi.web.dto.contquery;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import nohi.web.dto.cont.ContractDataReq;

/**
 * 合同查询响应体
 * @author NOHI
 * @date 2022/9/14 15:10
 */
@Data
@ApiModel(description = "合同查询响应体")
public class ContractQueryResult extends ContractDataReq {
    /**
     * 当前是否有效
     */
    @ApiModelProperty(value = "合同状态")
    private String htsfyx;

}
