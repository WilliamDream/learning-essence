package com.william.tomcat.http;


import java.io.OutputStream;

public class MyResponse {

    private OutputStream out;
    public MyResponse(OutputStream outputStream){
        this.out = outputStream;
    }

    public void write(String content)throws Exception{
        StringBuilder sb = new StringBuilder();
        sb.append("HTTP/1.1 200 OK\n");
        sb.append("Content-Type:text/html;\n");
        sb.append("\r\n");
        sb.append(content);
        out.write(sb.toString().getBytes());
    }

}
