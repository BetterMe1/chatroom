package service;

import dao.AccountDao;
import eneity.User;

/**
 * @Description:用户Service层
 * @Author:Anan
 * @Date:2019/8/19
 */
public class AccountService {
    private AccountDao accountDao = new AccountDao();

    //用户登录
    public boolean userLogin(String userName, String password){
        User user = accountDao.userLogin(userName,password);
        if(user != null){
            return true;
        }
        return false;
    }
    //用户注册
    public boolean userRegister(String userName,String password){
        User user = new User();
        user.setUserName(userName);
        user.setPassword(password);
        return accountDao.userReg(user);
    }
}
