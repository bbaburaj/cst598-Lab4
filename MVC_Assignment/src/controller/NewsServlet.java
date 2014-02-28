package controller;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.NewsItemBean;
import model.UserBean;
import dao.NewsDAO;
import dao.NewsDAOFactory;

public class NewsServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private static Map<String, String> pageViews = new HashMap<String, String>();
	private static String user_file = null;

	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		user_file = config.getInitParameter("users_list");
		if (user_file == null || user_file.length() == 0) {
			throw new ServletException();
		}
		System.out.println("Loaded init param user info with " + user_file);
		pageViews.put("validate", "/validateLogin.jsp");
		pageViews.put("add", "/newUser.html");
		pageViews.put("success", "/success");
		pageViews.put("view", "/viewNews");
		pageViews.put("error", "/error.jsp");
		pageViews.put("Edit", "/edit");
		pageViews.put("Delete", "/delete");
		pageViews.put("confirm", "/confirm.jsp");
		pageViews.put("addNews", "/add");
	}

	private void doAction(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException,
			URISyntaxException {
		HttpSession session = request.getSession();
		String action = request.getParameter("action");
		ServletContext sc = getServletContext();
		InputStream is = sc.getResourceAsStream(user_file);
		NewsDAO dao = (NewsDAO) NewsDAOFactory.getTheDAO();
		NewsItemBean newsToEdit = (NewsItemBean) session
				.getAttribute("newsToEdit");
		NewsItemBean newsToDelete = (NewsItemBean) session
				.getAttribute("newsToDelete");
		
		if (action != null && action.length() > 0) {
			if (action.equals("validate")) {
				dao.getDataFromResource(is);
			} else if (action.equals("new_user")) {
				String register = request.getParameter("register");
				String id = session.getAttribute("userid").toString();
				String role = request.getParameter("role");
				action = "fail";
				if (register != null && register.equals("yes")) {
					UserBean user = new UserBean(id, id,
							UserBean.Role.valueOf(role));
					dao.createUser(user);
					session.setAttribute("user", user);
					action = "success";
				}
				String realPath = sc.getRealPath("/");
				realPath = realPath.concat(user_file.substring(1));
				dao.updateStudentFile(realPath);
			} else if (action.equals("edit")) {
				action = request.getParameter("editOrDelete");
			} else if (newsToEdit != null) {
				String newStory = request.getParameter("newsStory");
				String newTitle = request.getParameter("newsTitle");
				dao.updateNewsItem(newsToEdit.getItemId(), newTitle, newStory);
				action = "confirm";
				session.setAttribute("newsToEdit", null);
			} else if (newsToDelete != null && request.getParameter("delete").equals("Yes")) {
				dao.deleteNewsItem(newsToDelete.getItemId());
				action = "confirm";
				session.setAttribute("newsToDelete", null);
			} else if (action.equals("addNews")) {
				dao.createNewsItem(new NewsItemBean(request.getParameter("newsTitle"), request.getParameter("newsBody"), "test"), request.getParameter("user"));
				action = "success";
			}
			String forwardPage = pageViews.get(action);
			request.getRequestDispatcher(forwardPage)
					.forward(request, response);
		}

	}

	/**
	 * Handle forms
	 * 
	 * @param request
	 *            HTTP Request object
	 * @param response
	 *            HTTP Response object
	 * 
	 * @throws ServletException
	 * @throws IOException
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			doAction(request, response);
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * @param request
	 *            HTTP Request object
	 * @param response
	 *            HTTP Response object
	 * 
	 * @throws ServletException
	 * @throws IOException
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			doAction(request, response);
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
