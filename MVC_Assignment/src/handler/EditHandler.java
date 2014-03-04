package handler;

import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpSession;

import dao.NewsDAO;
import dao.NewsDAOFactory;
import model.NewsItemBean;

public class EditHandler implements ActionHandler{

	@Override
	public String handleIt(Map<String, String[]> params, HttpSession session)
			throws IOException {
		NewsDAO dao = (NewsDAO) NewsDAOFactory.getTheDAO();
		NewsItemBean newsToEdit = (NewsItemBean) session.getAttribute("newsToEdit");
		String newStory = ((String[]) params.get("newsStory"))[0];
		String newTitle =  ((String[]) params.get("newsTitle"))[0];
		dao.updateNewsItem(newsToEdit.getItemId(), newTitle, newStory);
		session.setAttribute("newsToEdit", null);
		return "confirm";
	}

}
