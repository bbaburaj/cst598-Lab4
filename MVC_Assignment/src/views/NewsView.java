package views;


import java.io.PrintWriter;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.NewsItemBean;
import model.UserBean;
import dao.NewsDAO;
import dao.NewsDAOFactory;

public class NewsView extends HttpServlet{
	public void service(HttpServletRequest request, HttpServletResponse response){
		NewsDAO dao = (NewsDAO) NewsDAOFactory.getTheDAO();
		NewsItemBean[] news = dao.getNews();
		HttpSession session = request.getSession(false); 
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
				if (user != null) {
					out.write("\r\n");
					out.write("<h1 align=center> The latest news </h1>");
					for (int i =0;i<news.length;i++){
						NewsItemBean it = news[i];
						out.write("<a id=\""+it.getItemTitle()+"\" href=\"javascript:toggle('"+it.getItemStory()+"','"+it.getItemTitle()+"','"+it.getItemTitle()+"');\">"+it.getItemTitle()+"</a></center>");
						out.write("\r\n");
						out.write("<div id=\""+it.getItemStory()+"\" style=\"display: none\">");
						out.write("\r\n");
						out.write(it.getItemTitle()+" "+it.getItemStory());
						out.write("<p></p>");
						out.write("</div><br>");
						}
					}
				out.write("</body></html>");
				}out.close();
			}catch(Exception e){
				
			}
	}
}
