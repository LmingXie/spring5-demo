package com.lming;

import com.lming.bean.PayEntity;
import com.lming.bean.UserEntity;
import com.lming.config.MySpringConfig;
import com.lming.declareparents.Animal;
import com.lming.declareparents.Person;
import com.lming.service.MemberService;
import com.lming.service.UserService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class TestMain {
    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(MySpringConfig.class);
    
        System.out.println("------->>>>打印所有实例");
        String[] beanDefinitionNames = context.getBeanDefinitionNames();
        for (String name : beanDefinitionNames) {
            System.out.println(name);
    
        }
        UserEntity userEntity = context.getBean("userEntity", UserEntity.class);
        System.out.println("获取到实例：" + userEntity);

        UserService userService = context.getBean("userService", UserService.class);
        System.out.println("获取到通过扫描方式注入到IOC中的UserService实例！！");
        System.out.println("调用UserService的方法：" + userService.getUser());

        PayEntity payEntity = context.getBean("payEntity", PayEntity.class);
        System.out.println("获取手动注入的PayEntity Bean 实例：" + payEntity);

        System.out.println("--------------------@DeclareParents注解测试-------------------------");
        Person person = (Person) context.getBean("women");
        person.likePerson();
        Animal animal = (Animal) person;
        animal.eat();

        System.out.println("--------------------AOP测试-------------------------");
        MemberService memberService = context.getBean("memberServiceImpl", MemberService.class);
        memberService.login("zhangsan", "123456");
    }
    
}
