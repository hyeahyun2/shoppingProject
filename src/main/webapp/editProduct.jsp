<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../resources/DB/connDB.jsp" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="./resources/css/bootstrap.min.css">
<title>상품 편집</title>
<script type="text/javascript">
	function deleteConfirm(id){
		if(confirm("해당 상품을 삭제합니다!!")){ // 확인
			location.href = "./deleteProduct.jsp?id=" + id;
		}
		else return; // 취소
	}
</script>
</head>
<%
// edit = update or delete
String edit = request.getParameter("edit");
%>
<body>
	<jsp:include page="menu.jsp"/>
	<div class="jumbotron">
		<div class="container">
			<h1 class="display-3">상품 편집</h1>
		</div>
	</div>
	<%
	%>
	<div class="container">
		<div class="row" align="center">
			<%
			try{
				String sql = "select * from product";
				pstmt = conn.prepareStatement(sql);
				rs = pstmt.executeQuery();
				while(rs.next()){
			%>
			<div class="col-md-4">
				<img src="${pageContext.request.contextPath}/resources/images/<%= rs.getString("p_fileName") %>"
					style="width: 100%" alt="product img">
				<h3><%= rs.getString("p_name") %></h3>
				<p><%= rs.getString("p_description") %></p>
				<p><%= rs.getString("p_UnitPrice") %>원</p>
				<p>
					<%
					if(edit.equals("update")){ // 수정
					%>
					<a href="./updateProduct.jsp?id=<%= rs.getString("p_id") %>" class="btn btn-success" role="button">수정 &raquo;</a>
					<%
					} else if(edit.equals("delete")){ // 삭제
					%>
					<a href="#" onclick="deleteConfirm('<%= rs.getString("p_id") %>')" class="btn btn-danger" role="button">삭제 &raquo;</a>
					<%
					}
					%>
			</div>
			<%
				} 
			} catch(Exception e){
				e.printStackTrace();
			}
			finally {
				try{
					if(rs != null) rs.close();
					if(pstmt != null) pstmt.close();
					if(conn != null) conn.close();
				} catch(Exception e){
					e.printStackTrace();
				}
			}
			%>
		</div>
		<hr>
	</div>
	<jsp:include page="footer.jsp"/>
</body>
</html>