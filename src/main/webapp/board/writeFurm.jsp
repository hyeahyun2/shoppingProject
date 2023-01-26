<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	//String name = (String)request.getAttribute("name");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>게시글쓰기</title>
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
		<form name="newWrite" action="./BoardWriteAction.do" 
			enctype="multipart/form-data"
			class="form-horizotal" method="post" onsubmit="return checkForm()">
			<input type="hidden" name="id" class="form-control" value="${sessionId}">
			<div class="form-group row">
				<label class="col-sm-2 control-label">성명</label>
				<div class="col-sm-3">
					<input type="text" name="name" class="form-control" value="${name}">
				</div>
			</div>
			<div class="form-group row">
				<label class="col-sm-2 control-label">제목</label>
				<div class="col-sm-5">
					<input type="text" name="subject" class="form-control" 
						placeholder="subject">
				</div>
			</div>
			<div class="form-group row">
				<label class="col-sm-2 control-label">내용</label>
				<div class="col-sm-8">
					<textarea name="content" cols="50" rows="5" class="form-control" 
						placeholder="content"></textarea>
				</div>
			</div>
			<div class="form-group row">
				<label class="col-sm-2 control-label">이미지</label>
				<div class="col-sm-8">
					<input type="file" name="productImage" class="form-control">
				</div>
			</div>
			<div class="form-group row">
				<div class="col-sm-offset-2 col-sm-10">
					<input type="submit" class="btn btn-primary" value="등록">
					<input type="reset" class="btn btn-primary" value="취소">
				</div>
			</div>
		</form>
		<hr>
	</div>
	<jsp:include page="../inc/footer.jsp" />
</body>
</html>