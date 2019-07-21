package com.william.minispringmvc.servlet;

import com.william.minispringmvc.annotation.*;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Author: WilliamDream
 * @Description:
 * @Date: 2019/7/14 21:05
 */
public class MyDispatcherServlet extends HttpServlet {

    private Properties properties = new Properties();

    private List<String> classNameList = new ArrayList<>();

    private Map<String,Object> IOC = new HashMap<>();

    private List<Handler> handlerMapping = new ArrayList<>();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp);
        this.doPost(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
        doDispath(req, resp);
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
        doAutoWired();

        //5、初始化HanderMapping
        initHanderMapping();

        System.out.println("初始化完成!");


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
                        IOC.put(beanName,clazz.newInstance());
                        continue;
                    }
                    //3、根据类型自动赋值
                    //?????????????????
                    Class<?>[] intencefaces = clazz.getInterfaces();//获得接口的实现类
                    for (Class<?> i : intencefaces){
                        IOC.put(i.getName(),clazz.newInstance());
                    }



                }else{
                    continue;
                }

            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    //自动依赖注入
    private void doAutoWired() {
        if(IOC.isEmpty())
            return;
        for (Map.Entry<String,Object> entry : IOC.entrySet()){
            Field [] fileds = entry.getValue().getClass().getDeclaredFields();
            for (Field field : fileds){
                if(!field.isAnnotationPresent(MyAutoWired.class))
                    continue;
                MyAutoWired autoWired = field.getAnnotation(MyAutoWired.class);
                String beanname = autoWired.value().trim();
                if("".equals(beanname)){
                    beanname = field.getType().getName();
                }
                field.setAccessible(true);

                try {
                    //利用反射给字段赋值，实现自动注入依赖对象
                    field.set(entry.getValue(),IOC.get(beanname));
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }

            }

        }

    }

    private void initHanderMapping() {
        if(IOC.isEmpty())
            return;

        String url = "";
        for(Map.Entry<String,Object> entry : IOC.entrySet()){
            Class<?> clazz = entry.getValue().getClass();

            if(!clazz.isAnnotationPresent(MyController.class))
                continue;

            //获取Controller上的MyRequestMapping中的url配置
            if(clazz.isAnnotationPresent(MyRequestMapping.class)){
               MyRequestMapping requestMapping = clazz.getAnnotation(MyRequestMapping.class);
               url = requestMapping.value();
            }

            Method[] methods = clazz.getMethods();
            for (Method method : methods){
                if(!method.isAnnotationPresent(MyRequestMapping.class))
                    continue;
                MyRequestMapping requestMapping = method.getAnnotation(MyRequestMapping.class);
                String reqex = ("/" + url + "/"+requestMapping.value()).replaceAll("/+","/");
                Pattern pattern = Pattern.compile(reqex);
                handlerMapping.add(new Handler(entry.getValue(),method,pattern));

            }

        }

    }

    private String lowerFirstChar(String name){
        char[] chars = name.toCharArray();
        chars[0] += 32;
        return String.valueOf(chars);

    }


    private void doDispath(HttpServletRequest req, HttpServletResponse resp) {
        try {
            Handler handler = getHandler(req);
            if(handler == null){
                //404错误
                resp.getWriter().write("Resource Not Found,404!");
                return;
            }
            //获取方法的参数列表
            Class<?> [] paramTypes = handler.method.getParameterTypes();

            Object [] paramValues = new Object[paramTypes.length];
            Map<String,String[]> params = req.getParameterMap();
            for (Map.Entry<String,String[]> param : params.entrySet()){
                String value = Arrays.toString(param.getValue()).replaceAll("\\[|\\]", "").replaceAll(",\\s", ",");
                if(!handler.paramIndexMapping.containsKey(param.getKey())){continue;}
                int index = handler.paramIndexMapping.get(param.getKey());
                paramValues[index] = convert(paramTypes[index],value);
            }

            //设置方法中的request和response对象
            int reqIndex = handler.paramIndexMapping.get(HttpServletRequest.class.getName());
            paramValues[reqIndex] = req;
            int respIndex = handler.paramIndexMapping.get(HttpServletResponse.class.getName());
            paramValues[respIndex] = resp;

            Object res = handler.method.invoke(handler.controller, paramValues);
            if(res instanceof Void)
                return;

            resp.getWriter().write(res.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    private Handler getHandler(HttpServletRequest req) throws Exception{
        if(handlerMapping.isEmpty())
            return null;

        String url = req.getRequestURI();
        String contextPath = req.getContextPath();
        url = url.replace(contextPath, "").replaceAll("/+", "/");
        for (Handler handler : handlerMapping) {
            try{
                Matcher matcher = handler.pattern.matcher(url);
                //如果没有匹配上继续下一个匹配
                if(!matcher.matches())
                    continue;

                return handler;
            }catch(Exception e){
                throw e;
            }
        }
        return null;
    }


    private class Handler {

        //保存方法对应的实例
        protected Object controller;
        //保存映射的方法
        protected Method method;
        protected Pattern pattern;
        //方法参数顺序
        protected Map<String,Integer> paramIndexMapping;

        public Handler(Object controller, Method method, Pattern pattern) {
            this.controller = controller;
            this.method = method;
            this.pattern = pattern;
            paramIndexMapping = new HashMap<String,Integer>();
            putMethodParamIndex(method);

        }

        private void putMethodParamIndex(Method method) {
            Annotation[][] annotations = method.getParameterAnnotations();

            //获取方法参数中加了MyRequestParam的注解
            for (int i=0;i<annotations.length;i++){
                for (Annotation a : annotations[i]){
                    if(a instanceof MyRequestParam){
                        String paramNmae = ((MyRequestParam) a).value();
                        if(!"".equals(paramNmae.trim())){
                            paramIndexMapping.put(paramNmae,i);
                        }

                    }
                }
            }

            //提取方法中的request和response参数
            Class<?> [] paramsTypes = method.getParameterTypes();
            for (int i = 0; i < paramsTypes.length ; i ++) {
                Class<?> type = paramsTypes[i];
                if(type == HttpServletRequest.class ||
                        type == HttpServletResponse.class){
                    paramIndexMapping.put(type.getName(),i);
                }
            }



        }


    }

    //只需要把String转换为任意类型就好
    private Object convert(Class<?> type,String value){
        if(Integer.class == type){
            return Integer.valueOf(value);
        }
        //如果还有double或者其他类型，继续加if
        //这时候，我们应该想到策略模式了
        //在这里暂时不实现，希望小伙伴自己来实现
        return value;
    }

}
