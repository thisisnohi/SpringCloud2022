package nohi.ms.sysuser.feign;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import nohi.ms.sys.user.dto.userquery.UserDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author NOHI
 * 2022-07-21 12:32
 **/
@FeignClient(value = "SYS-USER", fallback = UserFeignFallback.class)
public interface UserFeignApi {
    @RequestMapping(value = "/user/lists", method = RequestMethod.GET)
    List<UserDTO> lists();

    /**
     *
     * @param sleep
     * @return
     */
    @RequestMapping(value = "/user/lists-sleep", method = RequestMethod.GET)
    List<UserDTO> listsSleep(@RequestParam("sleep") Integer sleep);
}
