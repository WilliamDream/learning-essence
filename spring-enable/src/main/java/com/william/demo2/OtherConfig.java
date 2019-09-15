package com.william.demo2;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: WilliamDream
 * @Description:
 * @Date: 2019/9/15 15:43
 */

@Configuration
public class OtherConfig {

    @Bean
    public OtherBean otherBean(){
        return new OtherBean();
    }

}
