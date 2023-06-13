package nohi.web.dto.cont;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;



/**
 * @author NOHI
 * @program: nohi-web
 * @description:
 * @create 2020-06-02 10:29
 **/
@Data
public class ContractPartItem {
    @NotBlank(message =  "主体类型不能为空")
    private String rlx; // 主体类型
    @NotBlank(message =  "姓名不能为空")
    private String xm; // 姓名
    @NotBlank(message =  "证件名称不能为空")
    private String zjmc; // 证件名称
    @NotBlank(message =  "证件号码不能为空")
    private String zjhm; // 证件号码
    @NotBlank(message =  "联系电话不能为空")
    private String lxdh; // 联系电话
//    @NotBlank(message =  "收付款主体标识不能为空")
//    @ApiModelProperty(value = "收付款主体标识")
//    private String sffbs; // 收付款主体标识
//    @NotBlank(message =  "收款账号不能为空")
//    @ApiModelProperty(value = "收款账号")
//    private String skzh; // 收款账号
//    @NotBlank(message =  "账号户名不能为空")
//    @ApiModelProperty(value = "账号户名")
//    private String zhhm; // 账号户名
}
