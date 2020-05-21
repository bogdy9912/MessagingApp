<%@ page import="models.Message" %>
<%@ page import="java.util.List" %>
<%@ page import="models.Group" %>
<%@ page import="repositories.GroupRepository" %><%--
  Created by IntelliJ IDEA.
  User: HP
  Date: 5/15/2020
  Time: 7:45 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>

<head>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
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
            height: 400px;
        }

        /* Style the buttons inside the tab */
        .tab button {
           /* overflow: auto;*/
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
            height: 370px;
        }
        .fa {
            font-size: 50px;
            cursor: pointer;
            user-select: none;
        }

        .fa:hover {
            color: darkblue;
        }
        .mes{
            width: 65%;
        }

        .forms{
            width: 50%;
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
        out.print("Grupul: " + GroupRepository.findById((String) request.getAttribute("groupId")).getName());

    }
    %>
</p>


<div class="tab" id="aicitrebuie">

        <p align="left">
            <%

                if (request.getAttribute("groups") != null) {
                    List<Group> groupsList = (List<Group>) request.getAttribute("groups");
                    for (Group group : groupsList) {

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

    <form method="post" action = "/home_page">
        <input type="text" name="newGroup" placeholder="Enter name new group...">
        <input type='hidden' name='groupId' value='<%=request.getAttribute("groupId")%>'>
        <input type='hidden' name='userName' value='<%=request.getAttribute("name")%>'>
        <input align="right" type="submit" value="Create group">

    </form>


    </div>
    <div class="column middle"  style = "backgroud-color:#abb;">
        <p></p>
    </div>



<div id="Tokyo" class="tabcontent">
        <p align="center" >
            <%

                if (request.getAttribute("messages") != null) {
                    List<Message> messagesList = (List<Message>) request.getAttribute("messages");
                    for (Message message : messagesList) {

                        String deleteMessage = "<hr>" +
                                "<form class='forms' method= 'post' action = '/home_page'>" +
                                "<input type='hidden' name='messageDelete' value='"+
                                 message.getId() +"'>"+
                                " <input type='hidden' name='userName' value='"+
                                request.getAttribute("name")+"'><br>\n"+
                                "<input type='hidden' name='groupId' value='"+
                                message.getIdGroup() + "'><br>\n" +
                                "<input type='submit' name='delete' value='Delete'>" +
                                "</form>";
                        out.print(deleteMessage);
                        out.print(message.toString());
                        String reactAndReplyMessage = "<form class='forms' method= 'post' action = '/home_page'>" +
                                "<input type='submit' name='react' value='React'>" +
                                "<input type='text' name='inputReply' placeholder='Insert reply...'>"+
                                "<input type='submit' name='reply' value='Reply'>" +
                                "<input type='hidden' name='messageReplyAndReact' value='"+
                                message.getId() +"'>"+
                                " <input type='hidden' name='userName' value='"+
                                request.getAttribute("name")+"'><br>\n"+
                                "<input type='hidden' name='groupId' value='"+
                                message.getIdGroup() + "'><br>\n" +
                                "</form>";
                        out.print(reactAndReplyMessage);
                        out.print("<br>");
                    }
                    out.print("<br>");
                    out.print("<br>");
                }
            %>
        </p>
</div>


    <form method="post" action = "/home_page">
        <input  class="mes" type="text" name="textMessage" placeholder="Enter message...">
        <input type='hidden' name='groupId' value='<%=request.getAttribute("groupId")%>'>
         <input type='hidden' name='userName' value='<%=request.getAttribute("name")%>'>
        <input type="submit" value="Send">

    </form >


<form method="post" action = "/home_page">
    <input type="text" name="newMember" placeholder="Enter new member">
    <input type='hidden' name='groupId' value='<%=request.getAttribute("groupId")%>'>
    <input type='hidden' name='userName' value='<%=request.getAttribute("name")%>'>
    <input type="submit" value="Add people in current group">
</form>

<form method="post", action="/home_page">
    <input type="text" name="newFriend" placeholder="Enter new friend name...">
    <input type='hidden' name='groupId' value='<%=request.getAttribute("groupId")%>'>
    <input type='hidden' name='userName' value='<%=request.getAttribute("name")%>'>
    <input type="submit" value=" ADD Friend">

</form>

<form method="post", action="/home_page">
    <input type="text" name="newBestFriend" placeholder="Enter new bestfriend name...">
    <input type='hidden' name='groupId' value='<%=request.getAttribute("groupId")%>'>
    <input type='hidden' name='userName' value='<%=request.getAttribute("name")%>'>
    <input type="submit" name="addBestFriend" value=" ADD BestFriend">
    <input type="submit" name="removeBestFriend" value=" Remove BestFriend">

</form>

<p><% out.print("Userul curent a trimis " + request.getAttribute("nrMessages")+" in total"); %></p>
<p><% out.print("BestFriends are: " + request.getAttribute("ListOfBestFriends"));%></p>
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

        var out = document.getElementById("aicitrebuie");
// allow 1px inaccuracy by adding 1
        var isScrolledToBottom = out.scrollHeight - out.clientHeight <= out.scrollTop + 1;
    }

    // Get the element with id="defaultOpen" and click on it
    document.getElementById("defaultOpen").click();


</script>



</body>
</html>
