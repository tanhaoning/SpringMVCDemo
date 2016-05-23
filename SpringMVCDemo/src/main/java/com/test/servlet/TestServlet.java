package com.test.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by tanzepeng on 2015/6/10.
 */
public class TestServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType( "text/html;charset=utf-8" );  //设置响应页面字符编码
        PrintWriter out;
        String province = req.getParameter("province");
        System.out.println("LoginServlet.province============"+province);
        try {
            out = resp.getWriter();
            out.print("哈哈，这只是测试!!!");
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
