package nohi.boot.demo.service;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import nohi.boot.demo.dao.TbUserMapper;
import nohi.boot.demo.entity.TbUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <h3>SpringBootTest</h3>
 *
 * @author NOHI
 * @description <p></p>
 * @date 2023/01/16 21:35
 **/
@Service
@Slf4j
public class HelloService {
    @Autowired
    TbUserMapper userMapper;

    public String sayHello(String some) {
        return "Hello " + some;
    }

    @Transactional(value = Transactional.TxType.REQUIRES_NEW, rollbackOn = Exception.class)
    public int modifyUser(TbUser user) throws Exception {
        log.info("modifyUser:{}", user);
        if (null == user) {
            throw new Exception("回滚事务");
        }
        return userMapper.updateById(user);
    }
}
