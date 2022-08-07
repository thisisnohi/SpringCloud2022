package nohi.ms.sysuser.feign;

import lombok.extern.slf4j.Slf4j;
import nohi.ms.sys.user.dto.userquery.UserDTO;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * UserFeign异常处理
 *
 * @author NOHI
 * 2022-08-06 21:30
 **/
@Component
@Slf4j
public class UserFeignFallback implements UserFeignApi{
    @Override
    public List<UserDTO> lists() {
        log.info("UserFeignFallback.lists");
        return new ArrayList<>();
    }

    @Override
    public List<UserDTO> listsSleep(Integer sleep) {
        log.info("UserFeignFallback.listsSleep");
        return this.lists();
    }
}
