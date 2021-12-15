package com.ag.web;

import com.ag.pojo.User;
import com.ag.service.UserService;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet("/registerServlet")
public class registerServlet extends HttpServlet {
    private UserService userService=new UserService();
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        String checkCode = request.getParameter("checkCode");
        HttpSession session = request.getSession();
        String checkcode1 = (String) session.getAttribute("checkcode1");

        if(checkcode1.equals(checkCode)){
                //2. 调用service 注册
                boolean flag = userService.register(user);
                //3. 判断注册成功与否
                if(flag){
                    //注册功能，跳转登陆页面

                    request.setAttribute("register_msg","注册成功，请登录");
                    request.getRequestDispatcher("/login.jsp").forward(request,response);
                }else {
                    //注册失败，跳转到注册页面

                    request.setAttribute("register_msg","用户名已存在");
                    request.getRequestDispatcher("/register.jsp").forward(request,response);
                }
            }else{
                request.setAttribute("register_msg","验证码错误");
                request.getRequestDispatcher("/register.jsp").forward(request,response);
            }





    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doGet(request, response);
    }
}
