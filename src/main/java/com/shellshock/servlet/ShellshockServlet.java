package com.shellshock.servlet;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class ShellshockServlet extends HttpServlet {

    protected final static String BASE = "/WEB-INF";
    protected final static String INDEX = "/index.jsp";
    protected String context;

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        setLocalContext(request);
        request.getRequestDispatcher(INDEX).forward(request, response);

    }

    public void init(final ServletConfig config) {
        this.context = config.getServletContext().getContextPath();

    }


    protected void setLocalContext(HttpServletRequest request){
        request.setAttribute("context", context);
    }


}