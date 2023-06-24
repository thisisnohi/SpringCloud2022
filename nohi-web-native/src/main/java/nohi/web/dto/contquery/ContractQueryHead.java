package nohi.web.dto.contquery;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * @author NOHI
 * @program: nohi-web
 * @description:
 * @create 2020-06-02 11:04
 **/
@Data
public class ContractQueryHead {
    @JsonProperty("trans_seq_no")
    private String transSeqNo;

    @JsonProperty("request_date")
    private String requestDate;

    @JsonProperty("request_time")
    private String requestTime;

    @JsonProperty("system_id")
    private String systemId;
}
