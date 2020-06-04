package com.lming.service;

import java.lang.reflect.InvocationTargetException;

public interface MethodInterceptor {

    /**
     * 执行通知的拦截
     *
     * @param methodInvocation
     * @return
     */
    Object invoke(MethodInvocation methodInvocation) throws InvocationTargetException, IllegalAccessException;
}
