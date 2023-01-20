package market.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import market.dto.BoardDto;
import mvc.database.DBConnection;

public class BoardDao {

	private static BoardDao instance;
	
	private BoardDao() {
		
	}

	public static BoardDao getInstance() {
		if(instance == null) {
			instance = new BoardDao();
		}
		return instance;
	}
	// borad 테이블의 레코드 개수
	public int getListCount(String items, String text) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		int x = 0; // 레코드 개수 저장할 변수
		
		String sql;
		if(items == null && text == null) {
			sql = "select count(*) from p_board";
		}
		else {
			sql = "select count(*) from p_board "
					+ "where " + items + " like '%" + text + "%'";
		}
		
		try {
			conn = DBConnection.getConnection();
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				x = rs.getInt(1);
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
		return x;
	}
	
	public ArrayList<BoardDto> getBoardList(int pageNum, int limit, String items, String text){
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		int total_record = getListCount(items, text);
		int start = (pageNum - 1) * limit;
		int index = start + 1;
		
		String sql;
		
		if(items == null && text == null) {
			sql = "select * from p_board order by num desc";
		}
		else {
			sql = "select * from p_board "
					+ "where " + items 
					+ " like '%" + text + "%' "
					+ "order by num desc";
		}
		
		ArrayList<BoardDto> boardlist = new ArrayList<BoardDto>();

		try {
			conn = DBConnection.getConnection();
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			while(rs.absolute(index)) {
				BoardDto board = new BoardDto();
				board.setNum(rs.getInt("num"));
				board.setId(rs.getString("id"));
				board.setName(rs.getString("name"));
				board.setSubject(rs.getString("subject"));
				board.setContent(rs.getString("content"));
				board.setRegistDay(rs.getString("regist_day"));
				board.setHit(rs.getInt("hit"));
				board.setIp(rs.getString("ip"));
				boardlist.add(board);
				
				if(index < (start + limit) && index <= total_record) {
					index ++;
				}
				else break;
			}
			return boardlist;
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
		return null;
	}
	
	// id로 name 구하기
	public String getLoginNameById(String id) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		String name = null;
		String sql = "select name from p_member where id = ?";
		
		try {
			conn = DBConnection.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				name = rs.getString("name");
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
		return name;
	}
	
	// 글쓰기
	public void insertBoard(BoardDto board) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		String sql = "insert into p_board "
				+ "(id, name, subject, content, regist_day, hit, ip) "
				+ "values (?, ?, ?, ?, now(), 0, ?)";
		try {
			conn = DBConnection.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, board.getId());
			pstmt.setString(2, board.getName());
			pstmt.setString(3, board.getSubject());
			pstmt.setString(4, board.getContent());
			pstmt.setString(5, board.getIp());
			pstmt.executeUpdate();
			
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
	}
	
	//  게시글 상세보기 하기 위해 읽어오기
	public BoardDto getBoardByNum(int num) {

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		BoardDto dto = new BoardDto();

		String sql = "select * from p_board where `num` = ?";
		try {
			conn = DBConnection.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, num);

			rs = pstmt.executeQuery();
			if (rs.next()) {
				dto.setNum(rs.getInt("num"));
				dto.setId(rs.getString("id"));
				dto.setName(rs.getString("name"));
				dto.setSubject(rs.getString("subject"));
				dto.setContent(rs.getString("content"));
				dto.setRegistDay(rs.getString("regist_day"));
				dto.setHit(rs.getInt("hit"));
				dto.setIp(rs.getString("ip"));

			}

			updateHit(num);

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("getBoardByNum 오류 : " + e);
		} finally {
			try {
				if (pstmt != null) pstmt.close();
				if (conn != null) conn.close();
				if (rs != null) rs.close();
			} catch (Exception e) {
				throw new RuntimeException(e.getMessage());
			}
		}
		return dto;
	}
	//  조회수랑 세트로 해야된다!!! +++++
	//  글을 클릭했을 경우 조회수 올려주기
	private void updateHit(int num) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		BoardDto dto = new BoardDto();

		//      http://localhost:8080/shoppingMall/board/BoardViewAction.do?num=6&pageNum=1
		String sql = "update p_board set `hit` = `hit`+ 1 where `num` = ?";
		try {
			conn = DBConnection.getConnection();
			pstmt = conn.prepareStatement(sql);
			//          pstmt.setInt(1, dto.getHit() + 1);
			pstmt.setInt(1, num);

			pstmt.executeUpdate();


		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("updateHit 오류 : " + e);
		} finally {
			try {
				if (pstmt != null) pstmt.close();
				if (conn != null) conn.close();
			} catch (Exception e) {
				throw new RuntimeException(e.getMessage());
			}
		}
	}
	
	// 글 수정하기
	public void updateBoard(BoardDto board) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		String sql = "update p_board "
				+ "set name = ?, subject = ?, content = ? "
				+ "where num = ?";
		try {
			conn = DBConnection.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, board.getName());
			pstmt.setString(2, board.getSubject());
			pstmt.setString(3, board.getContent());
			pstmt.setInt(4, board.getNum());
			pstmt.executeUpdate();
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (pstmt != null) pstmt.close();
				if (conn != null) conn.close();
			} catch (Exception e) {
				throw new RuntimeException(e.getMessage());
			}
		}
	}
	
	// 글 삭제하기
	public void deleteBoard(int num) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		String sql = "delete from p_board where num = ?";
		
		try {
			conn = DBConnection.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, num);
			pstmt.executeUpdate();
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (pstmt != null) pstmt.close();
				if (conn != null) conn.close();
			} catch (Exception e) {
				throw new RuntimeException(e.getMessage());
			}
		}
	}
}
