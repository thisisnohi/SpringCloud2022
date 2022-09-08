package nohi.web.dto.contquery;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import nohi.web.dto.cont.ContractDataReq;

/**
 * @author NOHI
 * @program: nohi-web
 * @description:
 * @create 2020-06-02 11:04
 **/
@Data
@ApiModel( description = "合同查询响应体")
public class ContractQueryResult extends ContractDataReq {
    @ApiModelProperty( value = "合同状态")
    private String htsfyx; // 当前是否有效

}
