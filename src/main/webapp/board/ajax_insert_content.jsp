<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="market.dto.RippleDto" %>
<%@ page import="market.dao.RippleDao" %>
<%@ page import="java.util.ArrayList" %>
<%@ include file="../resources/DB/connDB.jsp"%>
<%
request.setCharacterEncoding("utf-8");


RippleDao dao = RippleDao.getInstance();
RippleDto ripple = new RippleDto();
ripple.setBoardName(request.getParameter("boardName"));
ripple.setBoardNum(Integer.parseInt(request.getParameter("num")));
ripple.setMemberId((String)session.getAttribute("sessionId"));
ripple.setName(request.getParameter("name"));
ripple.setContent(request.getParameter("content"));
ripple.setIp(request.getRemoteAddr());

if(dao.insertRipple(ripple)){
	
%>
{"result" : "true"}
<%
}
else {
%>
{"result" : "false"}
<%
}
%>
