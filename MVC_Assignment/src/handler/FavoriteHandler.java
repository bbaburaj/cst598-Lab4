package handler;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.NewsItemBean;
import model.UserBean;
import dao.NewsDAO;
import dao.NewsDAOFactory;

public class FavoriteHandler implements ActionHandler{

	@Override
	public String handleIt(Map<String, String[]> params, HttpSession session,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		NewsDAO dao = (NewsDAO) NewsDAOFactory.getTheDAO();
		UserBean user = (UserBean)session.getAttribute("user");
		int itemId = Integer.parseInt(((String[]) params.get("itemId"))[0]);
		String[] favParam = ((String[]) params.get("favorite"));
		String favorite = (favParam == null)?"":favParam[0];
		boolean markFavorite = (favorite.equals("Mark As Favorite"));
		NewsItemBean[] news = dao.getNews();
		String action = "error";
		for(NewsItemBean n:news){
			if(n.getItemId() == itemId){
				if(markFavorite){
					dao.addFavorites(n, user, ViewNewsHandler.guestId);
					action = "fav";
				}else{
					dao.deleteFavorite(n,user, ViewNewsHandler.guestId);
					action = "fav";
				}
			}
		}
		return action;
	}

}
