package indi.ray.miniSpring.core.functionTest.xml.basic;


import java.util.List;
import java.util.Set;

public class ConstructorTestBean {
    public String consString;

    private void showConsString() {
        System.out.println(consString);
    }

    public ConstructorTestBean() {
        consString = "()";
        showConsString();
    }

    public ConstructorTestBean(Object a) {
        consString = "(Object)";
        showConsString();
    }

    public ConstructorTestBean(Object a, PoJoTestBean b) {
        consString = "(Object,PoJoTestBean)";
        showConsString();
    }

    public ConstructorTestBean(Object a, PoJoTestBean b, int c) {
        consString = "(Object,PoJoTestBean,int)";
        showConsString();
    }

    public ConstructorTestBean(Object a, String c) {
        consString = "(Object,String)";
        System.out.println(c);
        showConsString();
    }

    public ConstructorTestBean(Object[] a, int[] c) {
        consString = "(Object[],int[])";
        showConsString();
    }

    public ConstructorTestBean(Integer c) {
        consString = "(Integer)";
        showConsString();
    }

    public ConstructorTestBean(List<Integer> c) {
        consString = "(List<Integer>)" + c.getClass();
        showConsString();
    }

    public ConstructorTestBean(Set<String> c) {
        consString = "(Set<String>)" + c.getClass() + " " + c.contains("1");
        showConsString();
    }
}
