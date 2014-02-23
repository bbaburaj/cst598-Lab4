package edu.asupoly.ser422.lab4.model;

import java.util.ArrayList;
import java.util.Date;

public class NewsItemBean {
	private static int nextId = 1;
	
	private int itemId;
	private String itemTitle;
	private String itemStory;
	private Date itemDate;
	private String reporterId;
	private ArrayList<CommentBean> comments = new ArrayList<CommentBean>();
	
	// This constructor is used for a new news item
	public NewsItemBean(String title, String story, String rid) {
		this(nextId++, title, story, rid);
	}
	
	// This constructor is used for an existing, i.e. coming from datastore
	public NewsItemBean(int id, String title, String story, String rid) {
		itemTitle = title;
		itemStory = story;
		reporterId = rid;
		itemDate = new Date();
		itemId = id;
	}
	
	public int getItemId() {
		return itemId;
	}
	public String getReporterId() {
		return reporterId;
	}

	public void setItemTitle(String itemTitle, String rid) {
		if (rid.equals(reporterId)) {
			this.itemTitle = itemTitle;
		}
	}

	public String getItemTitle() {
		return itemTitle;
	}

	public void setItemStory(String itemStory, String rid) {
		if (rid.equals(reporterId)) {
			this.itemStory = itemStory;
			setItemDate(new Date());
		}
	}

	public String getItemStory() {
		return itemStory;
	}

	private void setItemDate(Date itemDate) {
		this.itemDate = itemDate;
	}

	public Date getItemDate() {
		return itemDate;
	}
	
	public void addComment(CommentBean cb) {
		comments.add(cb);
	}
	public CommentBean[] getComments() {
		return comments.toArray(new CommentBean[0]);
	}
}
