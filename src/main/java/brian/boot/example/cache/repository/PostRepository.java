package brian.boot.example.cache.repository;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import brian.boot.example.cache.model.Post;

@Repository
public class PostRepository {
	static Map<Integer, Post> dataMap = new HashMap<>();
	
	static {
		dataMap.put(new Integer(1), new Post(1, "No 1 Content"));
		dataMap.put(new Integer(2), new Post(2, "No 2 Content"));
		dataMap.put(new Integer(3), new Post(3, "No 3 Content"));
		dataMap.put(new Integer(4), new Post(4, "No 4 Content"));
		dataMap.put(new Integer(5), new Post(5, "No 5 Content"));
	}
	
	public Post getPostData(int id) {
		return dataMap.get(id);
	}
	
	public void removePostData(int id) {
		dataMap.remove(id);
	}
	
	public void addPostData(int id, Post p) {
		dataMap.put( id, p);
	}
}
