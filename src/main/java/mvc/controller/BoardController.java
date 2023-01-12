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
	static final int LISTCOUNT = 5; // �������� �Խù� ��

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
}
