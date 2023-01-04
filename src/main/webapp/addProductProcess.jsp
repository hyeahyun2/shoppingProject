<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.oreilly.servlet.*" %>
<%@ page import="com.oreilly.servlet.multipart.*" %>
<%@ page import="java.util.*" %>
<%@ page import="java.io.*" %>
<%@ page import="market.dto.Product" %>
<%@ page import="market.dao.ProductRepository" %>
<%@ page import="java.awt.*" %>
<%@ page import="java.sql.*" %>
<%@ include file="./resources/DB/connDB.jsp" %>

<%
//기본 세팅 (인코딩)
request.setCharacterEncoding("UTF-8");

//저장될 위치 정해주기
String realPath = request.getServletContext().getRealPath("resources/images");
//String realPath = "C:/hyeahyun/smartWeb_back/eclipse_workspace_2021_12/shoppingProject/src/main/webapp/uploadImage";
File dir = new File(realPath);
if(!dir.exists()){ // 만약 해당 디렉토리(경로)가 존재하지 않으면
	dir.mkdirs(); // 해당 경로를 만들어주기
}

//디렉토리 열기
if(!Desktop.isDesktopSupported()){
	return;
}
Desktop desktop = Desktop.getDesktop();
if(dir.exists()){
	desktop.open(dir);
}


String filename = ""; // 업로드된 파일명
String encType="utf-8"; // 인코딩 타입
int maxSize = 5 * 1024 * 1024; // 최대 파일 크기 (5MB)

//MultipartRequest 객체 생성 (request로 전달받은 값들을 포함하는 객체)
MultipartRequest muti = new MultipartRequest(request, realPath, maxSize, encType,
		new DefaultFileRenamePolicy());

//파라미터 받아오기
String productId = muti.getParameter("productId");
String name = muti.getParameter("name");
String unitPrice = muti.getParameter("unitPrice");
String description = muti.getParameter("description");
String manufacturer = muti.getParameter("manufacturer");
String category = muti.getParameter("category");
String unitsInStock = muti.getParameter("unitsInStock");
String condition = muti.getParameter("condition");

//가격 형변환
Integer price;
if(unitPrice.isEmpty()) price = 0;
else price = Integer.valueOf(unitPrice);
//재고수 형변환
long stock;
if(unitsInStock.isEmpty()) stock = 0;
else stock = Long.valueOf(unitsInStock);

//input태그의 type="file"인 request 전달받아 저장
Enumeration files = muti.getFileNames();
String fname = (String) files.nextElement(); // 파라미터 name 불러오기
String fileName = muti.getFilesystemName(fname); // 디렉토리에 저장될 파일명


String sql = "insert into product values(?,?,?,?,?,?,?,?,?)";
pstmt = conn.prepareStatement(sql);
pstmt.setString(1, productId);
pstmt.setString(2, name);
pstmt.setInt(3, price);
pstmt.setString(4, description);
pstmt.setString(5, category);
pstmt.setString(6, manufacturer);
pstmt.setLong(7, stock);
pstmt.setString(8, condition);
pstmt.setString(9, fileName);
pstmt.executeUpdate();

if(pstmt != null) pstmt.close();
if(conn != null) conn.close();


//상품 목록 페이지로 이동
response.sendRedirect("./products.jsp");
%>
