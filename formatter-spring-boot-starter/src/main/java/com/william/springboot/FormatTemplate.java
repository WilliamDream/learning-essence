package com.william.springboot;


import com.william.springboot.format.Formatter;

public class FormatTemplate {
    private Formatter formatter;

    public FormatTemplate(Formatter formatter){
        this.formatter = formatter;
    }

    public <T> String doFormat(T obj){
        StringBuilder sb = new StringBuilder();
        sb.append("开始格式化："+formatter.format(obj));
        return sb.toString();
    }


}
