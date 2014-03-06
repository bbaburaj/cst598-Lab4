package handler;

import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.NewsDAO;
import dao.NewsDAOFactory;
import model.NewsItemBean;

public class DeleteHandler implements ActionHandler{

	@Override
	public String handleIt(Map<String, String[]> params, HttpSession session, HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		NewsDAO dao = (NewsDAO) NewsDAOFactory.getTheDAO();
		NewsItemBean newsToDelete = (NewsItemBean) session.getAttribute("newsToDelete");
		dao.deleteNewsItem(newsToDelete.getItemId());
		session.setAttribute("newsToDelete", null);
		return "confirm";
	}

}
