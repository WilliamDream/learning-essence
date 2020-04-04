package com.william.tomcat.servlet;


import com.william.tomcat.http.MyRequest;
import com.william.tomcat.http.MyResponse;
import com.william.tomcat.http.MyServlet;

public class UserServlet extends MyServlet{

    @Override
    protected void doPost(MyRequest request, MyResponse response) {
        this.doPost(request,response);
    }

    @Override
    protected void doGet(MyRequest request, MyResponse response) {
        try {
            response.write("This is First Serlvet");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
