package brian.example.boot.cache.repository;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import brian.example.boot.cache.model.Post;

@Repository
public class PostRepository {
	Map<Integer, Post> dataMap = new HashMap<>();
	
	public PostRepository() {
		dataMap.put(1, new Post(1, "No 1 Content"));
		dataMap.put(2, new Post(2, "No 2 Content"));
		dataMap.put(3, new Post(3, "No 3 Content"));
		dataMap.put(4, new Post(4, "No 4 Content"));
		dataMap.put(5, new Post(5, "No 5 Content"));
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
