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
		<div class="form-group row user-repple-list">
			<ul>
				<!-- ajax 추가 부분 -->
			</ul>
		</div>
		<form name="frmRippleDelete" class="form-horizontal" method="post">
			<input type="hidden" name="num" value="<%= notice.getNum() %>">
			<input type="hidden" name="pageNum" value="${nowpage}">
			<input type="hidden" name="rippleId">
		</form>
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
						<span class="btn btn-primary goRippleSubmit">등록</span>
					</div>
				</div>
			</form>
			<script>
				const xhr = new XMLHttpRequest();
				/* 함수 선언 */
				const insertItem = function(item){
					/* 목록에 요소를 추가, 처음 로딩시 목록을 출력하거나, 새로운 글 등록시 사용| */
					let tagNew = document.createElement("li");
					tagNew.innerHTML = item.content + " | " + item.name;
					if(item.isWriter === "true"){
						tagNew.innerHTML += "<span class='btn btn-danger deleteBtn' onclick='goRippleDelete("+ item.rippleId + ");'>삭제</span>";
					}
					let tagUl = document.querySelector(".user-repple-list ul");
					tagUl.append(tagNew);
				}
				const insertList = function(){
					/* 목록 가져오기 */
					let num = document.frmRippleDelete.num;
					//xhr.open("GET", "../board/ajax_list_time.jsp?boardName=board&num=" + num.value);
					xhr.open("GET", "../RippleListAction.do?boardName=board&num=" + num.value);
					xhr.send();
					xhr.onreadystatechange = function(){
						if(xhr.readyState !== XMLHttpRequest.DONE) return;
						
						if(xhr.status === 200){ // 서버(url)에 문서가 존재
							const json = JSON.parse(xhr.response);
						
							let tagUl = document.querySelector(".user-repple-list ul");
							tagUl.innerHTML = "";
							
							for(let data of json.listData){
								console.log(data);
								insertItem(data);
							}
						}
						else {// 서버(url)에 문서가 존재하지 않음
							console.error("Error", xhr.status, xhr.statusText);
						}
					}
				}
				const goRippleDelete = function(ID){
					if(confirm("삭제하시겠습니까?")){
						const xhr = new XMLHttpRequest();
						//xhr.open("POST", "../board/ajax_delete.jsp?rippleId=" + ID);
						xhr.open("POST", "../RippleDeleteAction.do?rippleId=" + ID);
						xhr.send();
						xhr.onreadystatechange = ()=>{
							if(xhr.readyState !== XMLHttpRequest.DONE) return;
							if(xhr.status === 200){
								const json = JSON.parse(xhr.response);
								if(json.result === "true"){
									insertList();
								}
								else {
									alert("삭제에 실패했습니다.");
								}
							}
							else {
								console.error("Error", xhr.status, xhr.statusText);
							}
						}
					}
				}
				
				function goRippleSubmit(){ // 리플 등록
					let frm  = document.frmRipple;
					frm.action = "../RippleWriteAction.do";
					frm.submit();
				}
				document.addEventListener("DOMContentLoaded", function(){
					insertList(); // 리플 목록 불러오기
					
					const xhr = new XMLHttpRequest();
					document.querySelector(".goRippleSubmit").addEventListener("click", function(){
						/* 등록 버튼 클릭 시 */
						// 1) 데이터베이스에 등록, 2) 화면에 표시
						let frm  = document.frmRipple;
						let num = frm.num;
						let name = frm.name;
						let content = frm.content;
						
						//xhr.open("POST", "../board/ajax_insert_content.jsp?boardName=board&num=" + 
						//		num.value + "&name=" + name.value + "&content=" + content.value);
						xhr.open("POST", "../RippleWriteAction.do?boardName=board&num=" + 
								num.value + "&name=" + name.value + "&content=" + content.value);
						xhr.send();
						xhr.onreadystatechange = () => {
							if(xhr.readyState !== XMLHttpRequest.DONE) return;
							if(xhr.status === 200){
								const json = JSON.parse(xhr.response);
								if(json.result === 'true'){
									content.value = "";
									insertList();
								}
								else {
									alert("등록에 실패했습니다.");
								}
							}
							else {
								 console.error("Error", xhr.status, xhr.statusText);
							}
						}
					})
				})
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