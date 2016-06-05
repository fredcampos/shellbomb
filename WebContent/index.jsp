<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<link>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<script src="${request.contextPath}/res/js/bootstrap.min.js"></script>
<link rel="stylesheet" href="${request.contextPath}/res/css/bootstrap.min.css"/>
<link rel="stylesheet" href="${request.contextPath}/res/css/bootstrap-theme.min.css"/>

<title>Shellshock</title>
</head>
<body>
<nav class="navbar navbar-inverse navbar-fixed-top">
    <div class="container">
        <div class="navbar-header">

            <a class="navbar-brand" href="#">Shellshock!</a>
        </div>

    </div>
</nav>

<div class="container">
    <div class="starter-template">
        <form action="/command" enctype="multipart/form-data" name="uploadForm">
            <h3>Execute command</h3>
            Enter command to execute <br/>
            <input type="text" name="cmd" required />
            <input type="submit" />
        </form>


        <form action="/upload" enctype="multipart/form-data" name="uploadForm">
            <h3>Upload file</h3>
            Select file to upload <br/>
            <input type="file" name="file" required />
            <br/>
            Path to upload file <br/>
            <input name="filePath" type="text" required />
            <br/>
            <input type="submit" />
        </form>

        <c:if test="${not empty commandOutput}">
            <h3>Last command output </h3>
    <pre>
            ${commandOutput}
    </pre>
        </c:if>
        <c:if test="${not empty commandError}">
            <h3>Last command error </h3>
    <pre>
            ${commandError}
    </pre>
        </c:if>


    </div>

</div><!-- /.container -->














</body>
</html>