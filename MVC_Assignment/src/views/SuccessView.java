/**
 * 
 */
package views;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.UserBean;

public class SuccessView extends HttpServlet {
	public void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		PrintWriter out = null;
		try {
			response.setContentType("text/html");
			HttpSession session = request.getSession(false); 
			out = response.getWriter();
			out.write("<html>\r\n");
			out.write("<head>\r\n");
			out.write("<title>News Web App: Logged in</title>\r\n");
			out.write("</head>\r\n");
			if (session != null) {
				out.write("<body bgcolor=\"#E6E6FA\">\r\n");
				UserBean user = (UserBean) session.getAttribute("user");
				boolean canAddNews = (boolean)session.getAttribute("canAddNews");
				if (user != null) {
					out.write("\r\n");
					out.write("<h2>Welcome "+user.getUserId()+"! you are now logged in to NEW News</h2>\r\n");
					out.write("<p>NEW news is a news resource about NEW stuff.</p>");
					out.write("<p> NEW news is a fictional organization managing fictional news items for a non-fictional web programming class.</p>");
					out.write("<br/>");
					out.write("<table border=\"0\">");
					out.write("<tr>");
					out.write("<td><a href=\"./about.html\">About</a></td>");
					out.write("<td><a href=\"./news?action=view\">View News</a></td>");
					if(canAddNews)out.write("<td><a href=\"./add\">Add News</a></td>");
					out.write("<td><a href=\""+request.getContextPath()+"/index.jsp\">Home </a></td>");
					out.write("<td><a href=\""+request.getContextPath()+"/logout.jsp\">Logout </a></td>");
					out.write("</tr>");
					out.write("</table>");
					out.write("</body>\r\n");
					out.write("</html>\r\n");
				} else {
					out.write("Session Lost!");
					session.invalidate();
				}
			} else {
				out.write("No valid conversation exists between client and server");
			}
		} finally {
			if (out != null) {
				out.close();
				out = null;
			}
		}
	}
}
