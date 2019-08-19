package dao;

import eneity.User;
import org.apache.commons.codec.digest.DigestUtils;

import java.sql.*;

/**
 * @Description:用户模块的dao层
 * @Author:Anan
 * @Date:2019/8/19
 */
public class AccountDao extends BasedDao{
    /**
     *用户登录
     * @param userName
     * @param password
     * @return
     */
    public User userLogin(String userName, String password){
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = getConnection();
            String sql = "SELECT * FROM user WHERE userName=? AND password=?";
            statement = connection.prepareStatement(sql);
            statement.setString(1,userName);
            statement.setString(2, DigestUtils.md5Hex(password));
            resultSet = statement.executeQuery();
            while (resultSet.next()){
                return getUserInfo(resultSet);
            }
        } catch (SQLException e) {
            System.out.println("登录失败");
            e.printStackTrace();
        }finally {
            closeResources(connection,statement,resultSet);
        }
        return null;
    }

    /**
     * 用户注册
     * @param user
     */
    public boolean userReg(User user){
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = getConnection();
            String sql = "INSERT INTO user(userName,password) VALUES (?,?)";
            statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1,user.getUserName());
            statement.setString(2, DigestUtils.md5Hex(user.getPassword()));
            int rows = statement.executeUpdate();
            if(rows == 1){
                return true;
            }
        } catch (SQLException e) {
            System.out.println("注册用户失败");
            e.printStackTrace();
        }finally {
            closeResources(connection,statement);
        }
        return false;
    }
    public User getUserInfo(ResultSet resultSet) throws SQLException{
        User user = new User();
        user.setId(resultSet.getInt("id"));
        user.setUserName(resultSet.getString("userName"));
        user.setPassword(resultSet.getString("password"));
        return user;
    }
}
