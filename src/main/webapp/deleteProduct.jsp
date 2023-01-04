<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="./resources/DB/connDB.jsp" %>
<%
// 기본 세팅
request.setCharacterEncoding("utf-8");

// 파라미터 받아오기
String productId = request.getParameter("id");

try {
	// 해당 id 있는지 확인
	String sql = "select p_id from product where p_id=?";
	pstmt = conn.prepareStatement(sql);
	pstmt.setString(1, productId);
	rs = pstmt.executeQuery();
	if(rs.next()){ // 만약 존재하는 id라면
		// 삭제 과정 실행
		sql = "delete from product where p_id = ?";
		pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, productId);
		pstmt.executeUpdate();
		
		// 삭제 페이지로 이동
		response.sendRedirect("editProduct.jsp?edit=delete");
	}
	else { // 존재하지 않는 id값
		// 에러 페이지로 이동
		response.sendRedirect("errerPage/exceptionNoProductId.jsp");
	}
} catch(Exception e) {
	e.printStackTrace();
} finally {
	if(rs != null) rs.close();
	if(pstmt != null) pstmt.close();
	if(conn != null) conn.close();
}

%>