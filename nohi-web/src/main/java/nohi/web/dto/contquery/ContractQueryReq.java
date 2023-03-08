package nohi.web.dto.contquery;

import io.swagger.annotations.ApiModel;
import lombok.Data;

/**
 * @author NOHI
 * @program: nohi-web
 * @description:
 * @create 2020-06-02 11:04
 **/
@ApiModel( description = "合同查询请求体")
@Data
public class ContractQueryReq {
   private ContractQueryHead head;
   private ContractQueryBody body;
}
