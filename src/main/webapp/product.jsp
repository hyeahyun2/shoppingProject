<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="market.dto.Product" %>
<%@ page import = "market.dao.ProductRepository" %>
<%@ page errorPage="errorPage/exceptionNoProductId.jsp" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="./resources/css/bootstrap.min.css">
<title>상품 상세 정보</title>
<script type="text/javascript">
	function addToCart(){
		if(confirm("상품을 장바구니에 추가하시겠습니까?")){
			document.addForm.submit(); // 장바구니에 추가
		}
		else {
			document.addForm.reset(); // 영향x
		}
	}
</script>
</head>
<body>
	<jsp:include page="menu.jsp"/>
	<div class="jumbotron">
		<div class="container">
			<h1 class="display-3">상품 정보</h1>
		</div>
	</div>
	<%
	String id = request.getParameter("id");
	ProductRepository dao = ProductRepository.getInstance(); // 싱클톤 객체 불러오기
	Product product = dao.getProductById(id); // 싱글톤 객체의 해당 id product 정보 불러오기
	%>
	<div class="container">
		<div class="row">
			<div class="col-md-5">
				<img src="${pageContext.request.contextPath}/resources/images/<%= product.getFilename() %>"
					style="width: 100%" alt="">
			</div>
			<div class="col-md-6">
				<h3><%= product.getPname() %></h3>
				<p><%= product.getDescription() %></p>
				<p><b>상품 코드 : </b><span class="badge badge-danger"> <%= product.getProductId() %></span>
				<p><b>제조사</b> : <%= product.getManufacturer() %></p>
				<p><b>분류</b> : <%= product.getCategory() %></p>
				<p><b>재고 수</b> : <%= product.getUnitsInStock() %></p>
				<h4><%= product.getUnitPrice() %>원</h4>
				<form name="addForm" action="./addCart.jsp" method="post">
					<input type="hidden" name="id" value="<%= product.getProductId() %>">
					<span class="btn btn-info" onclick="addToCart()"> 상품 주문 &raquo;</span>
					<a href="./cart.jsp" class="btn btn-warning">장바구니 &raquo;</a>
					<a href="./products.jsp" class="btn btn-secondary"> 상품 목록 &raquo;</a>
				</form>
			</div>
		</div>
		<hr>
	</div>
	<jsp:include page="footer.jsp"/>
</body>
</html>