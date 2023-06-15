package com.sist.controller;

import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Properties;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sist.action.SistAction;

/**
 * Servlet implementation class SistController
 */
@WebServlet("*.do")
public class SistController extends HttpServlet {
	
	HashMap<String, SistAction> map;
	
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SistController() {
        super();
        // TODO Auto-generated constructor stub
    }

    
    
    
	@Override
	public void init(ServletConfig config) throws ServletException {
		// TODO Auto-generated method stub
		super.init(config);
		map = new HashMap<String, SistAction>();
		String path = config.getServletContext().getRealPath("WEB-INF");
		try {
			FileReader fr = 
			new FileReader(path +"/sist.properties");
			Properties prop = new Properties();
			prop.load(fr);
			Iterator key_list= prop.keySet().iterator();
			while(key_list.hasNext()) {
				String key = (String)key_list.next();
				String clsName = prop.getProperty(key);
				System.out.println("key:"+key);
				System.out.println("clsName:"+clsName);
				System.out.println("----------------------");
				SistAction action= 
				(SistAction)Class.forName(clsName).newInstance();
				map.put(key, action);
			}
			System.out.println(map);
			fr.close();
		}catch (Exception e) {
			System.out.println("예외발생:"+e.getMessage());
		}
	}




	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		pro(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		pro(request, response);
	}


	private void pro(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		// TODO Auto-generated method stub
		String uri = request.getRequestURI();
		String cmd = uri.substring(uri.lastIndexOf("/")+1);
		SistAction action = map.get(cmd);
		String view  = action.pro(request, response);
		RequestDispatcher dispatcher
		= request.getRequestDispatcher(view);
		dispatcher.forward(request, response);
	}

}








