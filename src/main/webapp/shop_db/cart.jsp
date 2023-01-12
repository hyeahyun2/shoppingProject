<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.ArrayList" %>
<%@ page import="market.dto.ProductDto" %>
<%@ page import="market.dao.ProductDao" %>
<%@ page import="market.dto.CartDto" %>
<%@ page import="market.dao.CartDao" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="../resources/css/bootstrap.min.css">
<%
String orderNo = session.getId();
%>
<title>장바구니</title>
<script src="../resources/js/check_system.js"></script>
</head>
<body>
	<jsp:include page="../inc/menu.jsp"/>
	<div class="jumbotron">
		<div class="container">
			<h1 class="display-3">장바구니</h1>
		</div>
	</div> 
	<%
	String errorMessage = request.getParameter("errorMessage");
	if(errorMessage != null){
		if(errorMessage.equals("fail")){
		%>
		<div class="alert alert-danger" role="alert">
		  물품 삭제에 실패했습니다! 다시 시도해주세요!
		</div>
		<%
		} else if(errorMessage.equals("noId")){
		%>
		<div class="alert alert-danger" role="alert">
		  해당 상품이 장바구니에 존재하지 않습니다! 장바구니를 다시 확인해주세요!
		</div>
		<%
		}
	}
	%>
	
	<div class="container">
		<div class="row">
			<table width="100%">
				<tr>
					<td align="left">
						<span class="btn btn-danger" onclick="deleteCart()">전체 삭제하기</span>
						<span class="btn btn-danger" onclick="deleteCartSel()">선택 삭제하기</span>
					</td>
					<td align="right"><a href="./shippingInfo.jsp?cartId=<%=orderNo%>" class="btn btn-success">주문하기</a></td>
				</tr>
			</table>
		</div>
		<div style="padding-top: 50px;">
			<form method="get" name="frmCart">
				<input type="hidden" name="id">
				<input type="text" name="chkdID">
				<table class="table table-hover">
					<tr>
						<th><input name="chkAll" type="checkbox" onclick="setChkAll();">상품</th>
						<th>상품</th>
						<th>가격</th>
						<th>수량</th>
						<th>소계</th>
						<th>비고</th>
					</tr>
					<%
					int sum = 0;
					CartDao cartDao = new CartDao();
	
					ArrayList<CartDto> cartArrayList = cartDao.getCartList(orderNo);
					for(CartDto cart : cartArrayList){
						int total = cart.getpUnitPrice() * cart.getCnt();
						sum += total;
					%>
					<tr>
						<td><input type="checkbox" name="chkID" value="<%= cart.getCartId() %>" onclick="setChkAlone(this);"></td>
						<td><%= cart.getProductId()%> - <%= cart.getpName() %></td>
						<td><%= cart.getpUnitPrice() %></td>
						<td><%= cart.getCnt() %></td>
						<td><%= total %></td>
						<td><span class="badge badge-danger" id="removeBtn" onclick="removeToCart('<%= cart.getCartId() %>')">삭제</span></td>
					</tr>
					<%
					}
					%>
					<tr>
						<th></th>
						<th></th>
						<th>총액</th>
						<th><%= sum %></th>
						<th></th>
					</tr>
				</table>
			</form>
			<a href="./products.jsp" class="btn btn-secondary">&laquo; 쇼핑 계속하기</a>
		</div>
	</div>
	<jsp:include page="../inc/footer.jsp"/>
<script>
	window.onload= function(){
		document.frmCart.chkAll.checked = true; // 전체 선택 체크 박스 체크
		setChkAll(); // 목록의 체크박스 체크
	}
	function frmName(){
		return document.frmCart;
	}
</script>
<script type="text/javascript">
	const form = document.frmCart;
	const removeBtn = document.getElementById("removeBtn");
	function removeToCart(removeId){
		if(confirm("장바구니에서 해당 상품을 삭제하시겠습니까?")){
			form.id.value = removeId;
			form.action = "removeCart.jsp";
			form.submit(); // 장바구니에서 삭제
		}
	}
	function deleteCartSel(){
		if(confirm("선택한 상품을 삭제하시겠습니까?")){
			form.action = "removeCartSel.jsp";
			form.submit();
		}
	}
	function deleteCart(){
		if(confirm("장바구니에서 상품을 모두 삭제하시겠습니까?")){
			form.action = "deleteCart.jsp";
			form.submit(); // 장바구니에서 삭제
		}
	}
</script>
</body>
</html>