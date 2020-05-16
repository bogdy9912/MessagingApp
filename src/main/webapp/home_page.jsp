<%@ page import="models.Message" %>
<%@ page import="java.util.List" %>
<%@ page import="models.Group" %><%--
  Created by IntelliJ IDEA.
  User: HP
  Date: 5/15/2020
  Time: 7:45 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>

<head>

    <meta name="viewport" content="width=device-width, initial-scale=1">
    <style>
        *{
            box-sizing: border-box;
        }
        .column {
            float: left;
            padding: 10px;
            height: 500px; /* Should be removed. Only for demonstration */
        }
        .left{
            width: 15%;
        }
        .right{
            width: 45%;
        }
        .middle{
            width: 1%;
        }
        .row:after {
            content: "";
            display: table;
            clear: both;
        }

    </style>>

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


<div class="row">
    <div class="column left" style="background-color:#bbb;">
        <p align="left">
            <%

                if (request.getAttribute("groups") != null) {
                    List<Group> groupsList = (List<Group>) request.getAttribute("groups");
                    for (Group group : groupsList) {

                       // out.print(group.toString());
                        String button = "<button onclick=\"myFunction(this.id)\""+" , " +
                                "id=" + group.getId() + ">" + group.getName() + "</button>";
                        String form = "<form method= 'post' action = '/home_page'>" +
                                "<input type='hidden' name='groupId' value='"+
                                group.getId() + "'><br>\n" +
                                " <input type='hidden' name='userName' value='"+
                                request.getAttribute("name")+"'><br>\n"+
                                "<input type=submit value = "+ group.getName() + ">" +
                                "</form>";
                        out.print(form);
                        out.print("<br>");
                    }
                }
            %>
        </p>

    </div>
    <div class="column middle"  style = "backgroud-color:#abb;">
        <p></p>
    </div>

    <div class="column right" style="background-color:#bbb;">

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


    </div>
</div>





</body>
</html>
