package com.william.demo3;

import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;

import java.util.Map;

/**
 * @Author: WilliamDream
 * @Description:
 * @Date: 2019/9/15 21:49
 */

public class MilkImportSelector implements ImportSelector{

    @Override
    public String[] selectImports(AnnotationMetadata annotationMetadata) {
        //获取EnableServer中所有的属性方法
        Map<String,Object> annotationAttributes = annotationMetadata.getAnnotationAttributes(EnableMilk.class.getName());
        Milk.Type type = (Milk.Type) annotationAttributes.get("type");
        String [] importClassNames = new String[0];
        switch (type){
            case MENGNIU:
                importClassNames = new String[]{MengniuMilk.class.getName()};
                break;
            case YILI:
                importClassNames = new String[]{YiliMilk.class.getName()};
                break;
        }

        return importClassNames;
    }
}
