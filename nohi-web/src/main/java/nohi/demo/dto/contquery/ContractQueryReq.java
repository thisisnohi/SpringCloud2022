package nohi.demo.dto.contquery;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Data;

/**
 * @author NOHI
 * @program: nohi-web
 * @description:
 * @create 2020-06-02 11:04
 **/
@Tag( name = "合同查询请求体")
@Data
public class ContractQueryReq {
   private ContractQueryHead head;
   private ContractQueryBody body;
}