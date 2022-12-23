<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.ArrayList" %>
<%@ page import="market.dto.Product" %>
<%@ page import = "market.dao.ProductRepository" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="./resources/css/bootstrap.min.css">
<title>상품 목록</title>
</head>
<body>
	<jsp:include page="menu.jsp"/>
	<div class="jumbotron">
		<div class="container">
			<h1 class="display-3">상품목록</h1>
		</div>
	</div>
	<%
	//ArrayList<Product> listOfProducts = market.dao.ProductRepository.getInstance().getAllProducts();
	ProductRepository dao = ProductRepository.getInstance();
	ArrayList<Product> listOfProducts = dao.getAllProducts();
	%>
	<div class="container">
		<div class="row" align="center">
			<%
			for(int i=0; i<listOfProducts.size(); i++){
				Product product = listOfProducts.get(i);
			%>
			<div class="col-md-4">
				<img src="${pageContext.request.contextPath}/resources/images/<%= product.getFilename() %>"
					style="width: 100%" alt="">
					<%  %>
				<h3><%= product.getPname() %></h3>
				<p><%= product.getDescription() %></p>
				<p><%= product.getUnitPrice() %>원</p>
				<p><a href="./product.jsp?id=<%= product.getProductId() %>" class="btn btn-secondary" role="button">
					상세 정보 &raquo;				
					</a>
			</div>
			<%
			}
			%>
		</div>
		<hr>
	</div>
	<jsp:include page="footer.jsp"/>
</body>
</html>