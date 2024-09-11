package nohi.boot.demo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

/**
 * <h3>SpringBootTest</h3>
 *
 * @author NOHI
 * @description <p>用户类</p>
 * @date 2023/01/13 13:01
 **/
@Builder
@TableName(value = "T_USER")
@Table(name = "T_USER") // 表名称
@Entity
@Data
@AllArgsConstructor
public class TbUser implements Serializable {

    private static final long serialVersionUID = -5644799954031156649L;
    /**
     * value与数据库主键列名一致，若实体类属性名与表主键列名一致可省略value
     */
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    private String name;
    private String sex;
    private String pwd;
    private String email;

    public TbUser() {

    }
}
