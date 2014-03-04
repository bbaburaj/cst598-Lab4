package handler;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;

import dao.NewsDAO;
import dao.NewsDAOFactory;

public class LoginHandler implements ActionHandler {
	
	NewsDAO dao = (NewsDAO) NewsDAOFactory.getTheDAO();
	public String handleIt(Map params, HttpSession session) throws IOException {
		ServletContext sc = session.getServletContext();
		String user_file = (String)session.getAttribute("userfile");
		InputStream is = sc.getResourceAsStream(user_file);
		dao.getDataFromResource(is);
		return "validate";
	}

}