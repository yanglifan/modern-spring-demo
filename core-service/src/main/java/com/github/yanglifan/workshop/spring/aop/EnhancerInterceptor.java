package com.github.yanglifan.workshop.spring.aop;

import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * @author Yang Lifan
 */
class EnhancerInterceptor implements MethodInterceptor {
    public Object intercept(Object obj, Method method, Object[] arg, MethodProxy proxy) throws Throwable {
        if (method.getName().toLowerCase().contains("add")) {
//            System.out.println("Before:" + method);
            Object object = proxy.invokeSuper(obj, arg);
//            System.out.println("After:" + method);
            return object;
        }
        return obj;
    }
}
