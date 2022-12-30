<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.ArrayList" %>
<%@ page import="market.dto.Product" %>
<%@ page import="market.dao.ProductRepository" %>
<%
/* 장바구니에서 상품을 개별 삭제 */
String id = request.getParameter("id");
if(id == null || id.trim().equals("")){
	response.sendRedirect("products.jsp");
	return;
}

ProductRepository dao = ProductRepository.getInstance();

Product product = dao.getProductById(id); // product id 존재 여부 검사
if(product == null){ // 존재하지 않는 아이디면
	response.sendRedirect("exceptionNoProductId.jsp");
}

ArrayList<Product> cartList = (ArrayList<Product>)session.getAttribute("cartlist");
Product removeP = null;
for(Product p : cartList){
	if(p.getProductId().equals(id)){ // 요청된 id값과 일치하면
		removeP = p;
	}
}
cartList.remove(removeP); // 카트에서 해당 id를 가지는 product 삭제
response.sendRedirect("cart.jsp");
%>