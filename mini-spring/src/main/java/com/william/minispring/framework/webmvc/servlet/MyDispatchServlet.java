package com.william.minispring.framework.webmvc.servlet;

import com.william.minispring.framework.annotation.MyController;
import com.william.minispring.framework.annotation.MyRequestMapping;
import com.william.minispring.framework.context.MyApplicationContext;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Auther: williamdream
 * @Date: 2019/10/10 14:28
 * @Description:
 */
//@Slf4j
public class MyDispatchServlet extends HttpServlet {

    private final String CONTEXT_CONFIG_LOCATION = "contextConfigLocation";

    private MyApplicationContext context;

    private List<MyHandlerMapping> handlerMappings;

    private Map<MyHandlerMapping,MyHandlerAdapter> handlerAdapters = new HashMap<MyHandlerMapping,MyHandlerAdapter>();

    private List<MyViewResolver> viewResolvers;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        try {
            this.doDispatch(req,res);
        } catch (Exception e) {
            Map<String,Object> model = new HashMap<String,Object>();
            model.put("detail",e.getCause().getMessage());
            model.put("stackTrace",e.getCause().getStackTrace());
            processDispatchResult(req,res,new MyModelAndView("500"));
            e.printStackTrace();
        }
    }

    private void doDispatch(HttpServletRequest req,HttpServletResponse res)throws Exception{
        //1.通过request请求拿到URL，然后匹配一个HandlerMapping
        MyHandlerMapping handler = getHandler(req);
        if(handler == null){
//            throw Exception();  404
            processDispatchResult(req,res,new MyModelAndView("404"));
            return;
        }
        //2.准备调用前的参数
        MyHandlerAdapter ha = getHandlerAdapter(handler);
        //3.调用方法
        MyModelAndView view = ha.handle(req,res,handler);

        processDispatchResult(req,res,view);

    }




    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        //1.初始化ApplicationContext
        context = new MyApplicationContext(config.getInitParameter(CONTEXT_CONFIG_LOCATION));
        //2.初始化Spring MVC 九大组件
        initStrategies(context);
    }

    //初始化策略
    protected void initStrategies(MyApplicationContext context) {
        //多文件上传的组件
        initMultipartResolver(context);
        //初始化本地语言环境
        initLocaleResolver(context);
        //初始化模板处理器
        initThemeResolver(context);
        //handlerMapping
        initHandlerMappings(context);
        //初始化参数适配器
        initHandlerAdapters(context);
        //初始化异常拦截器
        initHandlerExceptionResolvers(context);
        //初始化视图预处理器
        initRequestToViewNameTranslator(context);
        //初始化视图转换器
        initViewResolvers(context);
        //解决重定向传参问题
        initFlashMapManager(context);
    }

    private void initFlashMapManager(MyApplicationContext context) {
    }

    //视图转换器
    private void initViewResolvers(MyApplicationContext context) {
        //
        String templateRoot = context.getConfig().getProperty("templateRoot");
        String templateRootPath = this.getClass().getClassLoader().getResource(templateRoot).getFile();
        File templateRootDir = new File(templateRootPath);
        for (File template : templateRootDir.listFiles()){
            this.viewResolvers.add(new MyViewResolver(templateRoot));
        }


    }

    private void initRequestToViewNameTranslator(MyApplicationContext context) {
    }

    private void initHandlerExceptionResolvers(MyApplicationContext context) {
    }




    private void initHandlerMappings(MyApplicationContext context) {
        String[] beanNames = context.getBeanDefinitionNames();
        try {
            for (String beanName : beanNames){
                Object controller = context.getBean(beanName);
                Class<?> clazz = controller.getClass();
                if(!clazz.isAnnotationPresent(MyController.class)){
                    continue;
                }
                String baseUrl = "";
                //获取Controller上的MyRequestMapping中的url配置
                if(clazz.isAnnotationPresent(MyRequestMapping.class)){
                    MyRequestMapping requestMapping = clazz.getAnnotation(MyRequestMapping.class);
                    baseUrl = requestMapping.value();
                }

                Method[] methods = clazz.getMethods();
                for (Method method : methods){
                    if(!method.isAnnotationPresent(MyRequestMapping.class))
                        continue;
                    MyRequestMapping requestMapping = method.getAnnotation(MyRequestMapping.class);
                    String reqex = ("/" + baseUrl + "/"+requestMapping.value()).replaceAll("\\*",".*").replaceAll("/+","/");
                    Pattern pattern = Pattern.compile(reqex);
                    handlerMappings.add(new MyHandlerMapping(controller,method,pattern));

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initHandlerAdapters(MyApplicationContext context) {
        //把一个request请求找到适配的一个handler
        //将HandlerMapping与HandlerAdapter建立对应关系
        for (MyHandlerMapping handlerMapping : this.handlerMappings){
            this.handlerAdapters.put(handlerMapping,new MyHandlerAdapter());
        }
    }

    private void initThemeResolver(MyApplicationContext context) {
    }

    private void initLocaleResolver(MyApplicationContext context) {
    }

    private void initMultipartResolver(MyApplicationContext context) {
    }

    private MyHandlerMapping getHandler(HttpServletRequest req) throws Exception{
        if(this.handlerMappings.isEmpty())
            return null;

        String url = req.getRequestURI();
        String contextPath = req.getContextPath();
        url = url.replace(contextPath, "").replaceAll("/+", "/");
        for (MyHandlerMapping handler : this.handlerMappings) {
            try{
                Matcher matcher = handler.getPattern().matcher(url);
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

    private MyHandlerAdapter getHandlerAdapter(MyHandlerMapping handler) {
        if(this.handlerAdapters.isEmpty()){
            return null;
        }
        MyHandlerAdapter ha = this.handlerAdapters.get(handler);
        if(ha.supports(ha)){
            return ha;
        }
        return null;
    }

    //将ModelAndView变成一个html、freemark、OutputStream、json等
    private void processDispatchResult(HttpServletRequest req, HttpServletResponse res, MyModelAndView modelAndView)throws Exception {
        if(modelAndView == null){
            return;
        }

        if(this.viewResolvers.isEmpty()){
            return;
        }
        for (MyViewResolver viewResolver : this.viewResolvers){
            MyView view = viewResolver.resolverViewName(modelAndView.getViewName(),null);
            view.render(modelAndView.getModel(),req,res);
            return;
        }
    }

}
