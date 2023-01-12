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
	
	// ��ٱ��� ��� Ŭ�� ��
	public boolean updateCart(ProductDto productDto, String orderNo, String memberId) {
		// orderNo : �ֹ���ȣ,  id : �α��ΰ���id
		int flag = 0; // ��ȯ�� �� ����� ����
		
		try {
			// memberId && p_name ��ġ ���� Ȯ��
			String sql = "select cartId from p_cart where orderNo = ? and p_id = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, orderNo);
			pstmt.setString(2, productDto.getProductId());
			rs = pstmt.executeQuery();
			if(rs.next()) { // �̹� �ش� �ֹ���ȣ + �ش� ��ǰ �����ϴ� ���
				int cartId = rs.getInt("cartId"); // ��ٱ��Ͽ� ��� �ش� ��ǰ ����
				sql = "update p_cart set cnt = cnt + 1 where cartId = ?";
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, cartId);
				flag = pstmt.executeUpdate();
			}
			else { // �ֹ���ȣ && �ش� ��ǰ �������� �ʴ� ���
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
		
		// ��� ����/������Ʈ ������ ��� 1 => true
		return (flag == 1);
	}
	
	// orderNo�� ��ٱ����� ��� ���� �ҷ�����
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
	
	// ���� �����̸� ��ٱ��� �ֹ���ȣ �����ֱ�
	public boolean updateCartBylogin(HttpSession session) {
		int flag = 0;
		String orderNo = session.getId();
		String memberId = (String)session.getAttribute("sessionId");
		// ���� �α��ο� ��� ��ǰ ������Ʈ
		String sql = "update p_cart set orderNo = ? where memberId = ?";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, orderNo);
			pstmt.setString(2, memberId);
			flag = pstmt.executeUpdate();
			
			// �α��� ���� ��� ��ǰ ������Ʈ
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
	
	// ��ٱ��� ���� ��ǰ ������ �����
	public int removeProductInCart(int cartId, String orderNo) {
		int flag = -1;
		String sql = "select cartId from p_cart where cartId = ? and orderNo = ?";
				
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, cartId);
			pstmt.setString(2, orderNo);
			rs = pstmt.executeQuery();
			if(rs.next()) { // cartId && orderNo �� �����Ѵٸ�
				sql = "delete from p_cart where cartId = ?";
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, cartId);
				flag = pstmt.executeUpdate(); // ������� �� ����=1
			}
			else {// �ش� cartId && orderNo�� �������� ���� ��
				flag = 0;
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
		// -1 : db��ſ���, 0 : �α����� ������ ��ٱ��� ��ǰ�� �ƴ�
		// 1 : ���������� ��ȯ��
		return flag;
	}
	
	// ��ٱ��� ��ǰ ��ü ����
	public boolean deleteAllCart(String memberId, String orderNo) {
		int flag = 0;
		String sql = "select COUNT(*) from p_cart where memberId = ? and orderNo = ?";
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, memberId);
			pstmt.setString(2, orderNo);
			rs = pstmt.executeQuery();
			rs.next();
			if(rs.getInt(1) > 0) { // ���ǿ� �ش��ϴ� row�� ���� ���
				// memberId�� �ش��ϴ� ��ǰ ���� (�ش� ������ ��ٱ��� �ʱ�ȭ)
				sql = "delete from p_cart where memberId = ? and orderNo = ?";
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, memberId);
				pstmt.setString(2, orderNo);
				flag = pstmt.executeUpdate(); // ������� �� ����
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
		// ���������� ó�� => ������� �� ���� 1���� ����
		return flag > 0; 
	}
}
