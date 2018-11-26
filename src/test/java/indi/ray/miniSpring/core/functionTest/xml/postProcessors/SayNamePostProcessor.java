package indi.ray.miniSpring.core.functionTest.xml.postProcessors;

import indi.ray.miniSpring.core.API.BeanPostProcessor;
import indi.ray.miniSpring.core.API.Ordered;
import indi.ray.miniSpring.core.beans.exception.BeansException;
import indi.ray.miniSpring.core.beans.support.AbstractBeanProcessor;
import org.apache.log4j.Logger;

public class SayNamePostProcessor extends AbstractBeanProcessor implements Ordered {
    private static final Logger logger = Logger.getLogger(SayNamePostProcessor.class);

    public int getOrder() {
        return Ordered.LOWEST_ORDER;
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        logger.info(beanName + " before initiation");
        return super.postProcessBeforeInitialization(bean, beanName);
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        logger.info(beanName + " after initiation");
        return super.postProcessAfterInitialization(bean, beanName);
    }
}
