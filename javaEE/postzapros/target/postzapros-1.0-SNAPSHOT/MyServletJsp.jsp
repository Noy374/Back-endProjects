<%@ page import="java.util.Date" %><%--
  Created by IntelliJ IDEA.
  User: svary
  Date: 21.06.2023
  Time: 0:55
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>MyJsp</title>
</head>
<body>
   <h2>Testing JSP</h2>
   <p>
       <%

               out.print("<p>"+ new Date()+"</p>");
               String name=request.getParameter("name");
               out.print("Hello "+name.toUpperCase());
       %>
  </p>
</body>
</html>
