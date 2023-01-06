<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ include file="../resources/DB/connDB.jsp" %>

<%
request.setCharacterEncoding("utf-8");

String id = (String)session.getAttribute("sessionAdminId");
String password = request.getParameter("password");
String name = request.getParameter("name");

String sql = "update p_admin set password=?, name=? where id=?";
int upd = -1;
try {
	pstmt = conn.prepareStatement(sql);
	pstmt.setString(1, password);
	pstmt.setString(2, name);
	pstmt.setString(3, id);
	upd = pstmt.executeUpdate(); // 1 저장
} catch(Exception e) {
	e.printStackTrace();
} finally {
	try {
		if(pstmt != null) pstmt.close();
		if(conn != null) conn.close();
	} catch(Exception e) {
		e.printStackTrace();
	}
}

if(upd > 0){ // db 저장 성공
	session.setAttribute("sessionAdminName", name);
	response.sendRedirect("./index.jsp");
}
else {
	response.sendRedirect("./updateAdmin.jsp");
}
%>
