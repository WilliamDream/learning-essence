package com.william.demo5;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: WilliamDream
 * @Description:
 * @Date: 2019/9/16 22:39
 */
@Configuration
public class DataSourceConfiguration {

    @Bean
    public DataSource dataSource(){
        return new DataSource();
    }

}
