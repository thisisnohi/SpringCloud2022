package nohi.demo.dto.cont;

import lombok.Data;

/**
 * @author NOHI
 * @program: nohi-web
 * @description:
 * @create 2020-06-02 10:29
 **/
@Data
public class ContractDataResp {
    // 签约序号
    private String qyxh;
    // 姓名
    private String fwxh;
    private String jszt;
    private String msg;
}
