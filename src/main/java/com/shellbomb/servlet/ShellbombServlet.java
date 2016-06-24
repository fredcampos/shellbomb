package com.shellbomb.servlet;

import com.shellbomb.DirectoryTraverse;
import com.shellbomb.FileHolder;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.List;


public class ShellbombServlet extends HttpServlet {

    protected final static String BASE = "/WEB-INF";
    protected final static String INDEX = "/index.jsp";
    protected String context;

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        String dir = getCurrentDir(request);
        List<FileHolder> ls = DirectoryTraverse.instance().listFiles(dir, context);
        request.setAttribute("currentDir", dir);
        request.setAttribute("ls", ls);
        index(request, response);
    }

    public void init(final ServletConfig config) {
        this.context = config.getServletContext().getContextPath();
    }

    public void index(HttpServletRequest request,  HttpServletResponse response)
            throws IOException, ServletException{
        setLocalContext(request);
        request.getRequestDispatcher(INDEX).forward(request, response);
    }

    private String getCurrentDir(HttpServletRequest request){
        String path = null;
        if (request.getParameter("path") != null){
            path = request.getParameter("path");
        }

        if (path == null) {
            try {
                path = new File(".").getCanonicalPath();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return path;
    }

    protected void setLocalContext(HttpServletRequest request){
        request.setAttribute("context", context);
    }


}