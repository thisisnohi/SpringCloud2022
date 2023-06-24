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
public class ContractQueryRespHead {
    @JsonProperty("trans_seq_no")
    private String transSeqNo;

    @JsonProperty("response_date")
    private String responseDate;

    @JsonProperty("response_time")
    private String responseTime;

    @JsonProperty("system_id")
    private String systemId;

    @JsonProperty("response_code")
    private String responseCode;

    @JsonProperty("error_msg")
    private String errorMsg;

    @JsonProperty("sha1")
    private String sha1;
}
