<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql"%>

<%
request.setCharacterEncoding("utf-8");

String id = request.getParameter("id");
String password = request.getParameter("password");
%>

<sql:setDataSource var="dataSource"
	url="jdbc:mariadb://localhost:3306/studyjdbc"
	driver="org.mariadb.jdbc.Driver"
	user="root" password="6995"/>

<sql:query dataSource="${dataSource}" var="resultSet">
	select * from p_member where id=? and password=?
	<sql:param value="<%= id %>"/>
	<sql:param value="<%= password %>"/>
</sql:query>

<c:forEach var="row" items="${resultSet.rows}">
	<%
	session.setAttribute("sessionId", id);
	%>
	<c:redirect url="resultMember.jsp?msg=2"/>
</c:forEach>
<c:redirect url="loginMember.jsp?error=1"/>