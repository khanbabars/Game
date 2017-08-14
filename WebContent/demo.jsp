<%@page import="java.io.Console"%>
<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@page import = "java.sql.DriverManager" %>
     <%@page import = "java.sql.Connection" %>
     <%@page import = "org.datasource.GameSession" %>
     <%@page import = "org.aq.Publisher" %>
     <%@page import ="org.aq.AqTopicConn.State" %>
     
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Game Server</title>
</head>
<body>

Game Area	<br>



<% Publisher publisher = new Publisher(State.Status);%>

<% java.util.Date date  = new java.util.Date(); %>
<div> <%= date %></div>

	


	
<form action="BasicModel" method="post">



bet: <input type="text" name=bet value =0 ><br>
win: <input type="text" name=win  value =0 ><br>
balance: <input type="text" name=balance  value =0 > <br>
<% 

String bet = (request.getParameter("bet")==null)? "0" : request.getParameter("bet");
String win = (request.getParameter("win")==null)? "0" : request.getParameter("win");
String balance = (request.getParameter("balance")==null)? "0" : request.getParameter("balance");

%>


 <%
//  GameSession.getGameSession(Integer.parseInt(bet),Integer.parseInt(win),Integer.parseInt(balance));
 
 
 publisher.getGameSessionPublisher(Integer.parseInt(bet),Integer.parseInt(win),Integer.parseInt(balance));
 %> 
<input type="submit" value="Play">



</form>

</body>
</html>