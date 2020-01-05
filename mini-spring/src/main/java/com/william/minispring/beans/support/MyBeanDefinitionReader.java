package com.william.minispring.beans.support;

import com.william.minispring.beans.config.MyBeanDefinition;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * @Author: WilliamDream
 * @Description:
 * @Date: 2019/9/22 16:22
 */
public class MyBeanDefinitionReader {

    private Properties properties = new Properties();

    private List<String> registyBeanClasses = new ArrayList<>();

    private final String SCAN_PACKAGE = "scanPackage";

    public MyBeanDefinitionReader(String ... locations){
        InputStream is = this.getClass().getClassLoader().getResourceAsStream(locations[0].replace("classpath",""));
        try {
            properties.load(is);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(null!=is){
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        doScanner(SCAN_PACKAGE);
    }

    private void doScanner(String property){

    }

    public List<MyBeanDefinition> loadBeanDefinitions(){
        List<MyBeanDefinition> result = new ArrayList<>();
        for (String className : registyBeanClasses){
            MyBeanDefinition beanDefinition = doCreateBeanDefinition(className);
            if(beanDefinition == null){
                continue;
            }
            result.add(beanDefinition);
        }
        return result;
    }

    private MyBeanDefinition doCreateBeanDefinition(String className){
        try {
            Class<?> beanClass = Class.forName(className);
            //判断是否是接口，接口不能new，使用它的实现类
            if(!beanClass.isInterface()){
                MyBeanDefinition beanDefinition = new MyBeanDefinition();
                beanDefinition.setBeanClassName(className);
                beanDefinition.setFactoryBeanName(toLowerFirstChar(beanClass.getSimpleName()));
                return beanDefinition;
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }


    private String toLowerFirstChar(String name){
        char[] chars = name.toCharArray();
        chars[0] += 32;
        return String.valueOf(chars);

    }

    public Properties getProperties(){
        return this.properties;
    }
}
