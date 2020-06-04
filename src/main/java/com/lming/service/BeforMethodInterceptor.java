package com.lming.service;

import java.lang.reflect.InvocationTargetException;

public class BeforMethodInterceptor implements MethodInterceptor {
    public Object invoke(MethodInvocation methodInvocation) throws InvocationTargetException, IllegalAccessException {
        System.out.println("前置通知...");
        return methodInvocation.process();// 目标方法
    }
}