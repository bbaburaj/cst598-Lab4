package views;

import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.NewsItemBean;

public class DeleteView extends HttpServlet{
	public void service(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession(false);
		List<NewsItemBean> newsArray = (List<NewsItemBean>) session.getAttribute("mutableNews");
		int mutableNewsId = Integer.parseInt(request.getParameter("itemId"));
		for(NewsItemBean news:newsArray){
			if(news.getItemId() == mutableNewsId){
				deleteNews(request,response,news, session);
			}
		}
	}
		private void deleteNews(HttpServletRequest request,
				HttpServletResponse response, NewsItemBean news, HttpSession session) {
			PrintWriter out = null;
			try {
				response.setContentType("text/html");
				out = response.getWriter();
				out.write("<html>\r\n");
				out.write("<head>\r\n");
				out.write("<title>Delete News</title>\r\n");
				out.write("</head>\r\n");
				out.write("<body bgcolor=\"#E6E6FA\">\r\n");
				out.write("<h2> Would you like to delete this article?</h2>");
				out.write("<form method=\"post\" action=\"news\">");
				out.write("<input type=\"hidden\" name=\"action\" value=\"deletePage\"");
				session.setAttribute("newsToDelete", news);
				out.write("<h2>"+news.getItemTitle()+"</h2>");
				out.write("<h3>"+news.getItemStory()+"</h3>");
				out.write("<br>");
				out.write("<input type=\"submit\" name=\"delete\" value=\"Yes\">");
				out.write("<input type=\"submit\" name=\"delete\" value=\"No\">");
				out.close();
			} catch (Exception e) {

			}
	}
}
