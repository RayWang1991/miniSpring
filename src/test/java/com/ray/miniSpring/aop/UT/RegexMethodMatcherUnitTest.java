package com.ray.miniSpring.aop.UT;

import com.ray.miniSpring.aop.Man;
import com.ray.miniSpring.aop.People;
import com.ray.miniSpring.aop.Student;
import com.ray.miniSpring.aop.pointCut.JdkRegexMethodPointCut;
import com.ray.miniSpring.core.utils.AssertUtils;
import org.junit.Test;

import java.lang.reflect.Method;

public class RegexMethodMatcherUnitTest {


    @Test
    public void testHitName() throws Throwable {
        JdkRegexMethodPointCut pointCut = new JdkRegexMethodPointCut();
        pointCut.setPatterns("get.*", "say");

        Man man = new Man();
        Student student = new Student();

        Method manSay = man.getClass().getMethod("say");
        Method manGetAge = man.getClass().getMethod("getAge");
        Method manGetName = man.getClass().getMethod("getName");
        Method studentSay = student.getClass().getMethod("say");
        Method studentGetAge = student.getClass().getMethod("getAge");
        Method studentGetName = student.getClass().getMethod("getName");
        Method peopleSay = People.class.getMethod("say");
        Method peopleGetAge = People.class.getMethod("getAge");
        Method peopleGetName = People.class.getMethod("getName");

        Method[] methods = new Method[]{
                manSay, manGetAge, manGetName,
                studentSay, studentGetAge, studentGetName,
                peopleSay, peopleGetAge, peopleGetName,

        };
        for (Method method : methods) {
            AssertUtils.assertTrue(pointCut.matches(method), method.getName() + " should be matched");
        }
    }

    @Test
    public void testNotHitName() throws Throwable {
        JdkRegexMethodPointCut pointCut = new JdkRegexMethodPointCut();
        pointCut.setPatterns("(get[0-9]+)", "s[^a]y");

        Man man = new Man();
        Student student = new Student();

        Method manSay = man.getClass().getMethod("say");
        Method manGetAge = man.getClass().getMethod("getAge");
        Method manGetName = man.getClass().getMethod("getName");
        Method studentSay = student.getClass().getMethod("say");
        Method studentGetAge = student.getClass().getMethod("getAge");
        Method studentGetName = student.getClass().getMethod("getName");
        Method peopleSay = People.class.getMethod("say");
        Method peopleGetAge = People.class.getMethod("getAge");
        Method peopleGetName = People.class.getMethod("getName");

        Method[] methods = new Method[]{
                manSay, manGetAge, manGetName,
                studentSay, studentGetAge, studentGetName,
                peopleSay, peopleGetAge, peopleGetName,

        };
        for (Method method : methods) {
            AssertUtils.assertTrue(!pointCut.matches(method), method.getName() + " should not be matched");
        }
    }

    @Test
    public void testWithExcluded() throws Throwable {
        JdkRegexMethodPointCut pointCut = new JdkRegexMethodPointCut();
        pointCut.setPatterns("get[A-Za-z]+", "say");
        pointCut.setExcludedPatterns("(getN.*)");

        Man man = new Man();
        Student student = new Student();

        Method manSay = man.getClass().getMethod("say");
        Method manGetAge = man.getClass().getMethod("getAge");
        Method manGetName = man.getClass().getMethod("getName");
        Method studentSay = student.getClass().getMethod("say");
        Method studentGetAge = student.getClass().getMethod("getAge");
        Method studentGetName = student.getClass().getMethod("getName");
        Method peopleSay = People.class.getMethod("say");
        Method peopleGetAge = People.class.getMethod("getAge");
        Method peopleGetName = People.class.getMethod("getName");

        Method[] methodsToBeHit = new Method[]{
                manSay, manGetAge,
                studentSay, studentGetAge,
                peopleSay, peopleGetAge,
        };
        Method[] methodsNotToBeHit = new Method[]{
                manGetName,
                studentGetName,
                peopleGetName,
        };
        for (Method method : methodsToBeHit) {
            AssertUtils.assertTrue(pointCut.matches(method), method.getName() + " should be matched");
        }

        for (Method method : methodsNotToBeHit) {
            AssertUtils.assertTrue(!pointCut.matches(method), method.getName() + " should not be matched");
        }
    }
}
