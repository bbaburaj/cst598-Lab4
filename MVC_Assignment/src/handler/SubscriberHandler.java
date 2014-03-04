package handler;

import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpSession;

import dao.NewsDAO;
import dao.NewsDAOFactory;
import model.UserBean;
import model.UserBean.Role;

public class SubscriberHandler implements ActionHandler{

	@Override
	public String handleIt(Map<String, String[]> params, HttpSession session)
			throws IOException {
		boolean actionCanceled = ((String[]) params.get("subscriber"))[0].equals("No");
		NewsDAO dao = (NewsDAO) NewsDAOFactory.getTheDAO();
		if(actionCanceled){
			return "home";
		}else{
			String id = ((String[]) params.get("userId"))[0];
			UserBean user = new UserBean(id, id,
					Role.SUBSCRIBER);
			dao.createUser(user);
			session.setAttribute("user", user);
			return "success";
		}
	}

}
