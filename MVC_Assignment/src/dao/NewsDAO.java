package dao;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


import model.NewsItemBean;
import model.UserBean;
import model.UserBean.Role;
import model.CommentBean;

public class NewsDAO implements INewsDAO {
	
	public static Map<String, UserBean> userMap = new HashMap<String, UserBean>();	
	public static Map<Integer, NewsItemBean> newsMap = new HashMap<Integer, NewsItemBean>();
	
	public static final String NEW_LINE = System.getProperty("line.separator");
	
	public NewsDAO() {
		createNewsItem(new NewsItemBean("Reporter News 1", "This is the body-1", "reporter1"), "jbranger");
		createNewsItem(new NewsItemBean("Reporter News 2", "This is the body-2", "reporter1"), "jbranger");
		createNewsItem(new NewsItemBean("Reporter News 3", "This is the body-3", "reporter1"), "jbranger");
		createNewsItem(new NewsItemBean("Reporter News 4", "This is the body-4", "reporter1"), "jbranger");
		createNewsItem(new NewsItemBean("Reporter News 5", "This is the body-5", "reporter2"), "jbranger");
		createNewsItem(new NewsItemBean("Reporter News 6", "This is the body-6", "reporter3"), "jbranger");
		createNewsItem(new NewsItemBean("Reporter News 7", "This is the body-7", "reporter4"), "jbranger");
		createNewsItem(new NewsItemBean("Public News 1", "This is the body-1", "public"), "jbranger");
		createNewsItem(new NewsItemBean("Public News 2", "This is the body-2", "public"), "jbranger");
		createNewsItem(new NewsItemBean("Public News 3", "This is the body-3", "public"), "jbranger");
		createNewsItem(new NewsItemBean("Public News 4", "This is the body-4", "public"), "jbranger");
	}
	
	@Override
	public NewsItemBean[] getNews() {
		NewsItemBean newsItems[] = new NewsItemBean[newsMap.size()];
		for (int i = 0; i < newsItems.length; i++) {
			System.out.println(newsMap.get(i+1));
			newsItems[i] = newsMap.get(i+1);
		}
		return newsItems;
	}
	@Override
	public boolean createUser(UserBean user) {
		return userMap.put(user.getUserId(), user) != null;
	}

	@Override
	public UserBean getUser(String userid) {		
		return userMap.get(userid);	
	}
	
	public UserBean getUser(String userId, String role){
		UserBean u = getUser(userId);
		System.out.println("["+u+"]"+role);
		if(u!=null && u.getRole().toString().equals(role)){
			return u;
		}
		return null;
	}

	@Override
	public boolean storeComment(int newsItemId, String userid, String comment) {
		return Math.random() > 0.1;
	}

	@Override
	public boolean createNewsItem(NewsItemBean nib, String userid) {
		newsMap.put(nib.getItemId(), nib);
		return true;
	}

	@Override
	public NewsItemBean getNewsItem(int i) {
		return new NewsItemBean(i, "title"+i, "story"+i, "reporter"+i%3);
	}

	@Override
	public boolean updateNewsItem(int newsItemId, String newTitle, String newStory) {
		NewsItemBean news = newsMap.get(newsItemId);
		String rid = news.getReporterId();
		news.setItemStory(newStory, rid);
		news.setItemTitle(newTitle, rid);
		return Math.random() > 0.1;
	}

	@Override
	public boolean deleteNewsItem(int newsItemId) {
		System.out.println("delete news item:"+newsItemId);
		System.out.println("delete news item:"+newsMap.get(newsItemId));
		newsMap.put(newsItemId, null);
		return Math.random() > 0.1;
	}
	
	public void updateStudentFile(String name) throws IOException, URISyntaxException{
		File f = new File(name);
		BufferedWriter writer = new BufferedWriter(new FileWriter(f.getAbsoluteFile()));
		Iterator<UserBean> i = userMap.values().iterator();
		while(i.hasNext()){
			UserBean user = i.next();
			writer.write(user.getRole()+NEW_LINE);
			writer.write(user.getUserId()+NEW_LINE);
			writer.write(user.getPasswd()+NEW_LINE);
		}
		writer.close();
	}
	
	public void getDataFromResource(InputStream is) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		try {
			String nextLine = null;
			while ( (nextLine=reader.readLine()) != null)
			{
				String role  = nextLine;
				String username = reader.readLine();
				String passwd = reader.readLine();
				Role r = (role.equals("Subscriber"))?Role.SUBSCRIBER:Role.REPORTER;
				if(role.equals("Guest")) r = Role.GUEST;
				createUser(new UserBean(username,passwd,r));
			}
			reader.close();
		}
		catch (Exception e)
		{
			e.printStackTrace();
			System.out.println("Error processing user entries");
			throw new IOException("Failed to process login.txt");
		}
	}

}
