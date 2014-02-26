package dao;


public final class NewsDAOFactory {
	private static NewsDAO __theDAO = null;
	
	private NewsDAOFactory() {}
	
	public static final NewsDAO getTheDAO() {
		if (__theDAO == null) {
			__theDAO = new NewsDAO();
		}
		return __theDAO;
	}
}
