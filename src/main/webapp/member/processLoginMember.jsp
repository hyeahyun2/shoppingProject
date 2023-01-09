<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql"%>
<%@ include file="../resources/DB/connDB.jsp" %>
<%@ page import="java.util.*" %>
<%@ page import="market.dao.CartDao" %>

<%
request.setCharacterEncoding("utf-8");

String id = request.getParameter("id");
String password = request.getParameter("password");
String sql = "select * from p_member where id = ? and password = ?";
pstmt = conn.prepareStatement(sql);
pstmt.setString(1, id);
pstmt.setString(2, password);
rs = pstmt.executeQuery();
if(rs.next()){
	session.setAttribute("sessionId", id);
	session.setAttribute("sessionName", rs.getString("name"));
	
	CartDao cartDao = new CartDao();
	cartDao.updateCartBylogin(session);
	response.sendRedirect("resultMember.jsp?msg=2");
	
}
else {
	response.sendRedirect("loginMember.jsp?error=1");
}
%>
