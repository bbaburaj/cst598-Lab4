package handler;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;

import dao.NewsDAO;
import dao.NewsDAOFactory;
import model.UserBean;

public class NewUserHandler implements ActionHandler{

	@Override
	public String handleIt(Map<String, String[]> params, HttpSession session)
			throws IOException {
		ServletContext sc = session.getServletContext();
		NewsDAO dao = (NewsDAO) NewsDAOFactory.getTheDAO();
		String user_file = (String)session.getAttribute("userFile");
		String register =((String[]) params.get("register"))[0];
		String id = session.getAttribute("userid").toString();
		String role = ((String[]) params.get("role"))[0];
		String action = "error";
		if (register != null && register.equals("yes")) {
			UserBean user = new UserBean(id, id,
					UserBean.Role.valueOf(role));
			dao.createUser(user);
			session.setAttribute("user", user);
			action = "success";
		}
		String realPath = sc.getRealPath("/");
		realPath = realPath.concat(user_file.substring(1));
		try {
			dao.updateStudentFile(realPath);
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return action;
	}

}
