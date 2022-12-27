<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<% 
	session.invalidate(); // 세션값 모두 삭제
	response.sendRedirect("addProduct.jsp");
%>