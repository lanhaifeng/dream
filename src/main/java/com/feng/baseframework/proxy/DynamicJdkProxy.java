package com.feng.baseframework.proxy;

import org.apache.poi.ss.formula.functions.T;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @ProjectName: baseframework
 * @Description: jdk动态代理
 * @Author: lanhaifeng
 * @CreateDate: 2019/3/7 22:57
 * @UpdateUser:
 * @UpdateDate: 2019/3/7 22:57
 * @UpdateRemark:
 * @Version: 1.0
 */
public class DynamicJdkProxy implements InvocationHandler {

    private Object target;

    public DynamicJdkProxy(Object target) {
        this.target = target;
    }

    public <T> T getProxyObject(){
        return (T)Proxy.newProxyInstance(target.getClass().getClassLoader(), target.getClass().getInterfaces(), this);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Object object = method.invoke(target, args);

        return object;
    }
}
