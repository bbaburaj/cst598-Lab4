package handler;

import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.NewsDAO;
import dao.NewsDAOFactory;
import model.UserBean;
import model.NewsItemBean;

public class AddNewsHandler implements ActionHandler {

	@Override
	public String handleIt(Map<String, String[]> params, HttpSession session, HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		NewsDAO dao = (NewsDAO) NewsDAOFactory.getTheDAO();
		UserBean user = (UserBean) session.getAttribute("user");
		boolean isPublic = params.get("newsType")[0].equals("public");
		String id=user.getUserId();;
		String userId = (isPublic)?"publicRep"+id:id;
		dao.createNewsItem(new NewsItemBean(params.get("newsTitle")[0], params.get("newsBody")[0], userId), user.getUserId());
		return "success";
	}

}