package indi.ray.annotationSpring.core.functionTest.getPrototype;

import indi.ray.annotationSpring.core.annotations.Component;
import indi.ray.annotationSpring.core.annotations.Scope;
import indi.ray.annotationSpring.core.beans.definition.ScopeEnum;

import java.util.concurrent.atomic.AtomicInteger;

@Component
@Scope(ScopeEnum.PROTOTYPE)
public class Prototype {
    private static final AtomicInteger counter = new AtomicInteger(0);

    private int id;

    public Prototype() {
        this.id = counter.getAndIncrement();
    }

    @Override
    public int hashCode() {
        return this.id;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null) return false;
        if (!(obj instanceof Prototype)) return false;
        Prototype that = (Prototype) obj;
        return this.id == that.id;
    }

    @Override
    public String toString() {
        return "Prototype{" +
                "id=" + id +
                '}';
    }
}
