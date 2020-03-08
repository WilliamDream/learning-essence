package com.william.minispring.framework.aop;

import com.william.minispring.framework.aop.support.MyAdvisedSupport;

/**
 * @Auther: williamdream
 * @Date: 2010/10/11 15:07
 * @Description:
 */
public class MyCglibAopProxy implements MyAopProxy{

    public MyAdvisedSupport advised;

    public MyCglibAopProxy(MyAdvisedSupport advised) {
        this.advised = advised;
    }

    @Override
    public Object getProxy() {
        return null;
    }

    @Override
    public Object getProxy(ClassLoader classLoader) {
        return null;
    }
}
