package brian.example.boot.cache.model;

import java.io.Serializable;

public class Post implements Serializable {

	private int id;
	private String content;
	public Post() {}
	public Post(int id, String content) {
		super();
		this.id = id;
		this.content = content;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
}
