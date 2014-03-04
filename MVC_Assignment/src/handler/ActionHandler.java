package handler;

import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpSession;

public interface ActionHandler {
	public String handleIt(Map<String, String[]> params, HttpSession session) throws IOException;
	
}
