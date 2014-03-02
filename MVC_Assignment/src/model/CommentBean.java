package model;

/**
 * 	An immutable object representing a comment on a news story
 * @author kevinagary
 *
 */
public class CommentBean implements java.io.Serializable {
	private static final long serialVersionUID = 8241629811258814033L;
	
	private String userId;
	private String comment;
	private int newsItemId;
	
	public CommentBean(NewsItemBean nib, String userid, String comment) {
		newsItemId = nib.getItemId();
		this.userId = userid;
		this.comment = comment;
		nib.addComment(this);
	}
	@SuppressWarnings("unused")
	private CommentBean(int nid, String userid, String comment) {
		newsItemId = nid;
		this.userId = userid;
		this.comment = comment;
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
