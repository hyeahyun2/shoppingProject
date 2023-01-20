<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="market.dto.RippleDto" %>
<%@ page import="market.dao.RippleDao" %>
<%@ page import="java.util.ArrayList" %>
<%@ include file="../resources/DB/connDB.jsp"%>
{ "listData" : [
	<%
	request.setCharacterEncoding("utf-8");
	
	String sessionId = (String)session.getAttribute("sessionId");
	String boardName = request.getParameter("boardName");
	int num = Integer.parseInt(request.getParameter("num"));
	
	RippleDao dao = RippleDao.getInstance();
	
	ArrayList<RippleDto> list = dao.getRippleList(boardName, num);
	int i = 0;
	for(RippleDto dto : list){
		boolean flag = sessionId != null && sessionId.equals(dto.getMemberId()) ? true : false;
	%>
		{ "rippleId" : "<%= dto.getRippleId() %>",
			"name" : "<%= dto.getName() %>",
			"content" : "<%= dto.getContent() %>",
			"isWriter" : "<%= flag %>"
		}
	<%
		if(i++ < list.size() - 1){
			out.print(", ");
		}
	}
	%>
]}