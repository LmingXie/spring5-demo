package com.lming.service.impl;

import com.lming.service.MemberService;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

@Component
public class MemberServiceImpl   implements  InitializingBean, MemberService {
    public MemberServiceImpl() {
        System.out.println("1无参构造函数....");
    }

    public String login(String userName, String passWord) {
        int i = 1 / 0;
        System.out.println(">>>>正在执行登陆业务逻辑>>>>> userName:" + userName + "passWord:" + passWord);
        return ">>>>登陆业务逻辑..<<<<";
    }

    public void afterPropertiesSet() throws Exception {
        System.out.println("2执行自定义bean的init方法");
    }
}
