package indi.ray.miniSpring.core.beans.definition;

public class XmlBeanDefinition extends AbstractBeanDefinition {
    private AutowireType autowireType;

    @Override
    public AutowireType getAutowireType() {
        return autowireType;
    }

    public void setAutowireType(AutowireType autowireType) {
        this.autowireType = autowireType;
    }
}
