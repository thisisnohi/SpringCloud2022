package nohi.boot.demo.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import nohi.boot.demo.entity.TbUser;
import org.apache.ibatis.annotations.Mapper;

/**
 * <h3>SpringBootTest</h3>
 * 表明这是一个Mapper，也可以在启动类上加上包扫描
 * Mapper 继承该接口后，无需编写 mapper.xml 文件，即可获得CRUD功能
 *
 * @author NOHI
 * @description <p>UserMapper</p>
 * @date 2023/01/13 13:03
 **/
@Mapper
public interface TbUserMapper extends BaseMapper<TbUser> {

}