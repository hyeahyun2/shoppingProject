<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="market.dto.BoardDto"%>
<%@page import="market.dao.BoardDao"%>
<%@ include file="../resources/DB/connDB.jsp" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<%
	response.setCharacterEncoding("utf-8");
	
	BoardDao dao = BoardDao.getInstance();
	
	for(int i=0; i<300; i++){
		BoardDto board = new BoardDto();
		board.setId("test11");
		board.setName("김혜현");
		board.setSubject(i + "번째 글입니다.");
		board.setContent(i + "번째 글 내용 입니다.");
		board.setIp(request.getRemoteAddr());
		
		dao.insertBoard(board);
	}
	
	out.print("글 생성 완료");
	%>
</body>
</html>