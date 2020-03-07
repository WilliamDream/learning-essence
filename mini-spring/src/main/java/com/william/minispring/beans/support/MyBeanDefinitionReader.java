package com.william.minispring.beans.support;

import com.william.minispring.beans.config.MyBeanDefinition;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * @Author: WilliamDream
 * @Description: 定位配置文件的核心类，Spring中BeanDefinitionReader是接口，
 * 它的子类如AbstractBeanDefinitionReader,XmlBeanDefinitionReader等
 * @Date: 2019/9/22 16:22
 */
public class MyBeanDefinitionReader {

    private Properties config = new Properties();

    private List<String> registyBeanClasses = new ArrayList<>();

    private final String SCAN_PACKAGE = "scanPackage";

    public MyBeanDefinitionReader(String ... locations){
        //通过地址找到文件转换为文件流
        InputStream is = this.getClass().getClassLoader().getResourceAsStream(locations[0].replace("classpath",""));
        try {
            config.load(is);
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

        doScanner(config.getProperty(SCAN_PACKAGE));
    }

    //扫描相应包下的类
    private void doScanner(String property){
        //将包路径转换为文件路径
        URL url = this.getClass().getClassLoader().getResource("/" + property.replaceAll("\\.","/"));
        File classPath = new File(url.getFile());

        for (File file : classPath.listFiles()){
            if(file.isDirectory()){
                doScanner(property + "." + file.getName());
            }else {
                if(!file.getName().endsWith(".class")){
                    continue;
                }
                registyBeanClasses.add(property + "." + file.getName().replaceAll(".class",""));
            }
        }
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
        return this.config;
    }

    public Properties getConfig(){
        return this.config;
    }
}
