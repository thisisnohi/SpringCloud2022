package nohi.boot.demo.dao;


import nohi.boot.demo.entity.TbUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * <h3>SpringBootTest</h3>
 *
 * @author NOHI
 * @description <p>JPA</p>
 * @date 2024/09/11 10:53
 **/
@Repository
public interface TbUserRepository extends JpaRepository<TbUser, Integer>, JpaSpecificationExecutor<TbUser> {

}
