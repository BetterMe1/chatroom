package dao;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidDataSourceFactory;
import myUtil.CommUtils;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

/**
 * @Description:dao层基础类，封装数据源、获取连接、关闭资源等公共操作
 * @Author:Anan
 * @Date:2019/8/16
 */
public class BasedDao {
    private static DruidDataSource DATASOURCE;
    //加载数据源
    static{
        Properties properties = CommUtils.loadProperties("db.properties");
        try {
            DATASOURCE = (DruidDataSource) DruidDataSourceFactory.createDataSource(properties);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //获取连接
    protected  Connection getConnection(){
        try {
            return (Connection) DATASOURCE.getPooledConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    //关闭资源
    protected void closeResources(Connection connection, Statement statement){
        if(connection != null){
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if(statement != null){
            try {
                statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    protected void closeResources(Connection connection, Statement statement, ResultSet resultSet){
        closeResources(connection,statement);
        if(resultSet!=null){
            try {
                resultSet.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
