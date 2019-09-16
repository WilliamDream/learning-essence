package com.william.demo3;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * @Author: WilliamDream
 * @Description:
 * @Date: 2019/9/13 14:24
 */

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Import(MilkImportSelector.class)
public @interface EnableMilk {

    Milk.Type type();

    String name();


}
