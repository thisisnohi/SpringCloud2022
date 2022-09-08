package nohi.web.dto.contquery;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author NOHI
 * @program: nohi-web
 * @description:
 * @create 2020-06-02 11:04
 **/
@ApiModel( description = "合同查询请求体")
@Data
public class ContractQueryBody {
    @NotBlank(message = "合同编号不能为空")
    @ApiModelProperty( value = "合同编号")
    private String htbh;
}
