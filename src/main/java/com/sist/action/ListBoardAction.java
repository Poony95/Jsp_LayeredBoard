package com.sist.action;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sist.dao.BoardDAO;

public class ListBoardAction implements SistAction{

	@Override
	public String pro(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		BoardDAO dao = BoardDAO.getInstance();
		int pageNUM = 1;
		if(request.getParameter("pageNUM") != null){
			pageNUM = Integer.parseInt(request.getParameter("pageNUM"));
		}
		
		System.out.println("pageNUM:"+pageNUM);
		request.setAttribute("list", dao.findAll(pageNUM));
		request.setAttribute("totalPage", dao.totalPage);
		return "listBoard.jsp";
	}

}










