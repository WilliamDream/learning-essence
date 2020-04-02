package com.william.tomcat;

import com.william.tomcat.http.MyServlet;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.ServerSocket;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * J2EE标准
 * Servlet
 * Request
 * Response
 *
 * 1.配置监听端口（8080），等待连接，ServerSocket
 * 2.配置web.xml ，自己编写Servlet继承HttpServlet
 * 3.读取配置文件，将url-pattern 和Servlet建立关系
 * 4.接收http请求，遵循http协议
 * 5.从协议内容中获取url，把相应的servlet用反射进行实例化
 * 6.调用实例化对象的service()方法，执行具体的逻辑doGet或doPost方法
 * 7.Request ---InputStream  Response --- OutputStream
 *
 */
public class MyTomcat {

    private ServerSocket serverSocket;

    private Map<String,MyServlet> servletMapping = new HashMap<>();

    private Properties properties = new Properties();

    private void init(){
        try {
            String web_inf = this.getClass().getResource("/").getPath();
            FileInputStream fileInputStream = new FileInputStream(web_inf+"web.properties");
            properties.load(fileInputStream);
            for (Object k : properties.keySet()){
                String key =  k.toString();
                if(key.endsWith(".url")){
                    
                }


            }

        } catch (Exception e) {
            e.printStackTrace();
        }


    }


}
