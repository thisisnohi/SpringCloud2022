package nohi.web.dto.contquery;

import lombok.Data;

/**
 * @author NOHI
 * @program: nohi-web
 * @description:
 * @create 2020-06-02 11:04
 **/
@Data
public class ContractQueryResp {
    private ContractQueryRespBody body;
    private ContractQueryRespHead head;
}
