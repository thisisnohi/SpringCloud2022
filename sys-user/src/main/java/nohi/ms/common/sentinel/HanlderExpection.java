package nohi.ms.common.sentinel;

import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import nohi.ms.sys.user.dto.userquery.UserDTO;

import java.util.List;

/**
 * @author NOHI
 * 2022-08-07 16:52
 **/
@Slf4j
public class HanlderExpection {
    /**
     *   返回类型和参数必须与原函数返回类型和参数一致
     */
    public static List<UserDTO> allHandlerExpection(BlockException exception) {
        log.warn("===========AllHandlerExpection==========");
        return Lists.newArrayList();
    }
}
