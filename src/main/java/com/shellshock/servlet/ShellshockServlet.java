package com.shellshock.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class ShellshockServlet extends HttpServlet {

    protected final static String BASE = "/WEB-INF";
    protected final static String INDEX = BASE + "/index.jsp";


    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        request.getRequestDispatcher(INDEX).forward(request, response);

    }


}