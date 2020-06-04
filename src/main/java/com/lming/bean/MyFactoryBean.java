package com.lming.bean;

import org.springframework.beans.factory.FactoryBean;

/**
 * @description: 自定义的Bean工厂
 * @program: TestSpringboot
 * @author: Lming
 * @create: 2020-05-21 11:40
 **/
public class MyFactoryBean implements FactoryBean<PayEntity> {
    @Override
    public PayEntity getObject() throws Exception {
        return new PayEntity().setType(1).setAmount(100);
    }
    
    @Override
    public Class<?> getObjectType() {
        return PayEntity.class;
    }
}
