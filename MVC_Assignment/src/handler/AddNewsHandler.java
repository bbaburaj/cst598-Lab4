package handler;

import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpSession;

import dao.NewsDAO;
import dao.NewsDAOFactory;
import model.UserBean;
import model.NewsItemBean;

public class AddNewsHandler implements ActionHandler {

	@Override
	public String handleIt(Map<String, String[]> params, HttpSession session)
			throws IOException {
		NewsDAO dao = (NewsDAO) NewsDAOFactory.getTheDAO();
		UserBean user = (UserBean) session.getAttribute("user");
		boolean isPublic = params.get("newsType")[0].equals("public");
		String userId = (isPublic)?"public":user.getUserId();
		dao.createNewsItem(new NewsItemBean(params.get("newsTitle")[0], params.get("newsBody")[0], userId), user.getUserId());getClass();
		return "success";
	}

}