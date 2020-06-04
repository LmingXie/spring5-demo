package com.lming.aspect;

import com.alibaba.fastjson.JSON;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;

/**
 * @description: 自定义的BeanFactory后置处理器
 * @program: TestSpringboot
 * @author: Lming
 * @create: 2020-05-22 11:42
 **/
public class MyBeanFactoryPostProcessor implements BeanFactoryPostProcessor {
    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        String[] beanDefinitionNames = beanFactory.getBeanDefinitionNames();
        System.out.println("BeanFactory后置处理器，筛选所有Bean实例中名称为“payEntity”的实例，并将其amount改为500");
        for (String beanDefinitionName : beanDefinitionNames) {
            System.out.println("当前迭代实例------>>>" + beanDefinitionName);
            if (beanDefinitionName.equals("payEntity")) {
                BeanDefinition beanDefinition = beanFactory.getBeanDefinition(beanDefinitionName);
                System.out.println("开始修改，对象原属性值为：" + JSON.toJSONString(beanDefinition.getPropertyValues()));
                beanDefinition.getPropertyValues().add("amount", "500");
                System.out.println("修改后，对象原属性值为：" + JSON.toJSONString(beanDefinition.getPropertyValues()));
                return;
            }
        }
    }
}
