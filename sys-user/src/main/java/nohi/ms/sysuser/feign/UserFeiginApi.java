package nohi.ms.sysuser.feign;

import nohi.ms.sys.user.dto.userquery.UserDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

/**
 * @author NOHI
 * 2022-07-21 12:32
 **/
@FeignClient("SYS-USER")
public interface UserFeiginApi {
    @RequestMapping(value = "/users/lists", method = RequestMethod.GET)
    public List<UserDTO> lists();
}
