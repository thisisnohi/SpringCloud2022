package nohi.ms.sys.user.dto.userquery;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * @author NOHI
 * 2022-07-11 13:48
 **/
@Data
public class UserQueryReq {
    @NotBlank(message = "abc不能为空")
    @NotNull(message = "abc不能为空")
    private String userId;
    private String userNo;
    private String userName;
}
