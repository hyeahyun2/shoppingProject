<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="market.dao.CartDao" %>

<%

request.setCharacterEncoding("utf-8");
String[] chkID = request.getParameterValues("chkID");
String orderNo = session.getId();


// 기능 정상적 수행 여부
boolean state = true;

if(chkID != null){
	for(int i=0; i<chkID.length; i++){
		CartDao cartDao = new CartDao();
		int num = cartDao.removeProductInCart(Integer.parseInt(chkID[i]), orderNo);
		if(num <= 0) state = false; // 하나라도 삭제 실패한 경우 false
	}
}

if(state){
	response.sendRedirect("cart.jsp");
}
else {
	response.sendRedirect("cart.jsp?error=fail");
}


%>