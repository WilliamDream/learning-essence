package com.william.springboot.autoconfig;

import com.william.springboot.FormatTemplate;
import com.william.springboot.format.Formatter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;



/**
   * @Description : 这个是导入AutoConfiguration配置类
   * 因为对外提供的FormatTemplate类进行使用，所以需要将其注入到spring容器中。
   */
@Import(FormatAutoConfiguration.class)
@Configuration
public class MyAutoConfiguration {

    @Bean
    public FormatTemplate formatTemplate(Formatter formatter){
        return new FormatTemplate(formatter);
    }


}
