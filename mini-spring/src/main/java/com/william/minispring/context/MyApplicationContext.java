package com.william.minispring.context;

import com.william.minispring.beans.MyBeanFactory;
import com.william.minispring.beans.MyBeanWrapper;
import com.william.minispring.beans.config.MyBeanDefinition;
import com.william.minispring.beans.support.MyBeanDefinitionReader;
import com.william.minispring.beans.support.MyDefaultListableBeanFactory;

import java.util.List;
import java.util.Map;

/**
 * @Author: WilliamDream
 * @Description:
 * @Date: 2019/12/22 16:01
 */
public class MyApplicationContext extends MyDefaultListableBeanFactory implements MyBeanFactory{

    private String [] configLocations;

    private MyBeanDefinitionReader reader;

    public MyApplicationContext(String ... configLocations){
        this.configLocations = configLocations;
    }

    @Override
    public Object getBean(String beanName) {
        //1.初始化
        instantiateBean(beanName);
        //2.注入
        populateBean(beanName,new MyBeanDefinition(),new MyBeanWrapper());

        return null;
    }

    private void instantiateBean(String beanName){

    }

    private void populateBean(String beanName,MyBeanDefinition beanDefinition,MyBeanWrapper beanWrapper){

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
                getBean(beanName);
            }


        }
    }

}
