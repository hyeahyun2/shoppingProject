package mvc.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import market.dao.BoardDao;
import market.dao.RippleDao;
import market.dto.BoardDto;
import market.dto.RippleDto;

public class BoardController extends HttpServlet {
	static final int LISTCOUNT = 5; // 페이지당 게시물 수
	private String boardName = "board";

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
		else if (command.contains("/BoardViewAction.do")) {   //선택된 글 상세 페이지 가져오기
            RequestBoardView(request);
            //RequestRippleList(request);
            RequestDispatcher rd = request.getRequestDispatcher("../board/BoardView.do");
            rd.forward(request, response);
        } else if (command.contains("/BoardView.do")) {  // 글 상세 페이지 출력하기.
            RequestDispatcher rd = request.getRequestDispatcher("../board/view.jsp");
            rd.forward(request, response);
        }
        else if(command.contains("/BoardEditAction.do")) { // 글 수정 페이지 이동
        	RequestBoardView(request);
        	RequestDispatcher rd = request.getRequestDispatcher("../board/edit.jsp");
        	rd.forward(request, response);
        }
        else if(command.contains("/BoardUpdateAction.do")) { // 글 수정하기
        	requestBoardUpdate(request);
        	RequestDispatcher rd = request.getRequestDispatcher("../board/BoardListAction.do");
        	rd.forward(request, response);
        }
        else if(command.contains("/BoardDeleteAction.do")) { // 선택된 글 삭제
        	requestBoardDelete(request);
        	RequestDispatcher rd = request.getRequestDispatcher("../board/BoardListAction.do");
        	rd.forward(request, response);
        }
        else if(command.contains("RippleListAction.do")) { // 리플 리스트 가져오기
        	requestRippleList(request, response);
        }
        else if(command.contains("/RippleWriteAction.do")) { // 리플 작성
        	requestRippleWrite(request, response);
        }
        else if(command.contains("/RippleDeleteAction.do")) {
        	RequestRippleDelete(request, response);
        }
        else {
        	System.out.println("out : " + command);
        	PrintWriter out = response.getWriter();
        	out.append("<html><body><h2>잘못된 경로입니다.(" + command + "</h2><hr>");
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
		
		request.setAttribute("limit", limit); // 페이지당 게시물 수
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
	//  상세페이지 글 읽어오기 select
	public void RequestBoardView(HttpServletRequest req) {
		BoardDao dao = BoardDao.getInstance();
		int num = Integer.parseInt(req.getParameter("num"));
		int pageNum = Integer.parseInt(req.getParameter("pageNum"));

		BoardDto board = new BoardDto();
		board = dao.getBoardByNum(num);

		req.setAttribute("num", num);
		req.setAttribute("page", pageNum);
		req.setAttribute("board", board);
	}
	
	// 선택된 글 내용 수정하기
	public void requestBoardUpdate(HttpServletRequest request) {
		int num = Integer.parseInt(request.getParameter("num"));
		int pageNum = Integer.parseInt(request.getParameter("pageNum"));
		
		BoardDao dao = BoardDao.getInstance();
		
		BoardDto board = new BoardDto();
		board.setNum(num);
		board.setName(request.getParameter("name"));
		board.setSubject(request.getParameter("subject"));
		board.setContent(request.getParameter("content"));
		
		dao.updateBoard(board);
	}
	
	// 선택된 글 삭제하기
	public void requestBoardDelete(HttpServletRequest request) {
		int num = Integer.parseInt(request.getParameter("num"));
		int pageNum = Integer.parseInt(request.getParameter("pageNum"));
		
		BoardDao dao = BoardDao.getInstance();
		dao.deleteBoard(num);
	}
	
	// 리플 작성
	public void requestRippleWrite(HttpServletRequest req) {
		int num = Integer.parseInt(req.getParameter("num"));
		int pageNum = Integer.parseInt(req.getParameter("pageNum"));
		String memberId = (String)req.getSession().getAttribute("sessionId");
		String name = req.getParameter("name");
		String content = req.getParameter("content");

		RippleDao dao = RippleDao.getInstance();
		
		RippleDto ripple = new RippleDto();
		ripple.setBoardName(boardName);
		ripple.setBoardNum(num);
		ripple.setMemberId(memberId);
		ripple.setName(name);
		ripple.setContent(content);
		ripple.setIp(req.getRemoteAddr());
		
		dao.insertRipple(ripple);

		req.setAttribute("num", num);
		req.setAttribute("page", pageNum);
	}
	// 리플 작성
	public void requestRippleWrite(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		req.setCharacterEncoding("utf-8");


		RippleDao dao = RippleDao.getInstance();
		RippleDto ripple = new RippleDto();
		ripple.setBoardName(req.getParameter("boardName"));
		ripple.setBoardNum(Integer.parseInt(req.getParameter("num")));
		ripple.setMemberId((String)req.getSession().getAttribute("sessionId"));
		ripple.setName(req.getParameter("name"));
		ripple.setContent(req.getParameter("content"));
		ripple.setIp(req.getRemoteAddr());

		String result = "{\"result\" : ";
		if(dao.insertRipple(ripple)){
		result += "\"true\"}";
		}
		else {
			result += "\"false\"}";
		}
		PrintWriter out = resp.getWriter();
		out.append(result);
	}
	
	// 리플 리스트 가져오기
	public void RequestRippleList(HttpServletRequest req) {
		RippleDao dao = RippleDao.getInstance();
		List<RippleDto> rippleList = new ArrayList<>();
		int num = Integer.parseInt(req.getParameter("num"));
		
		rippleList = dao.getRippleList(this.boardName, num);
		
		req.setAttribute("rippleList", rippleList);
	}
	// 리플 리스트 가져오기
	public void requestRippleList(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		String sessionId = (String)req.getSession().getAttribute("sessionId");
		String boardName = req.getParameter("boardName");
		int num = Integer.parseInt(req.getParameter("num"));
		
		RippleDao dao = RippleDao.getInstance();

		ArrayList<RippleDto> list = dao.getRippleList(boardName, num);

		StringBuilder result = new StringBuilder("{\"listData\" : [");
		
		int i = 0;
		for(RippleDto dto : list) {
			boolean flag = sessionId != null && sessionId.equals(dto.getMemberId()) ?
					true : false;
			result.append("{\"rippleId\" : \"" + dto.getRippleId() + "\", ")
				.append("\"name\" : \"" + dto.getName() + "\", ")
				.append("\"content\" : \"" + dto.getContent() + "\", ")
				.append("\"isWriter\" : \"" + flag + "\"}");
			
			if(i++ < list.size() - 1) {
				result.append(", ");
			}
		}
		result.append("]}");
		System.out.println(result.toString());
		
		PrintWriter out = resp.getWriter();
		out.append(result.toString());
		
	}
	
	// 리플 삭제
	public void RequestRippleDelete(HttpServletRequest req) {
		int rippleId = Integer.parseInt(req.getParameter("rippleId"));
		
		RippleDao dao = RippleDao.getInstance();
		RippleDto ripple = new RippleDto();
		ripple.setRippleId(rippleId);
		dao.deleteRipple(ripple);
	}
	
	// 리플 삭제
	public void RequestRippleDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		int rippleId = Integer.parseInt(req.getParameter("rippleId"));

		RippleDao dao = RippleDao.getInstance();
		RippleDto ripple = new RippleDto();
		ripple.setRippleId(rippleId);
		
		String result = "{\"result\" : ";
		if(dao.deleteRipple(ripple)){
		result += "\"true\"}";
		}
		else {
			result += "\"false\"}";
		}
		System.out.println(result.toString());

		PrintWriter out = resp.getWriter();
		out.append(result);
	}
}
