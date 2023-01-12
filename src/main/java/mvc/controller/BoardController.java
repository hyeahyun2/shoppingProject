package mvc.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import market.dao.BoardDao;
import market.dto.BoardDto;

public class BoardController extends HttpServlet {
	static final int LISTCOUNT = 5; // 페이지당 게시물 수

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req, resp); // post로 넘기기
	}
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String RequestURI = request.getRequestURI(); // 전체 경로
		String contextPath = request.getContextPath(); // 프로젝트 Path
		// 전체 경로에서 프로젝트 Path 길이 만큼의 인덱스 이후 문자열
		String command = RequestURI.substring(contextPath.length());
		
		response.setContentType("text/html; charset=utf-8");
		request.setCharacterEncoding("utf-8");
		
		if(command.contains("/BoardListAction.do")) { // 등록된 글 목록 페이지 출력
			reqeustBoardList(request);
			RequestDispatcher rd = request.getRequestDispatcher("../board/list.jsp");
			rd.forward(request, response);
		}
		else if(command.contains("/BoardWriteForm.do")) { // 글 등록 페이지 출력
			requestLoginName(request);
			RequestDispatcher rd = request.getRequestDispatcher("../board/writeFurm.jsp");
			rd.forward(request, response);
		}
		else if(command.contains("/BoardWriteAction.do")) { // 새로운 글 등록
			requestBoardWrite(request);
			RequestDispatcher rd = request.getRequestDispatcher("../board/BoardListAction.do");
			rd.forward(request, response);
		}
		
	}
	
	// 등록된 글 목록 가져오기
	public void reqeustBoardList(HttpServletRequest request) {
		BoardDao dao = BoardDao.getInstance();
		List<BoardDto> boardlist = new ArrayList<BoardDto>();
		
		int pageNum = 1; // 페이지 번호가 전달되지 않았을 때의 페이지 번호
		int limit = LISTCOUNT; // 페이지당 게시물 수
		
		if(request.getParameter("pageNum") != null) { // 페이지 번호가 전달된 경우
			pageNum = Integer.parseInt(request.getParameter("pageNum"));
		}
		String items = request.getParameter("items"); // 검색 필드
		String text = request.getParameter("text"); // 검색어
		
		int total_record = dao.getListCount(items, text); // 전체 게시물 수
		boardlist = dao.getBoardList(pageNum, limit, items, text); // 현재 페이지에 해당되는 목록 데이터
		
		int total_page;
		
		if(total_record % limit == 0) { // 전체 게시물이 limit의 배수일 때
			total_page = total_record/limit;
			Math.floor(total_page);
		}
		else {
			total_page = total_record/limit;
			Math.floor(total_page);
			total_page = total_page + 1;
		}
		
		request.setAttribute("pageNum", pageNum); // 페이지 번호
		request.setAttribute("total_page", total_page); // 전체 페이지 수
		request.setAttribute("total_record", total_record); // 전체 게시물 수
		request.setAttribute("boardlist", boardlist); // 현재 페이지에 해당되는 목록 데이터
	}

	// 인증된 사용자명 가져오기
	public void requestLoginName(HttpServletRequest request) {
		String id = request.getParameter("id");
		
		BoardDao dao = BoardDao.getInstance();
		
		String name = dao.getLoginNameById(id);
		
		request.setAttribute("name", name);
	}
	
	public void requestBoardWrite(HttpServletRequest request) {
		BoardDao dao = BoardDao.getInstance();
		
		BoardDto board = new BoardDto();
		board.setId(request.getParameter("id"));
		board.setName(request.getParameter("name"));
		board.setSubject(request.getParameter("subject"));
		board.setContent(request.getParameter("content"));
		board.setIp(request.getRemoteAddr());
		
		System.out.println(request.getParameter("name"));
		System.out.println(request.getParameter("subject"));
		System.out.println(request.getParameter("content"));
		
		dao.insertBoard(board);
	}
}
