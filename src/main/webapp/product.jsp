<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="market.dto.Product" %>
<%@ page import = "market.dao.ProductRepository" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="./resources/css/bootstrap.min.css">
<title>상품 상세 정보</title>
</head>
<body>
	<jsp:include page="menu.jsp"/>
	<div class="jumbotron">
		<div class="container">
			<h1 class="display-3">상품목록</h1>
		</div>
	</div>
	<%
	String id = request.getParameter("id");
	ProductRepository dao = ProductRepository.getInstance(); // 싱클톤 객체 불러오기
	Product product = dao.getProductById(id); // 싱글톤 객체의 해당 id product 정보 불러오기
	%>
	<div class="container">
		<div class="row">
			<div class="col-md-6">
				<h3><%= product.getPname() %></h3>
				<p><%= product.getDescription() %></p>
				<p><b>상품 코드 : </b><span class="badge badge-danger"> <%= product.getProductId() %></span>
				<p><b>제조사</b> : <%= product.getManufacturer() %></p>
				<p><b>분류</b> : <%= product.getCategory() %></p>
				<p><b>재고 수</b> : <%= product.getUnitsInStock() %></p>
				<h4><%= product.getUnitPrice() %>원</h4>
				<p><a href="#" class="btn btn-info"> 상품 주문 &raquo;</a></p>
				<a href="./products.jsp" class="btn btn-secondary"> 상품 목록 &raquo;</a>
			</div>
		</div>
		<hr>
	</div>
	<jsp:include page="footer.jsp"/>
</body>
</html>