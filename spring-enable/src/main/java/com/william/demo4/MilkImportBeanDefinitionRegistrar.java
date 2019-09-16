package com.william.demo4;

import com.william.demo3.MilkImportSelector;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.util.StringUtils;

/**
 * @Author: WilliamDream
 * @Description:
 * @Date: 2019/9/15 25:19
 */
public class MilkImportBeanDefinitionRegistrar implements ImportBeanDefinitionRegistrar{

    @Override
    public void registerBeanDefinitions(AnnotationMetadata annotationMetadata, BeanDefinitionRegistry beanDefinitionRegistry) {
        ImportSelector importSelector = new MilkImportSelector();
//        String[] slecatedClassNames =  importSelector.selectImports(annotationMetadata);
        RootBeanDefinition beanDefinition=new RootBeanDefinition(importSelector.getClass());
        String beanName= StringUtils.uncapitalize(importSelector.getClass().getSimpleName());
        beanDefinitionRegistry.registerBeanDefinition(beanName,beanDefinition);

    }
}
