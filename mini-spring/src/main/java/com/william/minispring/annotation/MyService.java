package com.william.minispring.annotation;

import java.lang.annotation.*;

/**
 * @Author: WilliamDream
 * @Description:
 * @Date: 2019/7/14 22:04
 */

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface MyService {

    String value() default "";

}
