package com.shellshock.servlet;

import com.shellshock.DirectoryTraverse;
import com.shellshock.FileHolder;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.List;


public class ShellshockServlet extends HttpServlet {

    protected final static String BASE = "/WEB-INF";
    protected final static String INDEX = "/index.jsp";
    protected String context;

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        setLocalContext(request);
        String dir = getCurrentDir(request);
        List<FileHolder> ls = DirectoryTraverse.instance().listFiles(dir);
        request.setAttribute("currentDir", dir);
        request.setAttribute("ls", ls);
        request.getRequestDispatcher(INDEX).forward(request, response);

    }

    public void init(final ServletConfig config) {
        this.context = config.getServletContext().getContextPath();

    }

    private String getCurrentDir(HttpServletRequest request){
        String path = null;
        if (request.getParameter("path") != null){
            path = (String)request.getParameter("path");
        }

        if (path == null) {
            try {
                path = new File(".").getCanonicalPath();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        System.out.println("path = "  + path);
        return path;
    }

    protected void setLocalContext(HttpServletRequest request){
        request.setAttribute("context", context);
    }


}