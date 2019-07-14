package com.william.minispringmvc.annotation;

import java.lang.annotation.*;

/**
 * @Author: WilliamDream
 * @Description:
 * @Date: 2019/7/14 22:03
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface MyController {

    String value() default "";

}
