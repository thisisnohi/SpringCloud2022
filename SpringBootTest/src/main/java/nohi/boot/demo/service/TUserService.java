package nohi.boot.demo.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import nohi.boot.demo.dao.TUserMapper;
import nohi.boot.demo.entity.TUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <h3>SpringBootTest</h3>
 *
 * @author NOHI
 * @description <p>UserServiceImpl</p>
 * @date 2023/01/13 13:05
 **/
@Service
public class TUserService {

    @Autowired
    TUserMapper userMapper;

    /**
     * 查询全部
     *
     * @return
     */
    public List<TUser> queryAll() {
        return userMapper.selectList(null);
    }

    /**
     * 添加一条数据
     *
     * @param user
     * @return
     */
    public int add(TUser user) {
        return userMapper.insert(user);
    }

    /**
     * 添加多条数据
     *
     * @param users
     */
    public void add(List<TUser> users) {
        for (TUser user : users) {
            add(user);
        }
    }

    /**
     * 通过id查询
     *
     * @param user
     * @return
     */
    public TUser queryById(TUser user) {
        return userMapper.selectById(user.getId());
    }

    /**
     * 通过姓名模糊查询
     *
     * @param name
     * @return
     */
    public List<TUser> queryByName(String name) {
        QueryWrapper<TUser> userQueryWrapper = new QueryWrapper<>();
        // 参数为表中的列名，要查询的条件 相当于 WHERE name LIKE  %name%
        userQueryWrapper.like("name", name);
        return userMapper.selectList(userQueryWrapper);
    }

    /**
     * 通过姓名精确查询
     */
    public List<TUser> queryByName2(String name) {
        QueryWrapper<TUser> userQueryWrapper = new QueryWrapper<>();
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
    public List<TUser> queryByNameMap(String name) {
        Map<String, Object> map = new HashMap<>();
        map.put("name", name);
        return userMapper.selectByMap(map);
    }

    /**
     * 通过Id查询批量查询
     *
     * @return
     */
    public List<TUser> queryByIds() {
        List<Integer> idList = new ArrayList<>();
        idList.add(10);
        idList.add(11);
        return userMapper.selectBatchIds(idList);
    }

    /**
     * 计数
     *
     * @return
     */
    public Long count() {
        QueryWrapper<TUser> userQueryWrapper = new QueryWrapper<>();
        return userMapper.selectCount(userQueryWrapper);
    }


    /**
     * 根据条件更新
     *
     * @param user
     * @param column
     * @param val
     */
    public void changeBy(TUser user, String column, Object val) {
        QueryWrapper<TUser> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.eq(column, val);
        int num = userMapper.update(user, userQueryWrapper);
        System.out.println("影响行数：" + num);
    }

    /**
     * 通过ID修改信息
     *
     * @param user
     */
    public void changeUserById(TUser user) {
        int num = userMapper.updateById(user);
        System.out.println("影响行数：" + num);
    }


    /**
     * 通过ID删除
     *
     * @param user
     * @return
     */
    public int deleteById(TUser user) {
        return userMapper.deleteById(user.getId());
    }

    /**
     * 通过条件删除
     *
     * @param column
     * @param val
     */
    public void deleteBy(String column, Object val) {
        QueryWrapper<TUser> userQueryWrapper = new QueryWrapper<>();
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
}
