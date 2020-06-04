package com.lming.service;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

public interface MethodInvocation {
    /**
     * 方法调用 每个通知的方法
     *
     * @return
     */
    Object process() throws InvocationTargetException, IllegalAccessException;
}

class DefaultMethodInvacation implements MethodInvocation {
    // 方法拦截
    private List<MethodInterceptor> chian;
    private Object target;// 目标对象
    private Method method;// 目标方法
    private Object args[];// 目标参数
    int currentChianIndex;// 记录当前拦截器链调用指针

    public DefaultMethodInvacation(List<MethodInterceptor> chian, Object target, Method method, Object[] args) {
        this.chian = chian;
        this.target = target;
        this.method = method;
        this.args = args;
    }

    public Object process() throws InvocationTargetException, IllegalAccessException {
        if (currentChianIndex == chian.size()) {
            // 执行目标方法
            return method.invoke(target, args);
        }
        MethodInterceptor methodInterceptor = chian.get(currentChianIndex++);
        // 传递当前下一个通知
        return methodInterceptor.invoke(this);
    }
}
