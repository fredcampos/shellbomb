package com.shellshock.servlet;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FilenameUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.List;

public class UploadServlet extends ShellshockServlet {
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        try {
            List<FileItem> items = new ServletFileUpload(new DiskFileItemFactory()).parseRequest(request);
            String fileName = null;
            String filePath = null;
            String path = null;
            InputStream fileContent = null;
            for (FileItem item : items) {
                System.out.println(item.toString());
                if (!item.isFormField()) {
                    fileContent = item.getInputStream();
                    fileName = FilenameUtils.getName(item.getName());
                }else if (item.isFormField() && item.getFieldName() != null
                        && item.getFieldName().equals("filePath")){
                    path = item.getString();
                }
            }

            filePath = path + File.separator + fileName;


            OutputStream dest = new FileOutputStream(new
                    File(filePath));
            int read = 0;
            byte[] bytes = new byte[1024];
            while ((read = fileContent.read(bytes)) != -1) {
                dest.write(bytes, 0, read);
            }
            fileContent.close();
            dest.close();
            System.out.println("done writing file " + filePath);


        } catch (FileUploadException e) {
            e.printStackTrace();
            throw new ServletException("Cannot parse multipart request.", e);
        }

        request.getRequestDispatcher(BASE + "index.jsp").forward(request, response);

    }


}
