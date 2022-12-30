<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.ArrayList" %>
<%@ page import="market.dto.Product" %>
<%@ page import="market.dao.ProductRepository" %>

<%
// 상품id
String id = request.getParameter("id");
if(id == null || id.trim().equals("")){ // 아이디값이 비어있으면
	response.sendRedirect("products.jsp"); // 목록 페이지로 이동
	return;
}

ProductRepository dao = ProductRepository.getInstance();

Product product = dao.getProductById(id); // 등록된 상품인지 확인
if(product == null){ // 미등록 상품이라면
	response.sendRedirect("errorPage/exceptionNoProductId.jsp"); // 미등록상품 에러 페이지로 이동
}

/* 요청 파라미터 아이디의 상품을 담은 장바구니를 초기화 하도록 설정 */
// 장바구니 리스트 세션
ArrayList<Product> list = (ArrayList<Product>) session.getAttribute("cartlist");
// 장바구니에 원래 아무것도 담겨있지 않았을 경우 (장바구니 리스트 세션 생성 전이면)
if(list == null){
	list = new ArrayList<Product>(); // Product 리스트 객체 생성
	session.setAttribute("cartlist", list); // 장바구니 리스트 세션 생성 & 해당 세션 값=list
}

boolean isUpdate = false; //  기존 장바구니에 담긴 상품인지 확인하기 위한 용도 (true-> 있는 상품)
for(Product p : list){
	if(p.getProductId().equals(id)){// 기존 장바구니에 담긴 상품이라면
		isUpdate = true;
		p.setQuantity(p.getQuantity() + 1); // 기존 개수 + 1
	}
}
if(!isUpdate){ // 장바구니에 담기지 않은 새 상품이라면
	product.setQuantity(1); // 개수 : 1
	list.add(product); // 장바구니 리스트에 추가해주기
}
response.sendRedirect("product.jsp?id=" + id); // 해당 상품 페이지로 다시 이동
%>
