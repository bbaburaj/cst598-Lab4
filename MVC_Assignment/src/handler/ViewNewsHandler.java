package handler;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.ServletContext;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.CommentBean;
import model.NewsItemBean;
import model.UserBean;
import model.UserBean.Role;
import dao.NewsDAO;
import dao.NewsDAOFactory;

public class ViewNewsHandler implements ActionHandler {
	
	NewsDAO dao = (NewsDAO) NewsDAOFactory.getTheDAO();
	public static String guestId = null;
	public String handleIt(Map params, HttpSession session, HttpServletRequest request, HttpServletResponse response) throws IOException {
		NewsItemBean[] news = dao.getNews();
		UserBean user = (UserBean) session.getAttribute("user");
		List<NewsItemBean> mutableNews = new ArrayList<NewsItemBean>();
		
		boolean[] canEdit = new boolean[news.length];
		boolean[] canComment = new boolean[news.length];
		boolean[] canView = new boolean[news.length];
		boolean[] isPublicNews = new boolean[news.length];
		boolean[] canEditSorted = new boolean[news.length];
		boolean[] canCommentSorted = new boolean[news.length];
		boolean[] isPublicNewsSorted = new boolean[news.length];
		boolean isReporter = false;
		List<CommentBean[]> comments =  new ArrayList<CommentBean[]>();
		List<CommentBean[]> commentsSorted =  new ArrayList<CommentBean[]>();
		List<NewsItemBean> favorites = new ArrayList<NewsItemBean>();
		List<NewsItemBean> notFavorite = new ArrayList<NewsItemBean>();
		
		if(user == null){
			guestId = processCookie(request, "guestId");
			if (guestId == null) {
				guestId = generateUniqueId();
				Cookie c = new Cookie("guestId", guestId);
				// Favorites of guests could be stored for three minutes
				c.setMaxAge(180);
				response.addCookie(c);
			}
			favorites = dao.getFavorites(guestId);
		}
		if(user!=null){
			favorites = dao.getFavorites(user.getUserId());
			isReporter = (user.getRole() == Role.REPORTER);
		}
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
					|| (itemReporter.equals(userId)) || itemReporter.equals("publicRep"+userId);
			canEdit[i] = user != null && (role == Role.REPORTER) && (itemReporter.equals(userId) || itemReporter.equals("publicRep"+userId));
			canView[i] = itemReporter.equals("public") || itemReporter.contains("publicRep")
					|| (user != null && ((role == Role.SUBSCRIBER) || itemReporter
							.equals(userId) || itemReporter.equals("publicRep"+userId)));
			isPublicNews[i] = !itemReporter.equals("public");
			comments.add(it.getComments());
				if (canEdit[i] || canComment[i]) {
					mutableNews.add(it);
				}
			if(!favorites.contains(news[i]) && canView[i]){
					notFavorite.add(news[i]);
				}
			
		}
		NewsItemBean[] sortedNews = new NewsItemBean[news.length];
		for(int i=0;i<favorites.size();i++){
			NewsItemBean newsItemBean = favorites.get(i);
			int position = Arrays.asList(news).indexOf(newsItemBean);
			sortedNews[i] = newsItemBean;
			canEditSorted[i] =  canEdit[position];
			canCommentSorted[i] = canComment[position];
			isPublicNewsSorted[i] = isPublicNews[position];
			commentsSorted.add(newsItemBean.getComments());
		}
		int size = favorites.size();
		for(int j=0;j<notFavorite.size();j++){
			NewsItemBean newsItemBean = notFavorite.get(j);
			int position = Arrays.asList(news).indexOf(newsItemBean);
			sortedNews[size] = newsItemBean;
			
			canEditSorted[size] =  canEdit[position];
			canCommentSorted[size] = canComment[position];
			isPublicNewsSorted[size] = isPublicNews[position];
			commentsSorted.add(newsItemBean.getComments());
			size++;
		}
		session.setAttribute("canEdit",canEditSorted);
		session.setAttribute("canComment", canCommentSorted);
		session.setAttribute("mutableNews", mutableNews);
		session.setAttribute("news", sortedNews);
		session.setAttribute("isPublic", isPublicNewsSorted);
		session.setAttribute("comments", commentsSorted);
		session.setAttribute("favorites", favorites);
		session.setAttribute("canAddNews", isReporter);
		return "view";
	}
	
	public static String generateUniqueId() throws UnsupportedEncodingException{
		UUID uid = UUID.randomUUID();  
		String id = URLEncoder.encode(uid.toString(),"UTF-8");
		return id;
	}
	
	public static String processCookie(HttpServletRequest req, String cookieName) {
	    Cookie[] cookies = req.getCookies();
	    if (cookies != null) {
	      for (int i = 0; i < cookies.length; i++) {
	        if (cookies[i].getName().equals(cookieName)) {
	          return cookies[i].getValue();
	        }
	      }
	    }
	    return null;
	}

}