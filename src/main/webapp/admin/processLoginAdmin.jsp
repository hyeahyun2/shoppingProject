<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../resources/DB/connDB.jsp" %>

<%
request.setCharacterEncoding("utf-8");

String id = request.getParameter("id");
String password = request.getParameter("password");

String sql = "select * from p_admin where id = ?  and password = ?";
pstmt = conn.prepareStatement(sql);
pstmt.setString(1, id);
pstmt.setString(2, password);
rs = pstmt.executeQuery();
if(rs.next()){ // 로그인 처리
	// 세션 굽기
	session.setAttribute("sessionAdminId", rs.getString("id"));
	session.setAttribute("sessionAdminName", rs.getString("name"));
	session.setAttribute("sessionAdminDay", rs.getString("lately_login_day"));
	
	// 최근 로그인한 날짜를 변경
	sql = "update p_admin set lately_login_day = now() where id = ? and password = ?";
	pstmt = conn.prepareStatement(sql);
	pstmt.setString(1, id);
	pstmt.setString(2, password);
	pstmt.executeUpdate();
	
	// 인덱스 페이지로 이동
	response.sendRedirect("index.jsp");
}
else { // 로그인 실패한 경우
	response.sendRedirect("loginAdmin.jsp?error=1");
}
%>
