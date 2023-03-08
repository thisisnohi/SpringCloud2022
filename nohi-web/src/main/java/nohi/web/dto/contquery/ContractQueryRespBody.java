package nohi.web.dto.contquery;

import io.swagger.annotations.ApiModel;
import lombok.Data;

/**
 * @author NOHI
 * @program: nohi-web
 * @description:
 * @create 2020-06-02 11:04
 **/
@Data
@ApiModel( description = "合同查询响应体")
public class ContractQueryRespBody {
    private ContractQueryResult result;
    private String jszt;
    private String ztxx;

}
