package nohi.demo.dto.contquery;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;


/**
 * @author NOHI
 * @program: nohi-web
 * @description:
 * @create 2020-06-02 11:04
 **/
@Data
public class ContractQueryBody {
    @NotBlank(message = "合同编号不能为空")
    private String htbh;
}
