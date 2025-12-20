package nohi.demo.dto.cont;

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
    @NotBlank(message = "主体类型不能为空")
    private String rlx;
    @NotBlank(message = "姓名不能为空")
    private String xm;
    @NotBlank(message = "证件名称不能为空")
    private String zjmc;
    @NotBlank(message = "证件号码不能为空")
    private String zjhm;
    @NotBlank(message = "联系电话不能为空")
    private String lxdh;
}
