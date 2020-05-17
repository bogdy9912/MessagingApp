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

        body {font-family: "Lato", sans-serif;}



        /* Style the tab */
        .tab {
            overflow: auto;
            float: left;
            border: 1px solid #ccc;
            background-color: #f1f1f1;
            width: 30%;
            height: 600px;
        }

        /* Style the buttons inside the tab */
        .tab button {
            overflow: auto;
            display: block;
            background-color: inherit;
            color: black;
            padding: 22px 16px;
            width: 100%;
            border: none;
            outline: none;
            text-align: left;
            cursor: pointer;
            transition: 0.3s;
            font-size: 17px;
        }

        /* Change background color of buttons on hover */
        .tab button:hover {
            background-color: #ddd;
        }

        /* Create an active/current "tab button" class */
        .tab button.active {
            background-color: #ccc;
        }

        /* Style the tab content */
        .tabcontent {
            overflow: auto;
            float: left;
            padding: 0px 12px;
            border: 1px solid #ccc;
            width: 70%;
            border-left: none;
            height: 300px;
        }

    </style>

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


<div class="tab">

        <p align="left">
            <%

                if (request.getAttribute("groups") != null) {
                    List<Group> groupsList = (List<Group>) request.getAttribute("groups");
                    for (Group group : groupsList) {

                       // out.print(group.toString());
                        String button = "<button class=\"tablinks\" onclick=\"" +
                                "openCity(event, '" +"Tokyo" +"')\""+" , " +
                                "id=" + group.getId() + ">" + group.getName() + "</button>"+
                        "<input type='hidden' name='groupId' value='"+
                                group.getId() + "'><br>\n" +
                        " <input type='hidden' name='userName' value='"+
                                request.getAttribute("name")+"'><br>\n";

                        String form = "<form method= 'post' action = '/home_page'>" +
                                "<input type='hidden' name='groupId' value='"+
                                group.getId() + "'><br>\n" +
                                " <input type='hidden' name='userName' value='"+
                                request.getAttribute("name")+"'><br>\n"+
                                "<input type=submit value = "+ group.getName() + ">" +
                                "</form>";

                        String buttonForm = "<form method= 'post' action = '/home_page'>" +
                                "<input type='hidden' name='groupId' value='"+
                                group.getId() + "'><br>\n" +
                                " <input type='hidden' name='userName' value='"+
                                request.getAttribute("name")+"'><br>\n"+
                                "<button class=\"tablinks\" type='submit' onclick=\"" +
                                "openCity(event, '" +"Tokyo" +"')\""+" , " +
                                "id=" + group.getId() + ">" + group.getName() + "</button>"+
                                "</form>";

                        out.print(buttonForm);
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

<div id="Tokyo" class="tabcontent">
        <p align="center" >
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
</div>



<script>
    function openCity(evt, cityName) {
        var i, tabcontent, tablinks;
        tabcontent = document.getElementsByClassName("tabcontent");
        for (i = 0; i < tabcontent.length; i++) {
            tabcontent[i].style.display = "none";
        }
        tablinks = document.getElementsByClassName("tablinks");
        for (i = 0; i < tablinks.length; i++) {
            tablinks[i].className = tablinks[i].className.replace(" active", "");
        }
        document.getElementById(cityName).style.display = "block";
        evt.currentTarget.className += " active";
    }

    // Get the element with id="defaultOpen" and click on it
    document.getElementById("defaultOpen").click();
</script>



</body>
</html>
