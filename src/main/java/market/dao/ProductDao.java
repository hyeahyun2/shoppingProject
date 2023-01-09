package market.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import market.dto.ProductDto;
import mvc.database.DBConnection;

public class ProductDao {

	private Connection conn = null;
	private PreparedStatement pstmt = null;
	private ResultSet rs = null;
	
	public ProductDao() {
		connect();
	}
	// DB 연결
	void connect() {
		try {
			conn = DBConnection.getConnection();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	// id로 해당 상품 정보 찾기
	public ProductDto getProductById(String productId) {
		ProductDto product = null;
		
		String sql = "select * from product where p_id = ?";
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, productId);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				product = new ProductDto();
				product.setProductId(rs.getString("p_id"));
				product.setPname(rs.getString("p_name"));
				product.setUnitPrice(rs.getInt("p_unitPrice"));
				product.setDescription(rs.getString("p_description"));
				product.setManufacturer(rs.getString("p_category"));
				product.setCategory(rs.getString("p_manufacturer"));
				product.setUnitsInStock(rs.getLong("p_unitsInStock"));
				product.setCondition(rs.getString("p_condition"));
				product.setFilename(rs.getString("p_fileName"));
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
		return product;
	}
}
