package com.william.demo5;

import com.william.demo3.Milk;
import com.william.demo4.MilkImportBeanDefinitionRegistrar;
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
@Import(DataSourceConfiguration.class)
public @interface EnableDataSource {



}
