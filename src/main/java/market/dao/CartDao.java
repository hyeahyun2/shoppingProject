package market.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import javax.servlet.http.HttpSession;

import market.dto.CartDto;
import market.dto.ProductDto;
import mvc.database.DBConnection;

public class CartDao {
	private Connection conn = null;
	private PreparedStatement pstmt = null;
	private ResultSet rs = null;
	
	public CartDao() {
		connect();
	}
	private void connect() {
		try {
			conn = DBConnection.getConnection();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	//
	public boolean updateCart(ProductDto productDto, String orderNo, String memberId) {
		// orderNo : 주문번호,  id : 로그인계정id
		int flag = 0; // 반환할 때 사용할 변수
		
		try {
			// memberId && p_name 일치 여부 확인
			String sql = "select cartId from p_cart where orderNo = ? and p_id = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, orderNo);
			pstmt.setString(2, productDto.getProductId());
			rs = pstmt.executeQuery();
			if(rs.next()) { // 이미 해당 주문번호 + 해당 상품 존재하는 경우
				int cartId = rs.getInt("cartId"); // 장바구니에 담긴 해당 상품 개수
				sql = "update p_cart set cnt = cnt + 1 where cartId = ?";
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, cartId);
				flag = pstmt.executeUpdate();
			}
			else { // 주문번호 && 해당 상품 존재하지 않는 경우
				sql = "insert into p_cart (memberId, orderNo, p_id, p_name, p_unitPrice, cnt) "
						+ "values (?, ?, ?, ?, ?, ?)";
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, memberId);
				pstmt.setString(2, orderNo);
				pstmt.setString(3, productDto.getProductId());
				pstmt.setString(4, productDto.getPname());
				pstmt.setInt(5, productDto.getUnitPrice());
				pstmt.setInt(6, 1);
				flag = pstmt.executeUpdate();
			}
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if(rs != null) rs.close();
				if(pstmt != null) pstmt.close();
				if(conn != null) conn.close();
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
		
		// 디비 저장/업데이트 성공한 경우 1 => true
		return (flag == 1);
	}
	
	public ArrayList<CartDto> getCartList(String orderNo){
		ArrayList<CartDto> cartArrayList = null;
		
		String sql = "select * from p_cart where orderNo = ?";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, orderNo);
			rs = pstmt.executeQuery();
			cartArrayList = new ArrayList<CartDto>();
			while(rs.next()) {
				CartDto cart = new CartDto();
				cart.setCartId(rs.getInt("cartId"));
				cart.setMemberId(rs.getString("memberId"));
				cart.setOrderNo(rs.getString("orderNo"));
				cart.setProductId(rs.getString("p_id"));
				cart.setpName(rs.getString("p_name"));
				cart.setpUnitPrice(rs.getInt("p_unitPrice"));
				cart.setCnt(rs.getInt("cnt"));
				cartArrayList.add(cart);
			}
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if(rs != null) rs.close();
				if(pstmt != null) pstmt.close();
				if(conn != null) conn.close();
			} catch(Exception e) {
				
			}
		}
		
		return cartArrayList;
	}
	
	// 같은 계정이면 장바구니 주문번호 맞춰주기
	public boolean updateCartBylogin(HttpSession session) {
		int flag = 0;
		String orderNo = session.getId();
		String memberId = (String)session.getAttribute("sessionId");
		// 이전 로그인에 담운 상품 업데이트
		String sql = "update p_cart set orderNo = ? where memberId = ?";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, orderNo);
			pstmt.setString(2, memberId);
			flag = pstmt.executeUpdate();
			
			// 로그인 전에 담운 상품 업데이트
			sql = "update p_cart set memberId = ? where orderNo = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, memberId);
			pstmt.setString(2, orderNo);
			flag = pstmt.executeUpdate();
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if(rs != null) rs.close();
				if(pstmt != null) pstmt.close();
				if(conn != null) conn.close();
			} catch(Exception e) {
				e.printStackTrace();
			}
		}

		return flag != 0;
	}
}
