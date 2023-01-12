<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="market.dao.CartDao" %>
<%
/* 장바구니에서 상품을 개별 삭제 */
int cartId = Integer.parseInt(request.getParameter("id"));
String orderNo = session.getId();

CartDao cartDao = new CartDao();

int state = cartDao.removeProductInCart(cartId, orderNo);

if(state == 1){ // 정상적으로 처리 완료
	response.sendRedirect("cart.jsp");

}
else if(state == 0) { // 해당 cartId 존재하지 않음
	response.sendRedirect("cart.jsp?error=noId");
}
else {
	response.sendRedirect("cart.jsp?error=fail");
}
%>