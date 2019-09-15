package com.william.demo2;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * @Author: WilliamDream
 * @Description:
 * @Date: 2019/9/15 15:39
 */


@Import(OtherConfig.class)   //导入其他依赖的bean
@Configuration
public class BeanConfig {

    @Bean
    public DefaultBean defaultBean(){
        return new DefaultBean();
    }

}
