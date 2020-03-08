package com.william.minispring.framework.annotation;

import java.lang.annotation.*;

/**
 * @Author: WilliamDream
 * @Description:
 * @Date: 2019/7/14 22:31
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface MyAutoWired {

    String value() default "";

}
