<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="../resources/DB/connDB.jsp" %>
<html>
<head>
<%
String sessionAdminId = (String)session.getAttribute("sessionAdminId");
%>
<title>정보 수정</title>
</head>
<body>
	<jsp:include page="./menu.jsp" />
	<div class="jumbotron">
		<div class="container">
			<h1 class="display-3">관리자 정보 수정</h1>
		</div>
	</div>
		<%
		String sql = "select * from p_admin where id=?";
		pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, sessionAdminId);
		rs = pstmt.executeQuery();
		if(rs.next()){
		%>
		<div class="container">
			<form name="newMember" class="form-horizontal"  action="processUpdateAdmin.jsp" method="post" onsubmit="return checkForm()">
				<div class="form-group  row">
					<label class="col-sm-2 ">아이디</label>
					<div class="col-sm-3">
						<span class="form-control"><%= rs.getString("id") %></span>
					</div>
				</div>
				<div class="form-group  row">
					<label class="col-sm-2">비밀번호</label>
					<div class="col-sm-3">
						<input name="password" type="password" class="form-control" placeholder="password" value="<%= rs.getString("password") %>">
					</div>
				</div>
				<div class="form-group  row">
					<label class="col-sm-2">비밀번호확인</label>
					<div class="col-sm-3">
						<input name="password_confirm" type="password" class="form-control" placeholder="password confirm">
					</div>
				</div>
				<div class="form-group  row">
					<label class="col-sm-2">성명</label>
					<div class="col-sm-3">
						<input name="name" type="text" class="form-control" placeholder="name" value="<%= rs.getString("name") %>">
					</div>
				</div>
				<div class="form-group  row">
					<div class="col-sm-offset-2 col-sm-10 ">
						<input type="submit" class="btn btn-primary" value="정보수정 " > 
					</div>
				</div>
			</form>
		</div>
		<%
		}
		%>
		<jsp:include page="../inc/footer.jsp" />
</body>
</html>
<script type="text/javascript">
	// 유효성검사
	function checkForm(){
		if (!document.newMember.password.value) {
			alert("비밀번호를 입력하세요.");
			return false;
		}
		if (document.newMember.password.value != document.newMember.password_confirm.value) {
			alert("비밀번호를 동일하게 입력하세요.");
			return false;
		}
	}
</script>