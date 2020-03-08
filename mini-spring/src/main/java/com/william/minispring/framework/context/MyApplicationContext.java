package com.william.minispring.framework.context;

import com.william.minispring.framework.annotation.MyAutoWired;
import com.william.minispring.framework.annotation.MyController;
import com.william.minispring.framework.annotation.MyService;
import com.william.minispring.framework.aop.MyAopProxy;
import com.william.minispring.framework.aop.MyCglibAopProxy;
import com.william.minispring.framework.aop.MyJdkDynamicAopProxy;
import com.william.minispring.framework.aop.config.MyAopConfig;
import com.william.minispring.framework.aop.support.MyAdvisedSupport;
import com.william.minispring.framework.beans.MyBeanFactory;
import com.william.minispring.framework.beans.MyBeanWrapper;
import com.william.minispring.framework.beans.config.MyBeanDefinition;
import com.william.minispring.framework.beans.config.MyBeanPostProcessor;
import com.william.minispring.framework.beans.support.MyBeanDefinitionReader;
import com.william.minispring.framework.beans.support.MyDefaultListableBeanFactory;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * @Author: WilliamDream
 * @Description: 此类不写为接口了，Spring中该类为接口
 * @Date: 2019/9/22 16:01
 */
public class MyApplicationContext extends MyDefaultListableBeanFactory implements MyBeanFactory {

    //配置文件路径
    private String [] configLocations;

    private MyBeanDefinitionReader reader;

    //单例的IOC容器
    private Map<String,Object> singletonObjects = new HashMap<String,Object>();

    //IOC容器
    private Map<String,MyBeanWrapper> factoryBeanInstanceCache = new HashMap<String, MyBeanWrapper>();


    public MyApplicationContext(String ... configLocations){
        this.configLocations = configLocations;
        refresh();
    }

    @Override
    public Object getBean(String beanName) throws Exception{
        MyBeanDefinition myBeanDefinition = this.beanDefinitionMap.get(beanName);
        Object instance = null;
        MyBeanPostProcessor postProcessor = new MyBeanPostProcessor();
        postProcessor.postProcessBeforeInitialization(instance,beanName);

        //1.初始化，先初始化可以解决循环依赖
        instance = instantiateBean(beanName,myBeanDefinition);

        //2.拿到beanWrapper之后，把beanWrapper
//        if(this.factoryBeanInstanceCache.containsKey(beanName)){
//            throw new Exception("The "+beanName +" is exists");
//        }
        //3.把这个对象封装到BeanWrapper
        MyBeanWrapper beanWrapper = new MyBeanWrapper(instance);

        //把BeanWrapper保存到IOC容器中去
        this.factoryBeanInstanceCache.put(beanName,beanWrapper);
        postProcessor.postProcessAfterInitialization(instance,beanName);
        //4.依赖注入
        populateBean(beanName,new MyBeanDefinition(),beanWrapper);

        return this.factoryBeanInstanceCache.get(beanName).getWrappedClass();
    }

    public Object getBean(Class<?> beanClass) throws Exception{
        return getBean(beanClass.getName());
    }

    private Object instantiateBean(String beanName,MyBeanDefinition myBeanDefinition){
        //1.拿到实例化的对象的类名
        String className = myBeanDefinition.getBeanClassName();
        Object instance = null;

        //2.反射实例化，得到一个对象
        try {
            if(this.singletonObjects.containsKey(className)){
                instance = this.singletonObjects.get(className);
            }else {
                Class<?> clazz = Class.forName(className);
                instance = clazz.newInstance();
                MyAdvisedSupport config = instantionAopConfig(myBeanDefinition);
                config.setTarget(instance);
                config.setTargetClass(clazz);

                //如果是切点就创建代理对象
                if(config.pointCutMatch()){
                   instance = createProxy(config).getProxy();
                }

                this.singletonObjects.put(className,instance);
                this.singletonObjects.put(myBeanDefinition.getFactoryBeanName(),instance);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        //4.把BeanWrapper保存到IOC容器中
        return instance;
    }

    private MyAopProxy createProxy(MyAdvisedSupport config) {
        Class targetClass = config.getTargetClass();
        if(targetClass.getInterfaces().length>0){
            return new MyJdkDynamicAopProxy(config);
        }
        return new MyCglibAopProxy(config);
    }

    private MyAdvisedSupport instantionAopConfig(MyBeanDefinition myBeanDefinition) {
        MyAopConfig config = new MyAopConfig();
        config.setPointCut(this.reader.getConfig().getProperty("pointCut"));
        config.setAspectClass(this.reader.getConfig().getProperty("aspectClass"));
        config.setAspectBefore(this.reader.getConfig().getProperty("aspectBefore"));
        config.setAspectAfter(this.reader.getConfig().getProperty("aspectAfter"));
        config.setAspectAfterThrow(this.reader.getConfig().getProperty("aspectAfterThrow"));
        config.setAspectAfterThrowing(this.reader.getConfig().getProperty("aspectAfterThrowingName"));
        return new MyAdvisedSupport(config);
    }



    private void populateBean(String beanName,MyBeanDefinition beanDefinition,MyBeanWrapper beanWrapper){
        Object instance = beanWrapper.getWrappedInstance();
        //判断只有加了注解的类，才执行依赖注入
        Class<?> clazz = beanWrapper.getWrappedClass();
        if(!(clazz.isAnnotationPresent(MyController.class)||clazz.isAnnotationPresent(MyService.class))){
            return;
        }
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields){
            if(!field.isAnnotationPresent(MyAutoWired.class))
                continue;
            MyAutoWired autoWired = field.getAnnotation(MyAutoWired.class);
            String autowiredBeanName = autoWired.value().trim();
            if("".equals(autowiredBeanName)){
                autowiredBeanName = field.getType().getName();
            }
            field.setAccessible(true);
            try {
                field.set(instance,this.factoryBeanInstanceCache.get(autowiredBeanName).getWrappedClass());
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }

    }


    @Override
    protected void refresh() {
        //1.定位，定位配置文件

        reader = new MyBeanDefinitionReader();

        //2.加载配置文件，扫描相关类，把他们封装成BeanDefinitiion
        List<MyBeanDefinition> beanDefinitions = reader.loadBeanDefinitions();
        //3.注册，把bean放入容器中
        doRegisterBeanDefinition(beanDefinitions);

        //4.把不是延时加载的类先初始化
        doAutowrited();
    }

    private void doRegisterBeanDefinition(List<MyBeanDefinition> beanDefinitions){
        for (MyBeanDefinition beanDefinition : beanDefinitions){
            super.beanDefinitionMap.put(beanDefinition.getFactoryBeanName(),beanDefinition);
        }


    }

    private void doAutowrited(){
        for (Map.Entry<String, MyBeanDefinition> beanDefinitionEntry : super.beanDefinitionMap.entrySet()){
            String beanName = beanDefinitionEntry.getKey();
            if (!beanDefinitionEntry.getValue().isLazyInit()){
                try {
                    getBean(beanName);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public String[] getBeanDefinitionNames(){
        return this.beanDefinitionMap.keySet().toArray(new String[this.beanDefinitionMap.size()]);


    }
    public Properties getConfig(){
        return this.reader.getConfig();
    }

}
