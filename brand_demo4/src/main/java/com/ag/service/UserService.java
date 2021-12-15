package com.ag.service;
import com.ag.mapper.UserMapper4;
import com.ag.pojo.User;
import com.ag.util.SqlSessionFactoryUtils;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
/*
*
*
* 登录方法
*
*
*
* */
public class UserService {
    SqlSessionFactory sqlSessionFactory = SqlSessionFactoryUtils.getSqlSessionFactory();
    public User select(String username, String password) {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        UserMapper4 mapper = sqlSession.getMapper(UserMapper4.class);
        User user = mapper.select(username, password);
        sqlSession.close();
        return user;


    }

    /**
     * 注册方法
     * @return
     */

    public boolean register(User user){
        //2. 获取SqlSession
        SqlSession sqlSession = sqlSessionFactory.openSession();
        UserMapper4 mapper = sqlSession.getMapper(UserMapper4.class);

        //4. 判断用户名是否存在
        User u = mapper.selectByUsername(user.getUsername());

        if(u == null){
            // 用户名不存在，注册
            mapper.add(user);
            sqlSession.commit();
        }
        sqlSession.close();

        return u == null;

    }


}