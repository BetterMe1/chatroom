package myUtil;

import eneity.User;
import org.junit.Assert;
import org.junit.Test;

import java.util.Properties;

import static org.junit.Assert.*;

/**
 * @Description:单元测试：CommUtils
 * @Author:Anan
 * @Date:2019/8/19
 */
public class CommUtilsTest {
    @Test
    public void loadProperties(){
        Properties properties = CommUtils.loadProperties("db.properties");
        Assert.assertNotNull(properties);
    }
    @Test
    public void objectToJson(){
        User user = new User();
        user.setUserName("111");
        user.setPassword("222");
        String jsonStr = CommUtils.objectToJson(user);
        System.out.println(jsonStr);
    }
    @Test
    public void jsonToObject(){
        String jsonStr = "{\"userName\":\"111\",\"password\":\"222\"}";
        User user = (User) CommUtils.jsonToObject(jsonStr,User.class);
        System.out.println(user);
    }
}