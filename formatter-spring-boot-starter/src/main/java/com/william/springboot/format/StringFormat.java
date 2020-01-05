package com.william.springboot.format;

import java.util.Objects;

/**
 * @Author: WilliamDream
 * @Description:
 * @Date: 2019/11/24 20:58
 */
public class StringFormat implements Formatter{

    @Override
    public <T> String format(T obj) {
        return "String Format:" + Objects.toString(obj);
    }
}
