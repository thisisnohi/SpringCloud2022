package nohi.web.dto.cont;

import lombok.Data;

/**
 * @author NOHI
 * @program: nohi-web
 * @description:
 * @create 2020-06-02 10:29
 **/
@Data
public class ContractDataResp {
    private String qyxh; // 签约序号
    private String fwxh; // 姓名
    private String jszt;
    private String msg;
}
