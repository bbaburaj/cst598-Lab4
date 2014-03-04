package handler;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;

import model.CommentBean;
import model.NewsItemBean;
import model.UserBean;
import model.UserBean.Role;
import dao.NewsDAO;
import dao.NewsDAOFactory;

public class ViewNewsHandler implements ActionHandler {
	
	NewsDAO dao = (NewsDAO) NewsDAOFactory.getTheDAO();
	public String handleIt(Map params, HttpSession session) throws IOException {
		NewsItemBean[] news = dao.getNews();
		UserBean user = (UserBean) session.getAttribute("user");
		List<NewsItemBean> mutableNews = new ArrayList<NewsItemBean>();
		boolean[] canEdit = new boolean[news.length];
		boolean[] canComment = new boolean[news.length];
		boolean[] canView = new boolean[news.length];
		boolean[] isPublicNews = new boolean[news.length];
		List<CommentBean[]> comments =  new ArrayList<CommentBean[]>();;
		for (int i = 0; i < news.length; i++) {
			NewsItemBean it = news[i];
			if (it == null)
				continue;
			String itemReporter = it.getReporterId();
			Role role = Role.GUEST;
			String userId = "";
			if (user != null) {
				role = user.getRole();
				userId = user.getUserId();
			}
			canComment[i] = (role == Role.SUBSCRIBER)
					|| (itemReporter.equals(userId));
			canEdit[i] = user != null && (role == Role.REPORTER);
			canView[i] = itemReporter.equals("public")
					|| (user != null && ((role == Role.SUBSCRIBER) || itemReporter
							.equals(userId)));
			isPublicNews[i] = !itemReporter.equals("public");
			comments.add(it.getComments());
				if (canEdit[i] || canComment[i]) {
					mutableNews.add(it);
				}
			
		}
		session.setAttribute("canView", canView);
		session.setAttribute("canEdit",canEdit);
		session.setAttribute("canComment", canComment);
		session.setAttribute("mutableNews", mutableNews);
		session.setAttribute("news", news);
		session.setAttribute("isPublic", isPublicNews);
		session.setAttribute("comments", comments);
		return "view";
	}

}