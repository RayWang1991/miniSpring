package indi.ray.annotationSpring.core.beans.definition;

import indi.ray.annotationSpring.core.utils.AssertUtils;
import indi.ray.annotationSpring.core.utils.ObjectUtils;
import indi.ray.annotationSpring.core.utils.StringUtils;

import java.util.Arrays;

/**
 * This is an abstract class that implements {@Code BeanDefinition}. Subclasses could
 * inherit this class for convenience
 */
public class AbstractBeanDefinition implements BeanDefinition {
    private String          beanName;
    private String          parentBeanName;
    private ScopeEnum       scopeEnum;
    private String          beanClassName;
    private Class<?>        beanClass;
    private String          initMethodName;
    //    String    destroyMethodName;,todo
    private String          factoryBeanName;
    private String          factoryMethodName;
    private String[]        dependsOnBeanNames;
    private BeanConstructor constructor;
    private BeanField[]     fields;
    private boolean         lazyInit;
    boolean primary;


    /**
     * @see BeanDefinition#validate()
     */
    public void validate() {
        AssertUtils.assertTrue(StringUtils.isNotBlank(this.beanName), "bean name can not be blank");
        AssertUtils.assertNotNull(this.beanClass, "bean class can not be null");
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "{" +
                "beanName='" + beanName + '\n' +
                ", parentBeanName='" + parentBeanName + '\n' +
                ", scopeEnum=" + scopeEnum + '\n' +
                ", beanClassName='" + beanClassName + '\n' +
                ", beanClass=" + beanClass + '\n' +
                ", initMethodName='" + initMethodName + '\n' +
                ", factoryBeanName='" + factoryBeanName + '\n' +
                ", factoryMethodName='" + factoryMethodName + '\n' +
                ", dependsOnBeanNames=" + Arrays.toString(dependsOnBeanNames) +"\n" +
                ", constructor=" + constructor + '\n' +
                ", fields=" + Arrays.toString(fields) + '\n' +
                ", primary=" + primary + '\n' +
                '}';
    }

    @Override
    public boolean equals(Object other) {
        if (other == null) {
            return false;
        }

        if (this == other) {
            return true;
        }

        if (!(other instanceof AbstractBeanDefinition)) return false;
        AbstractBeanDefinition that = (AbstractBeanDefinition) other;
        if (!StringUtils.equals(this.beanName, that.beanName)) return false;
        if (!StringUtils.equals(this.parentBeanName, that.parentBeanName)) return false;
        if (this.scopeEnum != that.scopeEnum) return false;
        if (this.beanClass != that.beanClass) return false;
        if (!StringUtils.equals(this.initMethodName, that.initMethodName)) return false;
        if (!StringUtils.equals(this.factoryBeanName, that.factoryBeanName)) return false;
        if (!StringUtils.equals(this.factoryMethodName, that.factoryMethodName)) return false;
        if (!Arrays.equals(this.dependsOnBeanNames, that.dependsOnBeanNames)) return false;
        if (!ObjectUtils.equals(this.constructor, that.constructor)) return false;
        if (!Arrays.equals(this.fields, that.fields)) return false;
        return true;
    }

    public void setBeanName(String beanName) {
        this.beanName = beanName;
    }

    /**
     * @see BeanDefinition#getBeanName()
     */
    public String getBeanName() {
        return this.beanName;
    }

    public void setBeanClassName(String beanClassName) {
        this.beanClassName = beanClassName;
    }

    /**
     * @see BeanDefinition#getBeanClassName()
     */
    public String getBeanClassName() {
        return this.beanClassName;
    }

    public void setParentBeanName(String parentBeanName) {
        this.parentBeanName = parentBeanName;
    }

    /**
     * @see BeanDefinition#getParentBeanName()
     */
    public String getParentBeanName() {
        return this.parentBeanName;
    }

    public void setScopeEnum(ScopeEnum scopeEnum) {
        this.scopeEnum = scopeEnum;
    }

    /**
     * @see BeanDefinition#getScopeEnum()
     */
    public ScopeEnum getScopeEnum() {
        return this.scopeEnum;
    }

    public Class<?> getBeanClass() {
        return beanClass;
    }

    public void setBeanClass(Class<?> beanClass) {
        this.beanClass = beanClass;
        this.beanClassName = beanClass.getName();
    }

    public void setFactoryBeanName(String factoryBeanName) {
        this.factoryBeanName = factoryBeanName;
    }

    /**
     * @see BeanDefinition#getFactoryBeanName()
     */
    public String getFactoryBeanName() {
        return this.factoryBeanName;
    }

    public void setFactoryMethodName(String factoryMethodName) {
        this.factoryMethodName = factoryMethodName;
    }

    /**
     * @see BeanDefinition#getFactoryMethodName()
     */
    public String getFactoryMethodName() {
        return this.factoryMethodName;
    }

    public void setInitMethodName(String initMethodName) {
        this.initMethodName = initMethodName;
    }

    /**
     * @see BeanDefinition#getInitMethodName()
     */
    public String getInitMethodName() {
        return this.initMethodName;
    }

    public void setDependsOnBeanNames(String[] dependsOn) {
        this.dependsOnBeanNames = dependsOn;
    }

    /**
     * @see BeanDefinition#getDependsOnBeanNames()
     */
    public String[] getDependsOnBeanNames() {
        return this.dependsOnBeanNames;
    }

    /**
     * @see BeanDefinition#isPrimary()
     */
    public boolean isPrimary() {
        return primary;
    }

    /**
     * @see BeanDefinition#setPrimary(boolean)
     */
    public void setPrimary(boolean primary) {
        this.primary = primary;
    }

    /**
     * @see BeanDefinition#setConstructor(BeanConstructor)
     */
    public void setConstructor(BeanConstructor constructor) {
        AssertUtils.assertTrue(constructor != null && constructor.getConstructor() != null, "构造器为空");
        if (!constructor.getConstructor().isAccessible()) {
            constructor.getConstructor().setAccessible(true);
        }
        this.constructor = constructor;
    }

    /**
     * @see BeanDefinition#getConstructor()
     */
    public BeanConstructor getConstructor() {
        return this.constructor;
    }

    /**
     * @see BeanDefinition#setFields(BeanField[])
     */
    public void setFields(BeanField[] fields) {
        this.fields = fields;
    }

    /**
     * @see BeanDefinition#getFields()
     */
    public BeanField[] getFields() {
        return this.fields;
    }

    public boolean isLazyInit() {
        return lazyInit;
    }

    public void setLazyInit(boolean lazyInit) {
        this.lazyInit = lazyInit;
    }
}
