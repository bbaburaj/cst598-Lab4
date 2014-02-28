package views;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.NewsDAO;
import dao.NewsDAOFactory;

import model.NewsItemBean;
import model.UserBean;

public class AddView extends HttpServlet {
	public void service(HttpServletRequest request, HttpServletResponse response) {
		NewsDAO dao = (NewsDAO) NewsDAOFactory.getTheDAO();
		HttpSession session = request.getSession(false);
		PrintWriter out = null;
		try {
			response.setContentType("text/html");
			out = response.getWriter();
			out.write("<html>");
			out.write("<head>");
			out.write("<title>Add News</title>");
			out.write("</head>");
			out.write("<body bgcolor=\"#E6E6FA\">");
			out.write("<h2>Add News</h2>");
			out.write("<FORM ACTION=\"news\" METHOD=\"post\">");
			out.write("<input type=\"hidden\" name=\"action\" value=\"addNews\"/>");
			out.write("Title: ");
			out.write("<input type=\"text\" name=\"newsTitle\" size=\"25\"/><br>");
			out.write("Body: ");
			out.write("<input type=\"textarea\" name=\"newsBody\" rows=3 cols=30 size=\"25\"/><br>");
			out.write("Public?<br>");
			out.write("<input type=\"radio\" name=\"newsType\" value=\"public\">Yes<br>");
			out.write("<input type=\"radio\" name=\"newsType\" value=\"reporter\">No<br>");
			out.write("<input type=\"submit\" value=\"Submit\">");
			if (session != null) {
				UserBean user = (UserBean) session.getAttribute("user");
				// boolean canEdit = (user != null) && ((user.getRole().equals(UserBean.Role.REPORTER)) || (user.getRole().equals(UserBean.Role.SUBSCRIBER)));
				
			}
		} catch (Exception e) {
		}
	}
}