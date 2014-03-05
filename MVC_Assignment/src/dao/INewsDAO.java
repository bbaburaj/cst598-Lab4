package dao;

import java.util.List;

import model.NewsItemBean;
import model.UserBean;

public interface INewsDAO {
	public boolean createUser(UserBean user);
	public UserBean getUser(String userid);
	public boolean storeComment(int newsItemId, String userid, String comment);
	public boolean createNewsItem(NewsItemBean nib, String userid);
	public NewsItemBean[] getNews();
	public NewsItemBean getNewsItem(int newsItemId);
	public boolean updateNewsItem(int newsItemId, String newTitle, String newStory);
	public boolean deleteNewsItem(int newsItemId);	
	public void addFavorites(NewsItemBean nib, UserBean user);
	public List<NewsItemBean> getFavorites(String userId);
}
