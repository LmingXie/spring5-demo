package com.lming.bean;

import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;

/**
 * @description: 自定义手动注入bean
 * @program: TestSpringboot
 * @author: Lming
 * @create: 2020-05-21 11:20
 **/
public class MyImportBeanDefinitionRegistrar implements ImportBeanDefinitionRegistrar {
    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        String beanName = "payEntity";
        boolean beanNameInUse = registry.isBeanNameInUse(beanName);
        if (beanNameInUse) {
            System.out.println(beanName + "已被注入");
            return;
        }
        
        BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder.rootBeanDefinition(PayEntity.class);
        beanDefinitionBuilder.addPropertyValue("type", 1);
        beanDefinitionBuilder.addPropertyValue("amount", 100);
        
        registry.registerBeanDefinition(beanName, beanDefinitionBuilder.getBeanDefinition());
        
        System.out.println("手动注入Bean的结果为：" + registry.isBeanNameInUse(beanName));
    }
}
