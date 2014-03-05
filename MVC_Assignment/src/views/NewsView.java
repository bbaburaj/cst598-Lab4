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
		HttpSession session = request.getSession(false);
		NewsItemBean[] news = (NewsItemBean[]) session.getAttribute("news");
		boolean[] canEdit = (boolean[]) session.getAttribute("canEdit");
		boolean[] canComment = (boolean[]) session.getAttribute("canComment");
		boolean[] isPublic = (boolean[]) session.getAttribute("isPublic");

		@SuppressWarnings("unchecked")
		List<CommentBean[]> comments = (List<CommentBean[]>) session
				.getAttribute("comments");
		List<NewsItemBean> favorites = (List<NewsItemBean>) session
				.getAttribute("favorites");
		PrintWriter out = null;
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

				if (user != null) {
					out.write("<a href=\"" + request.getContextPath()
							+ "/logout.jsp\">Logout </a><br>");
					out.write("<a href=\"./add\">Add News</a><br>");
				}
				if (user == null) {
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
					int itemId = it.getItemId();
					out.write("<a id=\"" + itemTitle
							+ "\" href=\"javascript:toggle('" + itemStory
							+ "','" + itemTitle + "','" + itemTitle + "');\">"
							+ itemTitle + "</a></center>");
					out.write("\r\n");
					out.write("<div id=\"" + itemStory
							+ "\" style=\"display: none\">");
					out.write("\r\n");
					out.write(itemStory);
					out.write("<form method=\"post\" action=\"news\">");
					out.write("<input type =\"hidden\" name=\"action\" value=\"edit\"/>");
					if (!favorites.contains(news[i]))
						out.write("<input type=\"submit\" name=\"favorite\" value=\"Mark As Favorite\"/>");
					out.write("<input type =\"hidden\" name=\"itemId\" value="
							+ itemId + ">");
					if (canEdit[i]) {
						if (isPublic[i]) {
							out.write("<input type=\"submit\" name=\"editOrDelete\" value=\"Edit\"/>");
							out.write("<input type=\"submit\" name=\"editOrDelete\" value=\"Delete\"/>");
						}
					}
					out.write("<h4>Comments</h4>");
					CommentBean[] storyComment = comments.get(i);
					for (CommentBean cmt : storyComment) {
						out.write("<h4>" + cmt.getComment() + " by "
								+ cmt.getUserId() + "</h4>");
					}

					if (canComment[i]) {
						out.write("<input type=\"text\" id=\"newsComment\" name=\"newsComment\"/>");
						out.write("<input type=\"submit\" name=\"editOrDelete\" value=\"Add Comment\"/>");
					}
					out.write("</form>");

					out.write("</div><br>");
					out.write("<br>");
				}
			}

			out.write("</body></html>");

			out.close();
		} catch (Exception e) {
			out.write("There was an exception");
		}
	}
}
