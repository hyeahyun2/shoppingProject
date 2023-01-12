<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="market.dao.CartDao" %>
<%
String memberId = (String)session.getAttribute("sessionId");
String orderNo = session.getId();

CartDao cartDao = new CartDao();

boolean state = cartDao.deleteAllCart(memberId, orderNo);

if(state){ // 정상적으로 모든 데이터 삭제 완료
	response.sendRedirect("cart.jsp");
}
else { // 에러 발생
	response.sendRedirect("cart.jsp?error=fail");
}

%>