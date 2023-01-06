<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
//session.invalidate();
session.removeAttribute("sessionAdminName");
session.removeAttribute("sessionAdminId");
session.removeAttribute("sessionAdminDay");
response.sendRedirect("loginAdmin.jsp");
%>                      