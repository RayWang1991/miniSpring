# miniSpring

## 项目需要

* jdk 1.8
* mvn 3.0+

## 特性

### IOC
* 支持XML配置
* 自定义一套xsd用于支持XML配置
* 支持注解配置
* 支持Autowired, Component, ComponentScan, DependsOn, Primary, Qualifier, Scope等注解
* 支持singleton与prototype作用域
* 支持BeanPostProcessor

### AOP
* 支持ProxyFactoryBean
* 支持Jdk动态代理
* 支持BeanNameAutoProxyCreator
* 支持多切面