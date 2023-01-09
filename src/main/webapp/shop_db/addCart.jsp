<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.ArrayList" %>
<%@ page import="market.dto.ProductDto" %>
<%@ page import="market.dao.ProductDao" %>
<%@ page import="market.dto.CartDto" %>
<%@ page import="market.dao.CartDao" %>

<%
// 상품id
String id = request.getParameter("id");
if(id == null || id.trim().equals("")){ // 아이디값이 비어있으면
	response.sendRedirect("products.jsp"); // 목록 페이지로 이동
	return;
}

// product 객체 생성 밑 확인
ProductDto product = new ProductDao().getProductById(id);

if(product == null){ // 미등록 상품이라면
	response.sendRedirect("../errorPage/exceptionNoProductId.jsp"); // 미등록상품 에러 페이지로 이동
	return;
}

// 로그인한 member Id 저장
String orderNo = session.getId();
String memberId = (String)session.getAttribute("sessionId");

/* 요청 파라미터 아이디의 상품을 담은 장바구니를 초기화 하도록 설정 */
// 장바구니 리스트 세션
CartDao cartDao = new CartDao();

// 장바구니 업데이트
boolean flag = cartDao.updateCart(product, orderNo, memberId);


if(flag){ // 업데이트 성공시
	response.sendRedirect("product.jsp?id=" + id); // 해당 상품 페이지로 다시 이동
}
else { // 업데이트 실패시
	response.sendRedirect("product.jsp?id=" + id + "&errorMessage=fail");
}

%>
