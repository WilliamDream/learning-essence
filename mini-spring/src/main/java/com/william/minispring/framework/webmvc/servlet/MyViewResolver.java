package com.william.minispring.framework.webmvc.servlet;

import java.io.File;
import java.util.Locale;

/**
 * @Auther: williamdream
 * @Date: 2019/10/7 17:56
 * @Description:
 */
public class MyViewResolver {

    private final String DEFAULT_TEMPLATE_SUFFIX = ".html";
    private File templateRootDir;

    public MyViewResolver(String templateRoot){
        String templateRootPath = this.getClass().getClassLoader().getResource(templateRoot).getFile();
        templateRootDir = new File(templateRootPath);
    }

    public MyView resolverViewName(String viewName, Locale locale) throws Exception{
        if(viewName==null||"".equals(viewName.trim())){
            return null;
        }
        viewName = viewName.endsWith(DEFAULT_TEMPLATE_SUFFIX)?viewName:(viewName+DEFAULT_TEMPLATE_SUFFIX);
        File templateFile =new File((templateRootDir.getPath()+"/"+viewName).replaceAll("/+",""));

        return new MyView(templateFile);
    }

    public File getTemplateRootDir() {
        return templateRootDir;
    }

}
