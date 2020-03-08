package com.william.minispring.framework.webmvc.servlet;

import java.util.Map;

/**
 * @Auther: williamdream
 * @Date: 2019/10/10 15:37
 * @Description:
 */
public class MyModelAndView {

    private String viewName;

    private Map<String,?> model;

    public MyModelAndView(String viewName) {
        this.viewName = viewName;
    }

    public MyModelAndView(String viewName, Map<String, ?> model) {
        this.viewName = viewName;
        this.model = model;
    }

    public String getViewName() {
        return viewName;
    }

    public Map<String, ?> getModel() {
        return model;
    }
}
