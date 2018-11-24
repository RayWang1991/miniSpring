package indi.ray.miniSpring.aop.advise;

import indi.ray.miniSpring.aop.utils.AopUtils;
import indi.ray.miniSpring.aop.utils.InterceptorAndDynamicMethodMatcher;

import java.lang.reflect.Method;
import java.util.List;

public class ReflectiveMethodInvocation implements ProxyMethodInvocation {
    private List<?> interceptorsAndDynamicMethodMachers;

    private int currentIndex;

    private Object target;

    private Class<?> targetClass;

    private Method method;

    private Object[] arguments;

    public ReflectiveMethodInvocation(List<?> interceptorsAndDynamicMethodMatchers, Object target, Method method, Object[] arguments) {
        this.interceptorsAndDynamicMethodMachers = interceptorsAndDynamicMethodMatchers;
        this.target = target;
        this.method = method;
        this.arguments = arguments;
    }

    public Object proceed() throws Throwable {
        // start at 0, meaning next interceptor to match

        if (this.interceptorsAndDynamicMethodMachers.isEmpty()
                || this.currentIndex == this.interceptorsAndDynamicMethodMachers.size()) {
            return AopUtils.invokeMethodUsingReflective(method, target, arguments);
        }

        Object raw = this.interceptorsAndDynamicMethodMachers.get(currentIndex);
        currentIndex++;

        if (raw instanceof MethodInterceptor) {
            MethodInterceptor methodInterceptor = (MethodInterceptor) raw;
            // just call it
            return methodInterceptor.invoke(this);
        } else {
            InterceptorAndDynamicMethodMatcher idm = (InterceptorAndDynamicMethodMatcher) raw;
            if (idm.getMatcher().matches(method, arguments)) {
                MethodInterceptor methodInterceptor = idm.getInterceptor();
                return methodInterceptor.invoke(this);
            } else {
                return this.proceed();
            }
        }
    }

    public Method getMethod() {
        return method;
    }

    public Object[] getArguments() {
        return arguments;
    }


}
