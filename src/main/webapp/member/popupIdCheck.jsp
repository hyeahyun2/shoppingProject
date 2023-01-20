<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>아이디 중복 확인</title>
</head>
<body>
	<%
	request.setCharacterEncoding("utf-8");
	String id = request.getParameter("id");
	%>
	<%@ include file="../resources/DB/connDB.jsp" %>
	<sql:setDataSource var="dataSource"
	url="jdbc:mariadb://localhost:3306/studyjdbc"
	driver="org.mariadb.jdbc.Driver"
	user="root" password="6995"/>
	<sql:query dataSource="${dataSource}" var="resultSet">
		select count(*) as cnt from p_member where id = ?
		<sql:param value="<%= id %>"/>
	</sql:query>
	<c:forEach var="row" items="${resultSet.rows}">
		<c:choose>
			<c:when test="${row.cnt == 1}">
				동일한 아이디가 있습니다.
			</c:when>
			<c:otherwise>
				동일한 아이디가 없습니다.
			</c:otherwise>
		</c:choose>
	</c:forEach>
</body>
</html>