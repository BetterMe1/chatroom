package controller;

import service.AccountService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Description:
 * @Author:Anan
 * @Date:2019/8/19
 */
@WebServlet(urlPatterns = "/doRegister")
public class AccountController extends HttpServlet {
    private AccountService accountService = new AccountService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String userName = req.getParameter("username");
        String password = req.getParameter("password");
        resp.setContentType("text/html;charset=utf8");
        if(accountService.userRegister(userName,password)){
            resp.getWriter().write("<script>\n" +
                    "    alert(\"注册成功\");\n" +
                    "    window.location.href = \"/index.html\";\n" +
                    "</script>");
        }else{
            resp.getWriter().write("<script>\n" +
                    "    alert(\"注册失败\");\n" +
                    "    window.location.href = \"/registration.html\";\n" +
                    "</script>");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req,resp);
    }
}
