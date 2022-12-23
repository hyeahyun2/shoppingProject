<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="market.dto.Product" %>
<%@ page import="market.dao.ProductRepository" %>

<%
// 기본 세팅 (인코딩)
request.setCharacterEncoding("UTF-8");

// 파라미터 받아오기
String productId = request.getParameter("productId");
String name = request.getParameter("name");
String unitPrice = request.getParameter("unitPrice");
String description = request.getParameter("description");
String manufacturer = request.getParameter("manufacturer");
String category = request.getParameter("category");
String unitsInStock = request.getParameter("unitsInStock");
String condition = request.getParameter("condition");

// 가격 형변환
Integer price;
if(unitPrice.isEmpty()) price = 0;
else price = Integer.valueOf(unitPrice);
// 재고수 형변환
long stock;
if(unitsInStock.isEmpty()) stock = 0;
else stock = Long.valueOf(unitsInStock);

// ProductDAO 싱글톤 객체 불러오기
ProductRepository dao = ProductRepository.getInstance();

// 추가할 상품 객체 생성
Product newProduct = new Product();
// 생성한 객체의 정보 입력
newProduct.setProductId(productId);
newProduct.setPname(name);
newProduct.setUnitPrice(price);
newProduct.setDescription(description);
newProduct.setManufacturer(manufacturer);
newProduct.setCategory(category);
newProduct.setUnitsInStock(stock);
newProduct.setCondition(condition);

// 상품 리스트에 새 상품 정보 추가
dao.addProduct(newProduct);

// 상품 목록 페이지로 이동
response.sendRedirect("products.jsp");
%>