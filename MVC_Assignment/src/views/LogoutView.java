package views;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


public class LogoutView extends HttpServlet {
	public void service(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession(false);
		PrintWriter out = null;
			response.setContentType("text/html");
			try {
				out = response.getWriter();
			} catch (IOException e) {
				e.printStackTrace();
			}
			out.write("<html>");
			out.write("<head><title>Logout Confirmation</title></head>");
			out.write("<body bgcolor=\"#E6E6FA\">");
			out.write("<h2> You have been logged out of NEW News</h2>");
			out.write("<table border=\"0\">");
			out.write("<tr>");
			out.write("<td><a href=\""+request.getContextPath()+"/login.html\"> Click here to login </a></td>");
			out.write("<td><a href=\""+request.getContextPath()+"/index.jsp\">Home </a></td>");
			session.setAttribute("user", null);
	}


}
