<%--
  Created by IntelliJ IDEA.
  User: HP
  Date: 5/15/2020
  Time: 6:40 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Register</title>
</head>
<body>
<h3 align="center">Register here</h3>

    <form align="center" method="post" action="/register">
        <label for="name">Name: </label> <br>
        <input type="text" id="name" name="name"><br>
        <label for="password">Password: </label> <br>
        <input type="password" id="password" name="password"><br>
        <input type="submit" value="Login">

</form>


</body>
</html>
