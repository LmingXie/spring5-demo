package com.lming.service;

import com.lming.bean.UserEntity;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

/**
 * @description: 用户业务PAI
 * @program: TestSpringboot
 * @author: Lming
 * @create: 2020-05-21 10:31
 **/
@Service
@Lazy
//@Conditional(MyCondition.class)
public class UserService {
    
    public UserService() {
        System.out.println("UserService无参构造方法正在执行……");
    }
    
    public UserEntity getUser() {
        return new UserEntity(2, "李四");
    }
    
    public void login(String userName, String age) {
        System.out.println("userName:" + userName + ",age:" + age);
    }
}
