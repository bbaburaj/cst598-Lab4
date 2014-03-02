package views;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.CommentBean;
import model.NewsItemBean;
import model.UserBean;
import model.UserBean.Role;
import dao.NewsDAO;
import dao.NewsDAOFactory;

public class NewsView extends HttpServlet {
	public void service(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
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
				out.write("<div align=\"right\">");
				out.write("<a href=\"./add\">Add News</a><br>");
				out.write("<a href=\"" + request.getContextPath()
						+ "/logout.jsp\">Logout </a><br>");
				if(user==null){
					out.write("<a href=\"" + request.getContextPath()
							+ "/subscriber.jsp\">Become a subscriber </a>");
				}
				out.write("</div>");
				for (int i = 0; i < news.length; i++) {

					NewsItemBean it = news[i];
					if (it == null)
						continue;
					String itemTitle = it.getItemTitle();
					String itemStory = it.getItemStory();
					String itemReporter = it.getReporterId();

					Role role = Role.GUEST;
					String userId = "";
					if (user != null) {
						role = user.getRole();
						userId = user.getUserId();
					}

					boolean canComment = (role == Role.SUBSCRIBER)
							|| (itemReporter.equals(userId));
					boolean canEdit = user != null && (!(role == Role.GUEST));
					if (itemReporter.equals("public")
							|| (user != null && ((role == Role.SUBSCRIBER) || itemReporter
									.equals(userId)))) {
						out.write("<a id=\"" + itemTitle
								+ "\" href=\"javascript:toggle('" + itemStory
								+ "','" + itemTitle + "','" + itemTitle
								+ "');\">" + itemTitle + "</a></center>");
						out.write("\r\n");
						out.write("<div id=\"" + itemStory
								+ "\" style=\"display: none\">");
						out.write("\r\n");
						out.write(itemStory);
						if (canEdit) {
							mutableNews.add(it);
							out.write("<form method=\"post\" action=\"news\">");
							out.write("<input type =\"hidden\" name=\"action\" value=\"edit\"/>");
							out.write("<input type =\"hidden\" name=\"itemId\" value="
									+ it.getItemId() + ">");
							if (!itemReporter.equals("public")) {
								out.write("<input type=\"submit\" name=\"editOrDelete\" value=\"Edit\"/>");
								out.write("<input type=\"submit\" name=\"editOrDelete\" value=\"Delete\"/>");
							}

							out.write("<h4>Comments</h4>");
							CommentBean[] comments = it.getComments();
							for (CommentBean cmt : comments) {
								out.write("<h4>" + cmt.getComment() +" by "+cmt.getUserId()+"</h4>");
							}

							if (canComment) {
								out.write("<input type=\"text\" id=\"newsComment\" name=\"newsComment\"/>");
								out.write("<input type=\"submit\" name=\"editOrDelete\" value=\"Add Comment\"/>");
							}
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
			out.write("There was an exception");
		}
	}
}
