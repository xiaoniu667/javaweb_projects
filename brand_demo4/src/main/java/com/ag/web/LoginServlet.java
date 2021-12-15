package com.ag.web;

import com.ag.pojo.User;
import com.ag.service.UserService;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
  private  UserService userService=new UserService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
                        request.setCharacterEncoding("utf-8");
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String remember = request.getParameter("remember");
        User user = userService.select(username, password);
               if(user!=null){
                   /*登录成功*/
                   if("1".equals(remember)){
                       Cookie cookie1 = new Cookie("username", username);
                       Cookie cookie2 = new Cookie("password", password);
                       cookie1.setMaxAge(60*60*24*7);
                       cookie2.setMaxAge(60*60*24*7);
                       response.addCookie(cookie1);
                       response.addCookie(cookie2);

                   }
                   HttpSession session = request.getSession();
                   session.setAttribute("user",user);
                   request.getRequestDispatcher("/brand.jsp").forward(request,response);

               }else{
                   /*登陆失败*/
                   request.setAttribute("login_msg","用户名或密码不正确");
                   request.getRequestDispatcher("/login.jsp").forward(request,response);
               }

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doGet(request, response);
    }
}
