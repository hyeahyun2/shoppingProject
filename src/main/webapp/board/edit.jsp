<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="market.dto.BoardDto" %>
<%
	BoardDto notice = (BoardDto)request.getAttribute("board");
	int num = ((Integer)request.getAttribute("num")).intValue();
	int nowpage = ((Integer)request.getAttribute("page")).intValue();
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title><%=notice.getSubject()%></title>
<link rel="stylesheet" href="../resources/css/bootstrap.min.css" />
</head>
<script type="text/javascript">
	function checkForm(){
		if(!document.newWrite.name.value){
			alert("성명을 입력하세요.");
			return false;
		}
		if(!document.newWrite.subject.value){
			alert("제목을 입력하세요.");
			return false;
		}
		if(!document.newWrite.content.value){
			alert("내용을 입력하세요.");
			return false;
		}
	}
</script>
<body>
	<jsp:include page="../inc/menu.jsp" />
	<div class="jumbotron">
		<div class="container">
			<h1 class="display-3">게시판</h1>
		</div>
	</div>
	<div class="container">
		<form name="newUpdate" action="./BoardUpdateAction.do?num=<%=notice.getNum()%>&pageNum=<%=nowpage%>" 
			class="form-horizotal" method="post">
			<div class="form-group row">
				<label class="col-sm-2 control-label">성명</label>
				<div class="col-sm-3">
					<input type="text" name="name" class="form-control" value="<%=notice.getName()%>">
				</div>
			</div>
			<div class="form-group row">
				<label class="col-sm-2 control-label">제목</label>
				<div class="col-sm-5">
					<input type="text" name="subject" class="form-control" value="<%=notice.getSubject()%>">
				</div>
			</div>
			<div class="form-group row">
				<label class="col-sm-2 control-label">내용</label>
				<div class="col-sm-8">
					<textarea name="content" cols="50" rows="5" class="form-control"
						placeholder="content"><%=notice.getContent() %></textarea>
				</div>
			</div>
			<div class="form-group row">
				<div class="col-sm-offset-2 col-sm-10">
					<c:set var="userId" value="<%=notice.getId() %>" />
					<c:if test="${sessionId==userId}">
						<p>
							<input type="submit" class="btn btn-success" value="수정">
						</p>
						<a href="./BoardListAction.do?pageNum=<%=nowpage%>" class="btn btn-primary">목록으로</a>
					</c:if>
				</div>
			</div>
		</form>
		<hr>
	</div>
	<jsp:include page="../inc/footer.jsp" />
</body>
</html>