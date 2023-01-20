package market.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import market.dto.RippleDto;
import mvc.database.DBConnection;

public class RippleDao {
	private static RippleDao instance;
		
	private RippleDao() {
		
	}

	public static RippleDao getInstance() {
		if(instance == null) {
			instance = new RippleDao();
		}
		return instance;
	}
	
	// 리플 작성
	public boolean insertRipple(RippleDto ripple) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		int upd = 0;
		String sql = "insert into p_ripple "
				+ "(boardName, boardNum, memberId, name, content, insertDate, ip) "
				+ "values (?, ?, ?, ?, ?, now(), ?)";
		
		try {
			conn = DBConnection.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, ripple.getBoardName());
			pstmt.setInt(2, ripple.getBoardNum());
			pstmt.setString(3, ripple.getMemberId());
			pstmt.setString(4, ripple.getName());
			pstmt.setString(5, ripple.getContent());
			pstmt.setString(6, ripple.getIp());
			upd = pstmt.executeUpdate();
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if(pstmt != null) pstmt.close();
				if(conn != null) conn.close();
			} catch(Exception ex) {
				throw new RuntimeException(ex.getMessage());
			}
		}
		return upd != 0;
	}
	
	// 리플 리스트 불러오기
	public ArrayList<RippleDto> getRippleList(String boardName, int boardNum){
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		String sql = "select * from p_ripple "
				+ "where boardName = ? and boardNum = ?";
		ArrayList<RippleDto> list = new ArrayList<>();
		
		try {
			conn = DBConnection.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, boardName);
			pstmt.setInt(2, boardNum);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				RippleDto ripple = new RippleDto();
				ripple.setRippleId(rs.getInt("rippleId"));
				ripple.setBoardName(rs.getString("boardName"));
				ripple.setBoardNum(rs.getInt("boardNum"));
				ripple.setMemberId(rs.getString("memberId"));
				ripple.setName(rs.getString("name"));
				ripple.setContent(rs.getString("content"));
				ripple.setInsertDate(rs.getString("insertDate"));
				ripple.setIp(rs.getString("ip"));
				list.add(ripple);
			}
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if(rs != null) rs.close();
				if(pstmt != null) pstmt.close();
				if(conn != null) conn.close();
			} catch(Exception ex) {
				throw new RuntimeException(ex.getMessage());
			}
		}
		
		return list;
	}
	
	public boolean deleteRipple(RippleDto ripple) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		int upd = 0;
		String sql = "delete from p_ripple where rippleId = ?";
		
		try {
			conn = DBConnection.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, ripple.getRippleId());
			upd = pstmt.executeUpdate();
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if(pstmt != null) pstmt.close();
				if(conn != null) conn.close();
			} catch(Exception ex) {
				throw new RuntimeException(ex.getMessage());
			}
		}
		return upd != 0;
	}
}
