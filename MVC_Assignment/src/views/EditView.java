package views;

import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.NewsDAO;
import dao.NewsDAOFactory;
import model.NewsItemBean;

public class EditView extends HttpServlet{
	public void service(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession(false);
		NewsDAO dao = (NewsDAO) NewsDAOFactory.getTheDAO();
		List<NewsItemBean> newsArray = (List<NewsItemBean>) session.getAttribute("mutableNews");
		int mutableNewsId = Integer.parseInt(request.getParameter("itemId"));
		boolean addComment = !request.getParameter("editOrDelete").equals("Edit");
		String comment = request.getParameter("newsComment");
		for(NewsItemBean news:newsArray){
			if(news.getItemId() == mutableNewsId){
				if(addComment){
					commentNews(response, news,dao, comment);
				}else{
					editNews(request,response,news, session);
				}
			}
		}
	}
	
	private void commentNews(HttpServletResponse response, NewsItemBean news,NewsDAO dao, String comment){
		dao.storeComment(news.getItemId(), news.getReporterId(), comment);
		PrintWriter out = null;
		try {
			response.setContentType("text/html");
			out = response.getWriter();
			out.write("<html>\r\n");
			out.write("<head>\r\n");
			out.write("<title>Edit News</title>\r\n");
			out.write("</head>\r\n");
			out.write("<body bgcolor=\"#E6E6FA\">\r\n");
			out.write("<h2>Comment Added Successfully!</h2>");
			out.write("<br><a href=\"./news?action=view\">View News</a>");
			out.write("</body></html>");
		}catch (Exception e) {
			out.write("Error commenting on news");
		}
		
	}

	private void editNews(HttpServletRequest request,
			HttpServletResponse response, NewsItemBean news, HttpSession session) {
		PrintWriter out = null;
		try {
			response.setContentType("text/html");
			out = response.getWriter();
			out.write("<html>\r\n");
			out.write("<head>\r\n");
			out.write("<title>Edit News</title>\r\n");
			out.write("</head>\r\n");
			out.write("<body bgcolor=\"#E6E6FA\">\r\n");
			out.write("<form method=\"post\" action=\"news\">");
			out.write("<input type=\"hidden\" name=\"action\" value=\"editPage\"");
			session.setAttribute("newsToEdit", news);
			out.write("<h2> Edit Story </h2>");
			out.write("<input type=\"text\" name=\"newsTitle\" value=\""+news.getItemTitle()+"\"/>");
			out.write("<br>");
			out.write("<input type=\"text\" name=\"newsStory\" value=\""+news.getItemStory()+"\"/>");
			out.write("<br>");
			out.write("<input type=\"submit\" value=\"Submit\">");
			out.close();
		} catch (Exception e) {
			out.write("Error editing news");
		}
	}
}
