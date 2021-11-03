package cn.orzlinux.step4_file_img.Utils;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class SpringUtil implements ApplicationContextAware {
    private static ApplicationContext ac;

    @Override
    public void setApplicationContext(ApplicationContext arg0) throws BeansException {
        ac = arg0;
    }

    public static ApplicationContext getApplicationContext() {
        return ac;
    }

    /**
     * 通过class获取Bean
     */
    public static <T> T getBean(Class<T> clazz){
        return getApplicationContext().getBean(clazz);
    }


    /**
     * 如果BeanFactory包含所给名称匹配的bean返回true
     * @param name
     * @return boolean
     */
    public static boolean containsBean(String name) {
        return ac.containsBean(name);
    }

    /**
     * 判断注册的bean是singleton还是prototype。
     * 如果与给定名字相应的bean定义没有被找到，将会抛出一个异常（NoSuchBeanDefinitionException）
     * @param name
     * @return boolean
     */
    public static boolean isSingleton(String name) {
        return ac.isSingleton(name);
    }

    /**
     * @param name
     * @return Class 注册对象的类型
     */
    public static Class<?> getType(String name) {
        return ac.getType(name);
    }

}