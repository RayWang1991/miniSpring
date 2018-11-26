package indi.ray.miniSpring.core.functionTest.xml.postProcessors;

public class Counter {
    private int count;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public void count() {
        count++;
    }

    public void doDouble() {
        count *= 2;
    }
}
