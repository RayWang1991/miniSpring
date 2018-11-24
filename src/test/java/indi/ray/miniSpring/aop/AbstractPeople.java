package indi.ray.miniSpring.aop;

public abstract class AbstractPeople implements People {
    protected boolean isFemale;

    protected int age;

    protected String name;

    public void setFemale(boolean female) {
        isFemale = female;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public int getAge() {
        return this.age;
    }

    public boolean isFemale() {
        return this.isFemale;
    }
}
