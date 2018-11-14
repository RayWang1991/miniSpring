package labTest.loadFile;

import indi.ray.annotationSpring.core.annotations.Autowired;
import indi.ray.annotationSpring.core.annotations.Component;
import indi.ray.annotationSpring.core.annotations.Qualifier;
import indi.ray.annotationSpring.core.beans.definition.BeanDefinition;
import indi.ray.annotationSpring.core.beans.exception.BeansException;
import indi.ray.annotationSpring.core.context.scanner.ClassPathBeanDefinitionsProvider;
import indi.ray.annotationSpring.core.utils.AssertUtils;
import indi.ray.annotationSpring.core.utils.Executable;
import org.apache.log4j.Logger;
import org.junit.Test;


@Component
public class ClassPathBeanDefinitionProviderTest {
    private static final Logger logger = Logger.getLogger(ClassPathBeanDefinitionProviderTest.class);

    protected ClassPathBeanDefinitionsProvider provider = new ClassPathBeanDefinitionsProvider();

    @Autowired
    @Qualifier("hahaha")
    private Integer integer;

    @Test
    public void testBeanFieldScan() {
        BeanDefinition beanDefinition = provider.generateBeanDefinition(ClassPathBeanDefinitionProviderTest.class);
        logger.info(beanDefinition);
    }

    @Test
    public void testBeanConstructorScanWithOneAutowired() {
        BeanDefinition beanDefinition = provider.generateBeanDefinition(ClassWithOneAutowireConstructor.class);
        logger.info(beanDefinition);
    }

    @Test
    public void testBeanConstructorScanWithMoreAutowire() {
        AssertUtils.assertThrows(BeansException.class, new Executable() {
            public void execute() throws Exception {
                BeanDefinition beanDefinition = provider.generateBeanDefinition(ClassWithMoreAutowiredConstructor.class);
                logger.info(beanDefinition);
            }
        });
    }

    @Test
    public void testWithoutAutowire(){
        BeanDefinition beanDefinition = provider.generateBeanDefinition(ClassWithNoAutowiredConstructor.class);
        logger.info(beanDefinition);
    }
}
