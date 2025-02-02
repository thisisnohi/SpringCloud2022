package nohi.demo.dto.rsa;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author NOHI
 * @date 2022/9/3 13:51
 **/
@Data
public class RsaRespItemVO {
    private String acctNo;
    private String acctName;
    private String dateTime;
    private BigDecimal amt;
    private BigDecimal balance;

}
