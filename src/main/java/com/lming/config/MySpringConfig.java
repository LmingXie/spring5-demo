package com.lming.config;

import com.lming.aspect.MyBeanFactoryPostProcessor;
import com.lming.bean.MyImportBeanDefinitionRegistrar;
import com.lming.bean.UserEntity;
import org.springframework.context.annotation.*;

/**
 * @description: SpringBean 配置
 * @program: TestSpringboot
 * @author: Lming
 * @create: 2020-05-21 10:25
 **/
@Configuration
@ComponentScan(value = {"com.lming"})
@Import({MyImportBeanDefinitionRegistrar.class, MyBeanFactoryPostProcessor.class})
@EnableAspectJAutoProxy
public class MySpringConfig {
    
    @Bean
    public UserEntity userEntity() {
        return new UserEntity(1, "张三");
    }
}
