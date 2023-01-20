<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="market.dto.RippleDto" %>
<%@ page import="market.dao.RippleDao" %>
<%@ page import="java.util.ArrayList" %>
<%@ include file="../resources/DB/connDB.jsp"%>
<%
request.setCharacterEncoding("utf-8");
int rippleId = Integer.parseInt(request.getParameter("rippleId"));

RippleDao dao = RippleDao.getInstance();
RippleDto ripple = new RippleDto();
ripple.setRippleId(rippleId);
if(dao.deleteRipple(ripple)){
	
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
