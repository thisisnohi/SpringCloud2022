package nohi.ms.sys.user.dto.userquery;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 用户查询
 *
 * @author NOHI
 * 2022-07-11 13:16
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private String userId;
    private String userNo;
    private String userName;
    private Integer age;
    private String address;
}
