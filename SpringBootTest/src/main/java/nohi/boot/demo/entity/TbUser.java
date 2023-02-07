package nohi.boot.demo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

/**
 * <h3>SpringBootTest</h3>
 *
 * @author NOHI
 * @description <p>用户类</p>
 * @date 2023/01/13 13:01
 **/
@Data
@Builder
@TableName(value = "T_USER")
public class TbUser implements Serializable {

    private static final long serialVersionUID = -5644799954031156649L;
    /**
     * value与数据库主键列名一致，若实体类属性名与表主键列名一致可省略value
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    private String name;
    private String sex;
    private String pwd;
    private String email;
}
