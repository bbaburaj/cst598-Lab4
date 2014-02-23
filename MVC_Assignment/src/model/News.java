package model;

public class News {
	private String author;
	private String type;
	private String title;
	private String story;
	
	public News() {
		
	}
	public News(String author, String type, String title, String story) {
		super();
		this.author = author;
		this.type = type;
		this.title = title;
		this.story = story;
	}

	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getStory() {
		return story;
	}
	public void setStory(String story) {
		this.story = story;
	}
}
