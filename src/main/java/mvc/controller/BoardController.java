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
	static final int LISTCOUNT = 5; // �������� �Խù� ��
	private String boardName = "board";

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req, resp); // post�� �ѱ��
	}
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String RequestURI = request.getRequestURI(); // ��ü ���
		String contextPath = request.getContextPath(); // ������Ʈ Path
		// ��ü ��ο��� ������Ʈ Path ���� ��ŭ�� �ε��� ���� ���ڿ�
		String command = RequestURI.substring(contextPath.length());
		
		response.setContentType("text/html; charset=utf-8");
		request.setCharacterEncoding("utf-8");
		
		if(command.contains("/BoardListAction.do")) { // ��ϵ� �� ��� ������ ���
			reqeustBoardList(request);
			RequestDispatcher rd = request.getRequestDispatcher("../board/list.jsp");
			rd.forward(request, response);
		}
		else if(command.contains("/BoardWriteForm.do")) { // �� ��� ������ ���
			requestLoginName(request);
			RequestDispatcher rd = request.getRequestDispatcher("../board/writeFurm.jsp");
			rd.forward(request, response);
		}
		else if(command.contains("/BoardWriteAction.do")) { // ���ο� �� ���
			requestBoardWrite(request);
			RequestDispatcher rd = request.getRequestDispatcher("../board/BoardListAction.do");
			rd.forward(request, response);
		}
		else if (command.contains("/BoardViewAction.do")) {   //���õ� �� �� ������ ��������
            RequestBoardView(request);
            //RequestRippleList(request);
            RequestDispatcher rd = request.getRequestDispatcher("../board/BoardView.do");
            rd.forward(request, response);
        } else if (command.contains("/BoardView.do")) {  // �� �� ������ ����ϱ�.
            RequestDispatcher rd = request.getRequestDispatcher("../board/view.jsp");
            rd.forward(request, response);
        }
        else if(command.contains("/BoardEditAction.do")) { // �� ���� ������ �̵�
        	RequestBoardView(request);
        	RequestDispatcher rd = request.getRequestDispatcher("../board/edit.jsp");
        	rd.forward(request, response);
        }
        else if(command.contains("/BoardUpdateAction.do")) { // �� �����ϱ�
        	requestBoardUpdate(request);
        	RequestDispatcher rd = request.getRequestDispatcher("../board/BoardListAction.do");
        	rd.forward(request, response);
        }
        else if(command.contains("/BoardDeleteAction.do")) { // ���õ� �� ����
        	requestBoardDelete(request);
        	RequestDispatcher rd = request.getRequestDispatcher("../board/BoardListAction.do");
        	rd.forward(request, response);
        }
        else if(command.contains("RippleListAction.do")) { // ���� ����Ʈ ��������
        	requestRippleList(request, response);
        }
        else if(command.contains("/RippleWriteAction.do")) { // ���� �ۼ�
        	requestRippleWrite(request, response);
        }
        else if(command.contains("/RippleDeleteAction.do")) {
        	RequestRippleDelete(request, response);
        }
        else {
        	System.out.println("out : " + command);
        	PrintWriter out = response.getWriter();
        	out.append("<html><body><h2>�߸��� ����Դϴ�.(" + command + "</h2><hr>");
        }
	}
	
	// ��ϵ� �� ��� ��������
	public void reqeustBoardList(HttpServletRequest request) {
		BoardDao dao = BoardDao.getInstance();
		List<BoardDto> boardlist = new ArrayList<BoardDto>();
		
		int pageNum = 1; // ������ ��ȣ�� ���޵��� �ʾ��� ���� ������ ��ȣ
		int limit = LISTCOUNT; // �������� �Խù� ��
		
		if(request.getParameter("pageNum") != null) { // ������ ��ȣ�� ���޵� ���
			pageNum = Integer.parseInt(request.getParameter("pageNum"));
		}
		String items = request.getParameter("items"); // �˻� �ʵ�
		String text = request.getParameter("text"); // �˻���
		
		int total_record = dao.getListCount(items, text); // ��ü �Խù� ��
		boardlist = dao.getBoardList(pageNum, limit, items, text); // ���� �������� �ش�Ǵ� ��� ������
		
		int total_page;
		
		if(total_record % limit == 0) { // ��ü �Խù��� limit�� ����� ��
			total_page = total_record/limit;
			Math.floor(total_page);
		}
		else {
			total_page = total_record/limit;
			Math.floor(total_page);
			total_page = total_page + 1;
		}
		
		request.setAttribute("limit", limit); // �������� �Խù� ��
		request.setAttribute("pageNum", pageNum); // ������ ��ȣ
		request.setAttribute("total_page", total_page); // ��ü ������ ��
		request.setAttribute("total_record", total_record); // ��ü �Խù� ��
		request.setAttribute("boardlist", boardlist); // ���� �������� �ش�Ǵ� ��� ������
	}

	// ������ ����ڸ� ��������
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
	//  �������� �� �о���� select
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
	
	// ���õ� �� ���� �����ϱ�
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
	
	// ���õ� �� �����ϱ�
	public void requestBoardDelete(HttpServletRequest request) {
		int num = Integer.parseInt(request.getParameter("num"));
		int pageNum = Integer.parseInt(request.getParameter("pageNum"));
		
		BoardDao dao = BoardDao.getInstance();
		dao.deleteBoard(num);
	}
	
	// ���� �ۼ�
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
	// ���� �ۼ�
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
	
	// ���� ����Ʈ ��������
	public void RequestRippleList(HttpServletRequest req) {
		RippleDao dao = RippleDao.getInstance();
		List<RippleDto> rippleList = new ArrayList<>();
		int num = Integer.parseInt(req.getParameter("num"));
		
		rippleList = dao.getRippleList(this.boardName, num);
		
		req.setAttribute("rippleList", rippleList);
	}
	// ���� ����Ʈ ��������
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
	
	// ���� ����
	public void RequestRippleDelete(HttpServletRequest req) {
		int rippleId = Integer.parseInt(req.getParameter("rippleId"));
		
		RippleDao dao = RippleDao.getInstance();
		RippleDto ripple = new RippleDto();
		ripple.setRippleId(rippleId);
		dao.deleteRipple(ripple);
	}
	
	// ���� ����
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
