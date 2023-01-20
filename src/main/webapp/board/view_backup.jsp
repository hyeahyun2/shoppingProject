<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="market.dto.BoardDto" %>
<%@ page import="market.dto.RippleDto" %>
<%@ page import="java.util.ArrayList" %>
<%
	BoardDto notice = (BoardDto)request.getAttribute("board");
	int num = ((Integer)request.getAttribute("num")).intValue();
	int nowpage = ((Integer)request.getAttribute("page")).intValue();
	ArrayList<RippleDto> rippleList = (ArrayList<RippleDto>)request.getAttribute("rippleList");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title><%=notice.getSubject()%></title>
<link rel="stylesheet" href="../resources/css/bootstrap.min.css" />
</head>
<script type="text/javascript">
	function checkDelete(){
		if(confirm("정말 삭제하시겠습니까?")){
			location.href="./BoardDeleteAction.do?num=<%=notice.getNum()%>&pageNum=<%=nowpage%>"
		}
		else {
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
		<div class="form-group row">
			<label class="col-sm-2 control-label">성명</label>
			<div class="col-sm-3">
				<p><%=notice.getName()%></p>
			</div>
		</div>
		<div class="form-group row">
			<label class="col-sm-2 control-label">제목</label>
			<div class="col-sm-5">
				<p><%=notice.getSubject()%></p>
			</div>
		</div>
		<div class="form-group row">
			<label class="col-sm-2 control-label">내용</label>
			<div class="col-sm-8">
				<div class="form-control" style="height: 300px;"><%=notice.getContent() %></div>
			</div>
		</div>
		<hr>
		<!-- 리플 목록 -->
		<c:forEach var="ripple" items="${rippleList}">
			<p>${ripple.content} | ${ripple.name}
				<c:if test="${sessionId == ripple.memberId}">
					<span class="btn btn-danger" onclick="goRippleDelete(${ripple.rippleId});">삭제</span>
				</c:if>
			</p>
		</c:forEach>
		<form name="frmRippleDelete" class="form-horizontal" method="post">
			<input type="hidden" name="num" value="<%= notice.getNum() %>">
			<input type="hidden" name="pageNum" value="${nowpage}">
			<input type="hidden" name="rippleId">
		</form>
		<script>
			function goRippleDelete(id){
				if(confirm("삭제하시겠습니까?")){
					const frm = document.frmRippleDelete;
					frm.rippleId.value = id;
					frm.action = "RippleDeleteAction.do";
					frm.submit();
				}
			}
		</script>
		<hr>
		<c:set var="nowPageNum" value="<%=nowpage%>" />
		<c:if test="${sessionId != null}">
			<form name="frmRipple" class="form-horizontal" method="post">
				<input type="hidden" name="num" value="<%= notice.getNum() %>">
				<input type="hidden" name="pageNum" value="${nowPageNum}">
				<div class="form-group row">
					<label class="col-sm-2 control-label">성명</label>
					<div class="col-sm-3">
						<input type="text" name="name" class="form-control" value="${sessionId}" placeholder="name">
					</div>
				</div>
				<div class="form-group row">
					<label class="col-sm-2 control-label">내용</label>
					<div class="col-sm-8" style="word-break: break-all;">
						<textarea name="content" class="form-control" cols="50" rows="3"></textarea>
					</div>
				</div>
				<div class="form-group row">
					<label class="col-sm-2 control-label"></label>
					<div class="col-sm-3">
						<span class="btn btn-primary" onclick="goRippleSubmit();">등록</span>
					</div>
				</div>
			</form>
			<script>
				function goRippleSubmit(){
					let frm  = document.frmRipple;
					frm.action = "BoardRippleWriteAction.do";
					frm.submit();
				}
			</script>
		</c:if>
		<div class="form-group row">
			<div class="col-sm-offset-2 col-sm-10">
				<c:set var="userId" value="<%=notice.getId() %>" />
				<c:if test="${sessionId==userId}">
					<p>
						<button type="button" class="btn btn-danger" onclick="checkDelete()">삭제</button>
						<a href="./BoardEditAction.do?num=<%=notice.getNum()%>&pageNum=<%=nowpage%>" class="btn btn-success">수정하기</a>
					</p>
				</c:if>
				<a href="./BoardListAction.do?pageNum=<%=nowpage%>" class="btn btn-primary">목록으로</a>
			</div>
		</div>
		<hr>
	</div>
	<jsp:include page="../inc/footer.jsp" />
</body>
</html>