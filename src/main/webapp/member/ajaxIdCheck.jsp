<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql"%>
<%@ include file="../resources/DB/connDB.jsp" %>
<%
request.setCharacterEncoding("utf-8");
String id = request.getParameter("id");
%>
<sql:setDataSource var="dataSource"
	url="jdbc:mariadb://localhost:3306/studyjdbc"
	driver="org.mariadb.jdbc.Driver"
	user="root" password="6995"/>
<sql:query dataSource="${dataSource}" var="resultSet">
		select count(*) as cnt from p_member where id = ?
		<sql:param value="<%= id %>"/>
</sql:query>
{"result":
	<c:forEach var="row" items="${resultSet.rows}">
		<c:choose>
			<c:when test="${row.cnt == 1}">
				"true"
			</c:when>
			<c:otherwise>
				"false"
			</c:otherwise>
		</c:choose>
	</c:forEach>
}