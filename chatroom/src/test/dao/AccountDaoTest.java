package dao;

import eneity.User;
import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @Description:
 * @Author:Anan
 * @Date:2019/8/19
 */
public class AccountDaoTest {
    private AccountDao accountDao = new AccountDao();
    @Test
    public void useReg(){
        User user = new User();
        user.setUserName("啊哈");
        user.setPassword("111111");
        boolean b = accountDao.userReg(user);
        Assert.assertTrue(b);
    }

    @Test
    public void userLogin(){
        String userName = "啊哈";
        String password = "111111";
        User user = accountDao.userLogin(userName,password);
        Assert.assertNotNull(user);
    }
}