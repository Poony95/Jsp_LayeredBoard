
package com.sist.action;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.oreilly.servlet.MultipartRequest;
import com.sist.dao.BoardDAO;
import com.sist.vo.BoardVO;

public class InsertBoardOKAction implements SistAction {

	@Override
	public String pro(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		BoardDAO dao = BoardDAO.getInstance();
		
		int no = dao.getNextNo();
		int b_ref = no;
		int b_level = 0;
		int b_step = 0;
		request.setCharacterEncoding("utf-8");
		String path = request.getRealPath("board");
		System.out.println("path:"+path);
		MultipartRequest multi =
		new MultipartRequest(request, path, 1024*1024*5, "utf-8");
		
		int pno = Integer.parseInt(multi.getParameter("no"));
		//부모글의 글번호를 갖고 옵니다.
		//새글 : 0
		//답글 : 0이 아닌값(부모글의 글번호)
		
		//만약 답글작성이면
		if(pno != 0) {
			BoardVO p= dao.findByNo(pno, false);
			b_ref = p.getB_ref();
			b_level = p.getB_level();
			b_step = p.getB_step();
			dao.updateStep(b_ref, b_step);
			b_level++;
			b_step++;
		}
		
		BoardVO b = new BoardVO();
		b.setNo(no);
		b.setTitle(multi.getParameter("title"));
		b.setWriter(multi.getParameter("writer"));
		b.setPwd(multi.getParameter("pwd"));
		b.setContent(multi.getParameter("content"));
		b.setFname(multi.getFilesystemName("fname"));
		b.setB_ref(b_ref);
		b.setB_level(b_level);
		b.setB_step(b_step);
		
		int re = dao.insertBoard(b);
		if(re == 1) {
			request.setAttribute("msg", "등록성공");
		}else {
			request.setAttribute("msg", "등록실패");
		}
		
		
		return "insertBoardOK.jsp";
	}

}










