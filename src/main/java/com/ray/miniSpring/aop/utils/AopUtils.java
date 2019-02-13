package com.ray.miniSpring.aop.utils;

import com.ray.miniSpring.aop.exceptions.AopInvocationException;
import com.ray.miniSpring.core.utils.AssertUtils;
import com.ray.miniSpring.aop.advise.Advice;
import com.ray.miniSpring.aop.advise.MethodInterceptor;
import com.ray.miniSpring.aop.advisor.Advisor;
import com.ray.miniSpring.aop.advisor.DefaultPointCutAdvisor;
import com.ray.miniSpring.aop.advisor.PointCutAdvisor;
import com.ray.miniSpring.aop.pointCut.MethodMatcher;
import com.ray.miniSpring.aop.proxy.Advised;
import com.ray.miniSpring.aop.proxy.AdvisedSupport;
import com.ray.miniSpring.core.utils.ArrayUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class AopUtils {

    /**
     * 根据配置返回静态命中方法的Advisor。如果methodMatcher需要在运行时匹配，则一并返回。
     * 注意，不提供缓存
     *
     * @param config
     * @param method
     * @param targetClass
     * @return
     */
    public static List<Object> getInterceptorsAndDynamicInterceptionAdvice(Advised config, Method method, Class<?> targetClass) {
        Advisor[] advisors = config.getAdvisors();
        List<Object> interceptorList = new ArrayList<Object>(advisors.length);
        Class<?> actualClass = targetClass == null ? targetClass : method.getDeclaringClass();

        for (Advisor advisor : advisors) {
            AssertUtils.assertTrue(advisor instanceof PointCutAdvisor, "目前只支持PointCutAdvisor");
            PointCutAdvisor pointCutAdvisor = (PointCutAdvisor) advisor;
            if (pointCutAdvisor.getPointCut().getClassFilter().matches(actualClass)) {
                MethodMatcher methodMatcher = pointCutAdvisor.getPointCut().getMethodMatcher();
                Advice advice = pointCutAdvisor.getAdvice();
                AssertUtils.assertTrue(advice instanceof MethodInterceptor, "目前只支持MethodInterceptor");
                MethodInterceptor interceptor = (MethodInterceptor) advice;
                if (methodMatcher.matches(method)) {
                    if (methodMatcher.isRuntime()) {
                        interceptorList.add(new InterceptorAndDynamicMethodMatcher(interceptor, methodMatcher));
                    } else {
                        interceptorList.add(interceptor);
                    }
                }
            }
        }
        return interceptorList;
    }

    public static Class<?>[] completeInterfaces(AdvisedSupport advisedSupport) {
        Class<?>[] interfaceClasses = advisedSupport.getProxiedInterfaces();
        if (ArrayUtils.getLength(interfaceClasses) == 0) {
            Class<?> targetClass = advisedSupport.getTargetSource().getTargetClass();
            if (targetClass.isInterface()) {
                advisedSupport.setInterfaces(targetClass);
            }
            interfaceClasses = advisedSupport.getProxiedInterfaces();
        }
        int userDefinedCount = interfaceClasses.length;

        int nonUserDefinedCount = 0;
        boolean addAdvised = advisedSupport.isInterfaceProxied(Advised.class);
        if (addAdvised) {
            nonUserDefinedCount++;
        }

        Class<?>[] completeInterfaces = new Class<?>[userDefinedCount + nonUserDefinedCount];
        System.arraycopy(interfaceClasses, 0, completeInterfaces, 0, userDefinedCount);
        int index = userDefinedCount;
        if (addAdvised) {
            completeInterfaces[index] = Advised.class;
            index++;
        }
        return completeInterfaces;
    }

    public static void makeMethodAccessibleIfNecessary(Method method) {
        if (!method.isAccessible()) {
            method.setAccessible(true);
        }
    }

    public static Object invokeMethodUsingReflective(Method method, Object target, Object[] args) throws Throwable {
        try {
            makeMethodAccessibleIfNecessary(method);
            return method.invoke(target, args);
        } catch (InvocationTargetException e) {
            // rethrow the checked exception
            throw e.getTargetException();
        } catch (IllegalArgumentException e) {
            throw new AopInvocationException(e.toString());
        } catch (IllegalAccessException e) {
            throw new AopInvocationException(e.toString());
        }
    }

    public static Advisor wrapAdvisorIfNeeded(Object advice) {
        if (advice instanceof Advisor) {
            return (Advisor) advice;
        }
        if (advice instanceof MethodInterceptor) {
            return new DefaultPointCutAdvisor((Advice) advice);
        }
        throw new UnsupportedOperationException("Only MethodInterceptor and Advisor are valid");
    }
}
