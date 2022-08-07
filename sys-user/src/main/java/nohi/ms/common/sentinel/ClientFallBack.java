package nohi.ms.common.sentinel;

import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import nohi.ms.sys.user.dto.userquery.UserDTO;

import java.util.List;

/**
 * @author NOHI
 * 2022-08-07 16:52
 **/
@Slf4j
public class ClientFallBack {
    public static List<UserDTO> fallBack() {
        log.warn("fallBack");
        return Lists.newArrayList();
    }
}
