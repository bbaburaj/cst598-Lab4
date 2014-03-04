package handler;

import java.io.IOException;
import java.util.Enumeration;
import java.util.Map;

import javax.servlet.http.HttpSession;

public class LogoutHandler implements ActionHandler {

	@Override
	public String handleIt(Map<String, String[]> params, HttpSession session)
			throws IOException {
		boolean logout = ((String[]) params.get("logout"))[0].equals("Yes");
		if (logout) {
			Enumeration<String> enumerator = session.getAttributeNames();
			while (enumerator.hasMoreElements()) {
				session.setAttribute(enumerator.nextElement(), null);
			}
			return "logout";
		}
		return "home";
	}

}
