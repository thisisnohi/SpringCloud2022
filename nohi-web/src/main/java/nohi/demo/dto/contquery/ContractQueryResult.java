package nohi.demo.dto.contquery;

import lombok.Data;
import nohi.demo.dto.cont.ContractDataReq;

/**
 * 合同查询响应体
 * @author NOHI
 * @date 2022/9/14 15:10
 */
@Data
public class ContractQueryResult extends ContractDataReq {
    /**
     * 当前是否有效
     */
    private String htsfyx;

}
