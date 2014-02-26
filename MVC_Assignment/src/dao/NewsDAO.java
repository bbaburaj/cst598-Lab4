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

public class NewsDAO implements INewsDAO {
	
	public static Map<String, UserBean> userMap = new HashMap<String, UserBean>();	
	public static final String NEW_LINE = System.getProperty("line.separator");
	@Override
	public NewsItemBean[] getNews() {
		NewsItemBean[] news = new NewsItemBean[10];
		for (int i = 0; i < news.length; i++) {
			news[i] = new NewsItemBean(i, "title"+i, "story"+i, "reporter"+i%3);
		}
		return news;
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
		return Math.random() > 0.1;
	}

	@Override
	public NewsItemBean getNewsItem(int i) {
		return new NewsItemBean(i, "title"+i, "story"+i, "reporter"+i%3);
	}

	@Override
	public boolean updateNewsItem(int newsItemId, String newTitle, String newStory) {
		return Math.random() > 0.1;
	}

	@Override
	public boolean deleteNewsItem(int newsItemId) {
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
