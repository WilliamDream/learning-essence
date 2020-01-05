package com.william.springboot.format;

/**
 * @Author: WilliamDream
 * @Description:
 */
public interface Formatter {

    <T> String format(T obj);

}
