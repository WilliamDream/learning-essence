package com.william.springboot.autoconfig;

import com.william.springboot.format.Formatter;
import com.william.springboot.format.JsonFormat;
import com.william.springboot.format.StringFormat;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class FormatAutoConfiguration {


    @Primary
    @Bean
    @ConditionalOnMissingClass("com.alibaba.fastjson.JSON")
    public Formatter stringFormat(){
        return new StringFormat();
    }

    @Bean
    @ConditionalOnClass(name="com.alibaba.fastjson.JSON")
    public Formatter jsonFormat(){
        return new JsonFormat();
    }

}
