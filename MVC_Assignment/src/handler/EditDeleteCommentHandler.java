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

public class EditDeleteCommentHandler implements ActionHandler{



@Override
public String handleIt(Map<String, String[]> params, HttpSession session, HttpServletRequest request, HttpServletResponse response)
		throws IOException {
	NewsDAO dao = (NewsDAO) NewsDAOFactory.getTheDAO();
	UserBean user = (UserBean)session.getAttribute("user");
	int mutableNewsId = Integer.parseInt(((String[]) params.get("itemId"))[0]);
	String[] editDeleteParam = ((String[]) params.get("editOrDelete"));
	String editOrDelete = (editDeleteParam == null)?"":editDeleteParam[0];
	String[] favParam = ((String[]) params.get("favorite"));
	String favorite = (favParam == null)?"":favParam[0];
	boolean edit = editOrDelete.equals("Edit");
	boolean delete = editOrDelete.equals("Delete");
	boolean canComment = !(edit||delete);
	boolean markFavorite = (favorite.equals("Mark As Favorite"));
	String action =editOrDelete;
	List<NewsItemBean> newsArray = (List<NewsItemBean>) session.getAttribute("mutableNews");
	String[] comments = ((String[]) params.get("newsComment"));
	String comment = (comments!=null)?comments[0]:"";
	for(NewsItemBean news:newsArray){
		if(news.getItemId() == mutableNewsId){
			if(canComment){
				dao.storeComment(news.getItemId(), user.getUserId(), comment);
			}else if(edit){
				session.setAttribute("newsToEdit", news);
			}else if(delete){
				session.setAttribute("newsToDelete",news);
			}else{
				action = "blank";
			}
		}
	}
	session.setAttribute("mutableNewsId", mutableNewsId);
	session.setAttribute("canComment", canComment);
	return action;
}
}
