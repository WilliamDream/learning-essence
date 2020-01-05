package com.william.springboot.format;


import com.alibaba.fastjson.JSON;

public class JsonFormat implements Formatter{

    @Override
    public <T> String format(T obj) {
        return "JSON format:"+ JSON.toJSONString(obj);
    }
}
