package com.william.demo4;

import com.william.demo3.Milk;
import com.william.demo3.MilkImportSelector;
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
@Import(MilkImportBeanDefinitionRegistrar.class)
public @interface EnableNewMilk {

    Milk.Type type();

    String name();


}
