package views;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.NewsItemBean;
import model.UserBean;
import dao.NewsDAO;
import dao.NewsDAOFactory;

public class NewsView extends HttpServlet {
	public void service(HttpServletRequest request, HttpServletResponse response) throws IOException {
		NewsDAO dao = (NewsDAO) NewsDAOFactory.getTheDAO();
		NewsItemBean[] news = dao.getNews();
		HttpSession session = request.getSession(false);
		PrintWriter out = null;
		List<NewsItemBean> mutableNews = new ArrayList<NewsItemBean>();
		try {
			response.setContentType("text/html");
			out = response.getWriter();
			out.write("<html>\r\n");
			out.write("<head>\r\n");
			out.write("<title>View News</title>\r\n");
			out.write("</head>\r\n");
			
			if (session != null) {
				out.write("<body bgcolor=\"#E6E6FA\">\r\n");
				UserBean user = (UserBean) session.getAttribute("user");
				out.write("<script language=\"javascript\">");
				out.write("function toggle(id1,id2,text) {");
				out.write("var e1 = document.getElementById(id1);");
				out.write("var e2 = document.getElementById(id2);");
				out.write("    if(e1.style.display == \"block\") {");
				out.write("e1.style.display = \"none\";");
				out.write("e2.innerHTML = text;");
				out.write("   }");
				out.write("   else {");
				out.write("       e1.style.display = \"block\";");
				out.write("        e2.innerHTML = \"Hide Story\";");
				out.write("    }");
				out.write("} ");
				out.write("</script>");
				out.write("\r\n");
				out.write("<h1 align=center> The latest news </h1>");
				out.write("<a href=\"./add\">Add News</a><br>");
				for (int i = 0; i < news.length; i++) {
					NewsItemBean it = news[i];
					if(it == null) continue;
					String itemTitle = it.getItemTitle();
					String itemStory = it.getItemStory();
					String itemReporter = it.getReporterId();
					
					boolean canEdit = user != null && (user.getRole().equals(
							UserBean.Role.REPORTER)) && !itemReporter.equals("public");
					if (itemReporter.equals("public")
							|| (user != null && (user.getRole().equals(
									UserBean.Role.SUBSCRIBER) || itemReporter
									.equals(user.getUserId())))) {
						out.write("<a id=\"" + itemTitle
								+ "\" href=\"javascript:toggle('" + itemStory
								+ "','" + itemTitle + "','" + itemTitle
								+ "');\">" + itemTitle + "</a></center>");
						out.write("\r\n");
						out.write("<div id=\"" + itemStory
								+ "\" style=\"display: none\">");
						out.write("\r\n");
						out.write(itemTitle + " " + itemStory);
						if(canEdit){
							mutableNews.add(it);
							out.write("<form method=\"post\" action=\"news\">");
							out.write("<input type =\"hidden\" name=\"action\" value=\"edit\"/>");
							out.write("<input type =\"hidden\" name=\"itemId\" value="+it.getItemId()+">");
							out.write("<input type=\"submit\" name=\"editOrDelete\" value=\"Edit\"/>");
							out.write("<input type=\"submit\" name=\"editOrDelete\" value=\"Delete\"/>");
							out.write("</form>");
						}
						
						out.write("</div><br>");
						out.write("<br>");
					}
				}
				session.setAttribute("mutableNews", mutableNews);
			}
			out.write("</body></html>");

			out.close();
		} catch (Exception e) {

		}
	}
}
