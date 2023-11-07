package nohi.boot.demo.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import nohi.boot.demo.dao.TbUserMapper;
import nohi.boot.demo.entity.TbUser;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

/**
 * <h3>SpringBootTest</h3>
 *
 * @author NOHI
 * @description <p>UserServiceImpl</p>
 * @date 2023/01/13 13:05
 **/
@Slf4j
@Service
public class TbUserService {

    @Autowired
    TbUserMapper userMapper;

    @Autowired
    HelloService helloService;

    /**
     * 查询全部
     *
     * @return 返回列表
     */
    public List<TbUser> queryAll() {
        return userMapper.selectList(null);
    }


    /**
     * selectByExample
     *
     * @return 返回列表
     */
    public List<TbUser> selectByExample(Map<String, Object> map) {
        return userMapper.selectByExample(map);
    }


    /**
     * 保存用户
     * ID存在则更新，不存在则新增
     *
     * @param user 入参
     * @return 返回
     */
    public TbUser saveUser(TbUser user) {
        /** 对象检查 **/
        if (null == user || StringUtils.isBlank(user.getName())) {
            log.warn("用户信息为空/用户姓名为空");
            throw new RuntimeException("用户信息不能为空/姓名不能为空");
        }

        /** 判断是否重复 **/
        Integer id = user.getId();
        TbUser old = null;
        if (null != id) {
            old = this.queryById(id);
        }

        if (old != null) {
            log.info("用户[{}]已存在，更新数据", id);
            user.setId(id);
        } else {
            log.info("新增用户[{}]", user.getName());
        }
        id = this.add(user);
        user.setId(id);
        return user;
    }

    /**
     * 添加一条数据
     *
     * @param user
     * @return
     */
    public int add(TbUser user) {
        return userMapper.insert(user);
    }

    /**
     * 添加多条数据
     *
     * @param users
     */
    public void add(List<TbUser> users) {
        for (TbUser user : users) {
            add(user);
        }
    }

    /**
     * 通过id查询
     *
     * @param user
     * @return
     */
    public TbUser queryById(TbUser user) {
        return this.queryById(user.getId());
    }

    public TbUser queryById(Integer id) {
        return userMapper.selectById(id);
    }

    /**
     * 通过姓名模糊查询
     *
     * @param name
     * @return
     */
    public List<TbUser> queryByName(String name) {
        QueryWrapper<TbUser> userQueryWrapper = new QueryWrapper<>();
        // 参数为表中的列名，要查询的条件 相当于 WHERE name LIKE  %name%
        userQueryWrapper.like("name", name);
        return userMapper.selectList(userQueryWrapper);
    }

    /**
     * 通过姓名精确查询
     */
    public List<TbUser> queryByName2(String name) {
        QueryWrapper<TbUser> userQueryWrapper = new QueryWrapper<>();
        // 参数为表中的列名，要查询的条件 相当于 WHERE name = name
        userQueryWrapper.eq("name", name);
        return userMapper.selectList(userQueryWrapper);
    }

    /**
     * 还可以使用map来实现相同的效果
     *
     * @param name
     * @return
     */
    public List<TbUser> queryByNameMap(String name) {
        Map<String, Object> map = new HashMap<>(20);
        map.put("name", name);
        return userMapper.selectByMap(map);
    }

    /**
     * 通过Id查询批量查询
     *
     * @return
     */
    public List<TbUser> queryByIds(Collection ids) {
        return userMapper.selectBatchIds(ids);
    }

    /**
     * 计数
     *
     * @return
     */
    public Long count() {
        QueryWrapper<TbUser> userQueryWrapper = new QueryWrapper<>();
        return userMapper.selectCount(userQueryWrapper);
    }


    /**
     * 根据条件更新
     *
     * @param user
     * @param column
     * @param val
     */
    public void changeBy(TbUser user, String column, Object val) {
        QueryWrapper<TbUser> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.eq(column, val);
        int num = userMapper.update(user, userQueryWrapper);
        System.out.println("影响行数：" + num);
    }

    /**
     * 通过ID修改信息
     *
     * @param user
     */
    public void changeUserById(TbUser user) {
        int num = userMapper.updateById(user);
        System.out.println("影响行数：" + num);
    }


    /**
     * 通过ID删除
     *
     * @param user
     * @return
     */
    public int deleteById(TbUser user) {
        return userMapper.deleteById(user.getId());
    }

    /**
     * 通过条件删除
     *
     * @param column
     * @param val
     */
    public void deleteBy(String column, Object val) {
        QueryWrapper<TbUser> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.eq(column, val);
        int num = userMapper.delete(userQueryWrapper);
        System.out.println("影响行数：" + num);
    }

    public void delete(Map<String, Object> map) {
        userMapper.deleteByMap(map);
    }


    /**
     * 通过id批量删除
     */
    public void deleteByIds() {
        List<Integer> idList = new ArrayList<>();
        idList.add(10);
        idList.add(11);
        int num = userMapper.deleteBatchIds(idList);
        System.out.println("影响行数：" + num);
    }

    /**
     * jakarta.transaction注解事务
     *
     * @param id
     * @return
     * @throws Exception
     */
    @Transactional(value = Transactional.TxType.REQUIRED, rollbackOn = Exception.class)
    public TbUser testTransaction(Integer id) throws Exception {
        log.info("测试事务 jakarta.transaction.Transactional");
        TbUser user = userMapper.selectById(id);
        log.info("TbUser:{}", user);

        /** 更新用户 **/
        user.setPwd(LocalDateTime.now().toString());
        user.setEmail("222");
        helloService.modifyUser(user);

        user.setEmail("11111");
        userMapper.updateById(user);
        if ("1".equals("1")) {
            throw new Exception("回滚事务");
        }
        log.info("测试事务，结束");
        return userMapper.selectById(id);
    }

    /**
     * Spring注解事务
     *
     * @param id
     * @return
     * @throws Exception
     */
    @org.springframework.transaction.annotation.Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public TbUser testTransaction2(Integer id) throws Exception {
        log.info("测试事务 org.springframework.transaction.annotation.Transactional");
        TbUser user = userMapper.selectById(id);
        log.info("TbUser:{}", user);

        /** 更新用户 **/
        user.setPwd(LocalDateTime.now().toString());
        user.setEmail("第一次提交");
        helloService.modifyUser(user);
        log.info("第一次提交结束...");
        user.setEmail("第二次修改");
        userMapper.updateById(user);
        log.info("第二次修改结束...");
        if ("1".equals("1")) {
            log.warn("准备回滚事务...");
            throw new Exception("回滚事务");
        }
        log.info("测试事务，结束");
        return userMapper.selectById(id);
    }


    /**
     *  测试默认事务
     */
    public TbUser testTransactionDefault(Integer id) throws Exception {
        log.info("测试默认事务 org.springframework.transaction.annotation.Transactional");
        TbUser user = userMapper.selectById(id);
        log.info("TbUser:{}", user);

        /** 更新用户 **/
        user.setPwd(LocalDateTime.now().toString());
        user.setEmail("第一次提交");
        helloService.modifyUserDefaultTC(user);
        log.info("第一次提交结束...");

        user.setEmail("第二次修改");
        helloService.modifyUserDefaultTC(user);
        log.info("第二次修改结束...");
        if ("1".equals("1")) {
            log.warn("准备回滚事务...");
            throw new Exception("回滚事务");
        }
        log.info("测试事务，结束");
        return userMapper.selectById(id);
    }
}
