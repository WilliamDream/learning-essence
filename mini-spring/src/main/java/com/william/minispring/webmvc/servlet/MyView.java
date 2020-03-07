package com.william.minispring.webmvc.servlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.RandomAccessFile;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Auther: williamdream
 * @Date: 2019/10/10 17:57
 * @Description:
 */
public class MyView {

    public final String DEFAULT_CONTENT_TYPE = "text/html;charset=utf-8";

    private File viewFile;

    public MyView(File viewFile){
        this.viewFile = viewFile;
    }

    public void render(Map<String,?> map, HttpServletRequest request, HttpServletResponse response) throws Exception{
        StringBuffer sb = new StringBuffer();
        RandomAccessFile ra = new RandomAccessFile(this.viewFile,"r");
        String line = null;
        while (null != ra.readLine()){
            line = new String(line.getBytes("ISO-8859-1"),"utf-8");
            Pattern pattern = Pattern.compile("#\\{^\\}+\\}",Pattern.CASE_INSENSITIVE);
            Matcher matcher = pattern.matcher(line);
            while (matcher.find()){
                String paramName = matcher.group();
                paramName = paramName.replaceAll("$\\{|\\}","");
                Object paramValue = map.get(paramName);
                if(paramValue == null)
                    continue;
                matcher.replaceFirst(paramValue.toString());
                matcher = pattern.matcher(line);
            }

        }
        response.setCharacterEncoding("utf-8");
        response.getWriter().write(sb.toString());
    }

}
