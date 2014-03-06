package controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import handler.*;

public class NewsServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private static String errorPage = "error.jsp";
	private static Map<String, String> pageViews = new HashMap<String, String>();
    private static Map<String, ActionHandler> handlers = new HashMap<String, ActionHandler>();
	private static String user_file = null;

	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		user_file = config.getInitParameter("users_list");
		if (user_file == null || user_file.length() == 0) {
			throw new ServletException();
		}
		
		System.out.println("Loaded init param user info with " + user_file);
		handlers.put("validate", new LoginHandler());
		handlers.put("new_user", new NewUserHandler());
		handlers.put("view", new ViewNewsHandler());
		handlers.put("logout", new LogoutHandler());
		handlers.put("becomeSubscriber", new SubscriberHandler());
		handlers.put("addNews", new AddNewsHandler());
		handlers.put("success", new ActionHandler() {
			public String handleIt(Map<String, String[]> params, HttpSession s, HttpServletRequest request, HttpServletResponse response) {
				return "success";
			}
		});
		handlers.put("add", new ActionHandler() {
			public String handleIt(Map<String, String[]> params, HttpSession s, HttpServletRequest request, HttpServletResponse response) {
				return "add";
			}
		});
		handlers.put("Add Comment", new ActionHandler() {
			public String handleIt(Map<String, String[]> params, HttpSession s, HttpServletRequest request, HttpServletResponse response) {
				return "Add Comment";
			}
		});
		handlers.put("edit", new EditDeleteCommentHandler());
		handlers.put("editPage", new EditHandler());
		handlers.put("fav", new FavoriteHandler());
		pageViews.put("validate", "/validateLogin.jsp");
		pageViews.put("add", "/newUser.html");
		pageViews.put("success", "/success");
		pageViews.put("view", "/viewNews");
		pageViews.put("error", "/error.jsp");
		pageViews.put("Edit", "/edit");
		pageViews.put("Delete", "/delete");
		pageViews.put("confirm", "/confirm.jsp");
		pageViews.put("addNews", "/add");
		pageViews.put("logout", "/logout");
		pageViews.put("home", "/index.jsp");
		pageViews.put("Add Comment" , "/edit");
		pageViews.put("fav", "/favoritesAdded.jsp");
	}
	
    private void doAction(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		String forwardPage = errorPage;
		Map<String, String[]> params = request.getParameterMap();
		String action = request.getParameter("action");
		session.setAttribute("userfile", user_file);
		if (action != null && action.length() > 0) {
			// Forward to web application to page indicated by action
			ActionHandler handler = handlers.get(action);
			if (handler != null) {
				String result = handler.handleIt(params, session, request, response);
				if (result != null && result.length() > 0) {
					forwardPage = pageViews.get(result);
				}
				if (forwardPage == null || forwardPage.length() == 0) {
					forwardPage = errorPage;
				}
			}
		}
		request.getRequestDispatcher(forwardPage).forward(request, response);
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
			response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
			doAction(request, response);
		
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
		
		response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
		doAction(request, response);
	}
}
