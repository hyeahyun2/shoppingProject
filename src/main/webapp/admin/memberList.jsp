<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../resources/DB/connDB.jsp" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원 목록</title>
</head>
<body>
	<%@ include file="./menu.jsp" %>
	<div class="jumbotron">
		<div class="container">
			<h1 class="display-3">회원 목록</h1>
		</div>
	</div>
	<div class="container">
		<table class="table table-hover text-center">
		  <thead>
		    <tr>
		      <th scope="col">아이디</th>
		      <th scope="col">이름</th>
		      <th scope="col">이메일</th>
		      <th scope="col">휴대폰 번호</th>
		      <th scope="col">주소</th>
		      <th scope="col">가입날짜</th>
		      <th scope="col">상세보기</th>
		    </tr>
		  </thead>
		  <tbody>
		  		<%
		  		int cntListPerPage = 2; // 페이지당 게시물 수
		  		int pageNum = 1; // 페이지 번호 (초기값 : 1)
		  		if(request.getParameter("pageNum") != null) { // 페이지 번호 파라미터가 전달된 경우
		  			pageNum = Integer.parseInt(request.getParameter("pageNum"));
		  		}
		  		
		  		// 해당 페이지에서 불러올 정보의 첫번째 number
		  		int startNum = (pageNum - 1) * cntListPerPage;
					String sql = "select * from p_member limit ?, ?";
					pstmt = conn.prepareStatement(sql);
					pstmt.setInt(1, startNum);
					pstmt.setInt(2, cntListPerPage);
					rs = pstmt.executeQuery();
					while(rs.next()){
					%>
					<tr>
						<td><%= rs.getString("id") %></td>
						<td><%= rs.getString("name") %></td>
						<td><%= rs.getString("mail") %></td>
						<td><%= rs.getString("phone") %></td>
						<td><%= rs.getString("address") %></td>
						<td><%= rs.getString("regist_day") %></td>
						<td><button class="btn btn-success">상세보기</button></td>
					</tr>
					<%
					}
					
					
					%>
		  </tbody>
	  </table>
	  <div class="col-sm-5">
	  	<%
			sql = "select COUNT(*) from p_member";
	  	pstmt = conn.prepareStatement(sql);
	  	rs = pstmt.executeQuery();
	  	rs.next();
	  	int totalRecord = rs.getInt(1); // 전체 게시물 수
	  	int totalPage = // 전체 페이지 수
	  			(totalRecord % cntListPerPage == 0) ? 
	  				totalRecord/cntListPerPage : (totalRecord/cntListPerPage) + 1;
	  	
	  	// 페이징 처리
	  	int block = 2; // 페이지 나올 범위
	  	int blockTotal = totalPage % block == 0 ? 
	  				totalPage / block : totalPage / block + 1; // 총 블럭의 수
	  	int blockThis = ((pageNum - 1) / block) + 1; // 현재 페이지의 블럭
	  	int blockThisFirstPage = ((blockThis - 1) * block) + 1; // 현재 블럭의 첫 페이지
	  	int blockThisLastPage = blockThis * block; // 현재 블럭의 마지막 페이지
	  	blockThisLastPage = (blockThisLastPage > totalPage) ? 
	  				totalPage : blockThisLastPage; // 마지막 블럭의 경우 전체 페이지 번호가 마지막 페이지
	  	
	  	out.println("totalRecord : " + totalRecord + "<br>");
	  	out.println("totalPage : " + totalPage + "<br>");
	  	out.println("blockTotal : " + blockTotal + "<br>");
	  	out.println("blockThis : " + blockThis + "<br>");
	  	out.println("blockThisFirstPage : " + blockThisFirstPage + "<br>");
	  	out.println("blockThisLastPage : " + blockThisLastPage + "<br>");
	  	
	  	// 페이징 출력
	  	%>
	  	<a href="memberList.jsp?pageNum=1">첫 페이지</a> |
	  	<%
	  	if(blockThis > 1) { // 이번 블럭의 첫 페이지 -1
	  	%>
		  	<a href="memberList.jsp?pageNum=<%=(blockThisFirstPage - 1)%>">이전</a> |
	  	<%
	  	}
	  	// 현재 블럭의 첫 페이지 부터 현재 블럭의 마지막 페이지까지 출력
	  	for(int i=blockThisFirstPage; i<= blockThisLastPage; i++){
	  	%>
		  	<a href="memberList.jsp?pageNum=<%= i %>"><%= i %></a> |
	  	<%
	  	}
	  	if(blockThis < blockTotal){ // 현재 블럭이 마지막 블럭이 아니면
	  	%>
		  	<a href="memberList.jsp?pageNum=<%=(blockThisLastPage + 1)%>">다음</a> |
	  	<%
	  	}
	  	%>
	  	<a href="memberList.jsp?pageNum=<%=totalPage%>">마지막 페이지</a>
	  </div>
  </div>
  <%
  if(rs != null) rs.close();
	if(pstmt != null) pstmt.close();
	if(conn != null) conn.close();
  %>
</body>
</html>