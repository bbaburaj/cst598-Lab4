package edu.asupoly.ser422.lab4.dao;


public final class NewsDAOFactory {
	private static INewsDAO __theDAO = null;
	
	private NewsDAOFactory() {}
	
	public static final INewsDAO getTheDAO() {
		if (__theDAO == null) {
			__theDAO = new NewsDummyDAO();
		}
		return __theDAO;
	}
}
