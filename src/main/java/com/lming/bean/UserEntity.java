package com.lming.bean;

import org.springframework.context.annotation.Configuration;

/**
 * @description: 用户实例
 * @program: TestSpringboot
 * @author: Lming
 * @create: 2020-05-21 10:17
 **/
public class UserEntity {
    private Integer userId;
    private String userName;
    
    public UserEntity(Integer userId, String userName) {
        this.userId = userId;
        this.userName = userName;
    }
    
    public void setUserId(Integer userId) {
        this.userId = userId;
    }
    
    public void setUserName(String userName) {
        this.userName = userName;
    }
    
    @Override
    public String toString() {
        return "UserEntity{" +
                "userId=" + userId +
                ", userName='" + userName + '\'' +
                '}';
    }
}
