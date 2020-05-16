<%--
  Created by IntelliJ IDEA.
  User: HP
  Date: 5/15/2020
  Time: 3:44 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>

<h3 align="center">WELCOME</h3>


<form align="center" method="post" action="/login">
    <label for="name">Name: </label> <br>
    <input type="text" id="name" name="name"><br>
    <label for="password">Password: </label> <br>
   <input type="password" id="password" name="password"><br>
    <input type="submit" value="Login">

 </form>

<form align = "center" method="get" action="/register">
    <input type="submit" value="Register" >

</form>


 <%--
 <p align="center">
     <%
         if (request.getAttribute("mesaj") != null) {
             String line = (String) request.getAttribute("mesaj");
             out.print(line);
         }
     %>
 </p>
 --%>s

</body>
</html>

