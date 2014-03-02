package model;

/**
 * 	An immutable object representing a comment on a news story
 * @author kevinagary
 *
 */
public class CommentBean {
	private String userId;
	private String comment;
	private int newsItemId;
	
	public CommentBean(NewsItemBean nib, String userid, String comment) {
		newsItemId = nib.getItemId();
		this.userId = userid;
		this.comment = comment;
		nib.addComment(this);
	}

	public String getUserId() {
		return userId;
	}

	public String getComment() {
		return comment;
	}

	public int getNewsItemId() {
		return newsItemId;
	}
}
