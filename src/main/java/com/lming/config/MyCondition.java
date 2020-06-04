package com.lming.config;

import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.env.Environment;
import org.springframework.core.type.AnnotatedTypeMetadata;

/**
 * @description: 自定义Bean装载过滤规则
 * @program: TestSpringboot
 * @author: Lming
 * @create: 2020-05-21 11:06
 **/
public class MyCondition implements Condition {
    @Override
    public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
        Environment environment = context.getEnvironment();
        String osName = environment.getProperty("os.name");
        
        System.out.println("当前环境是 " + osName + " 系统");
        if ("Windows 7".equals(osName)) {
            return true;
        }
        System.out.println("非Windows 7 系统，不加载当前实例Bean");
        return false;
    }
}
