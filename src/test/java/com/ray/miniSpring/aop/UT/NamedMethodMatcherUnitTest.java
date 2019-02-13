package com.ray.miniSpring.aop.UT;

import com.ray.miniSpring.aop.Man;
import com.ray.miniSpring.aop.Student;
import com.ray.miniSpring.aop.People;
import com.ray.miniSpring.aop.pointCut.NamedMatchMethodPointCut;
import com.ray.miniSpring.core.utils.AssertUtils;
import org.junit.Test;

import java.lang.reflect.Method;

public class NamedMethodMatcherUnitTest {


    @Test
    public void testHitName() throws Throwable {
        NamedMatchMethodPointCut namedMatchMethodPointCut = new NamedMatchMethodPointCut();
        namedMatchMethodPointCut.setMappedNames("say","getAge" , "getName", "getWeight");

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
            AssertUtils.assertTrue(namedMatchMethodPointCut.matches(method), method.getName() + " should be matched");
        }
    }

    @Test
    public void testNotHitName() throws Throwable {
        NamedMatchMethodPointCut namedMatchMethodPointCut = new NamedMatchMethodPointCut();
        namedMatchMethodPointCut.setMappedNames("getWeight");

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
            AssertUtils.assertTrue(!namedMatchMethodPointCut.matches(method), method.getName() + " should be matched");
        }
    }
}
