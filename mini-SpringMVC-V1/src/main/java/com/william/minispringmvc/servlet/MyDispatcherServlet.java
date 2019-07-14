package com.william.minispringmvc.servlet;

import com.william.minispringmvc.annotation.MyController;
import com.william.minispringmvc.annotation.MyService;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.*;

/**
 * @Author: WilliamDream
 * @Description:
 * @Date: 2019/7/14 21:05
 */
public class MyDispatcherServlet extends HttpServlet {

    private Properties properties = new Properties();

    private List<String> classNameList = new ArrayList<>();

    private Map<String,Object> IOC = new HashMap<>();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }

    @Override
    public void init(ServletConfig config) throws ServletException {

        //1、加载配置文件
        doLoadConfig("");

        //2、扫描相关类
        doScan("");

        //3、初始化相关类，并放入到IOC容器中
        doInstance();

        //4、完成依赖注入


        //5、初始化HanderMapping



        super.init(config);
    }

    @Override
    public void init() throws ServletException {
        super.init();
    }

    /**
       * @Description 加载配置文件
       * @param configPath
       * @return void
       * @Date 2019/7/14 21:15
       */

    private void doLoadConfig(String configPath){
        InputStream is = this.getClass().getClassLoader().getResourceAsStream(configPath);
        try {
            properties.load(is);
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            if (is!=null){
                try {
                    is.close();
                }catch (Exception ex){
                    ex.printStackTrace();
                }
            }
        }
    }

    //扫描相关类
    private void doScan(String packageName){
        //将包路径转换为文件路径
        URL url = this.getClass().getClassLoader().getResource("/"+packageName.replaceAll("\\.","/"));
        File classPath = new File(url.getFile());

        for (File file : classPath.listFiles()){
            if(file.isDirectory()){
                doScan(packageName + "." + file.getName());
            }else {
                if(!file.getName().endsWith(".class")){
                    continue;
                }
                classNameList.add(packageName + "." + file.getName().replaceAll(".class",""));
            }
        }
    }

    //
    private void doInstance(){
        if(classNameList.isEmpty())
            return;
        try {
            for (String className : classNameList){
                Class<?> clazz = Class.forName(className);
                //只需要初始化带有MyController和MyService
                String name = clazz.getSimpleName();
                if(clazz.isAnnotationPresent(MyController.class)){
                    Object instance = clazz.newInstance();
                    name = lowerFirstChar(name);
                    IOC.put(name,instance);

                }else if(clazz.isAnnotationPresent(MyService.class)){
                    //1、默认类名首字母小写
                    String beanName = lowerFirstChar(name);
                    //2、自定义类名
                    MyService service = clazz.getAnnotation(MyService.class);
                    if(!"".equals(service.value())){
                        beanName = service.value();
                    }
                    //3、根据类型自动赋值
                    



                }

            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }


    private String lowerFirstChar(String name){
        char[] chars = name.toCharArray();
        chars[0] += 32;
        return String.valueOf(chars);

    }

}
