<%@ page import="models.Message" %>
<%@ page import="java.util.List" %><%--
  Created by IntelliJ IDEA.
  User: HP
  Date: 5/15/2020
  Time: 7:45 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>HomePage</title>
</head>
<body>


<p align="center">
    <%
       /* if (request.getAttribute("loginError") != null)
            out.print(request.getAttribute("loginError"));*/
      /*  else */if (request.getAttribute("name") != null) {
            out.print("Salut " + request.getAttribute("name"));

            out.print("<br>");
            out.print("<br>");

        }
    %>
</p>

<p align="center">
    <%

      if (request.getAttribute("messages") != null) {
        List<Message> messagesList = (List<Message>) request.getAttribute("messages");
        for (Message message : messagesList) {
            out.print(message.toString());
            out.print("<br>");
        }

        out.print("<br>");
        out.print("<br>");
    }
    %>
</p>

</body>
</html>
