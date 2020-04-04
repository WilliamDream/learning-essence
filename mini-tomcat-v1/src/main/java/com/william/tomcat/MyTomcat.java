package com.william.tomcat;

import com.william.tomcat.http.MyRequest;
import com.william.tomcat.http.MyResponse;
import com.william.tomcat.http.MyServlet;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
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

    private int port=8080;

    private Map<String,MyServlet> servletMapping = new HashMap<>();

    private Properties properties = new Properties();

    private void init(){
        try {
            String web_inf = this.getClass().getResource("/").getPath();
            FileInputStream fileInputStream = new FileInputStream(web_inf+ "/META-INF/web.properties");
            properties.load(fileInputStream);
            //解析配置文件
            for (Object k : properties.keySet()){
                String key =  k.toString();
                if(key.endsWith(".url")){
                    String servletName = key.replaceAll("\\.url$","");
                    String url = properties.getProperty(key);                               //获取到url
                    String className = properties.getProperty(servletName + ".className");  //获取到servlet
                    MyServlet servlet = (MyServlet) Class.forName(className).newInstance();
                    servletMapping.put(url,servlet);        //将url 与 Servlet实例一一对应

                }

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void start(){
        //1.加载配置文件
        init();

        try {
            serverSocket = new ServerSocket(this.port);
            System.out.println("==============Mytomcat 已启动，正在监听"+this.port+"端口===============");

            while(true){
                Socket client = serverSocket.accept();

                processs(client);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void processs(Socket client) {
        try {
            InputStream inputStream = client.getInputStream();
            OutputStream outputStream =  client.getOutputStream();
            // 包装request 和 response，在实际使用tomcat中不需要我们new这两个对象，因为tomcat帮我们做了
            MyRequest request = new MyRequest(inputStream);
            MyResponse response = new MyResponse(outputStream);
            String url = request.getUrl();
            if(servletMapping.containsKey(url)){
                servletMapping.get(url).service(request,response);
            }else {
                response.write("Not fund -- 404");
            }
            outputStream.flush();
            outputStream.close();
            inputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {
        MyTomcat tomcat = new MyTomcat();
        tomcat.start();

    }


}
