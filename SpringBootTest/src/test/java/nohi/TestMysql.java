package nohi;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * <h3>SpringCloud2022</h3>
 *
 * @author NOHI
 * @description <p></p>
 * @date 2024/06/11 10:50
 **/
public class TestMysql {

    public static void main(String[] args) throws Exception {
        Class.forName("com.mysql.jdbc.Driver");
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/test", "root", "root");
        con.setAutoCommit(false);
        PreparedStatement stmt = con.prepareStatement("insert into emp(name,hire date,dept id) values(?,?,?)");
        stmt.executeUpdate("insert into dept(name,location) value('R&D','Beijing')");
        //获取刚刚产生的主键<填入代码>
        //()
        ResultSet rs = stmt.getGeneratedKeys();
        stmt.close();
        con.close();

    }


    public List getInfo() throws Exception {
        Class.forName("com.mysql.jdbc.Driver");
        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/test", "root", "root");
        conn.setAutoCommit(false);

        List list = new ArrayList();
        String sql = "select * from table1";
        PreparedStatement pst = conn.prepareStatement(sql);
        ResultSet rs = pst.executeQuery();
        if (rs.next()) {
            list.add(rs.getString("name"));
            return list;
        }

        return list;
    }
}
