package nohi.web.dto.contquery;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author NOHI
 * @program: nohi-web
 * @description:
 * @create 2020-06-02 11:04
 **/
@ApiModel( description = "合同查询请求体")
@Data
public class ContractQueryHead {
    @ApiModelProperty( value = "合同编号")
    @JsonProperty("trans_seq_no")
    private String transSeqNo;

    @JsonProperty("request_date")
    private String requestDate;

    @JsonProperty("request_time")
    private String requestTime;

    @JsonProperty("system_id")
    private String systemId;
}
