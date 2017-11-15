package brian.boot.example.cache.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import brian.boot.example.cache.model.Post;

@Service
public class PostService {
	
	private Map<Integer, Post> dataMap = new HashMap<>();
	
	public PostService() {
		dataMap.put(new Integer(1), new Post(1, "No 1 Content"));
		dataMap.put(new Integer(2), new Post(2, "No 2 Content"));
		dataMap.put(new Integer(3), new Post(3, "No 3 Content"));
		dataMap.put(new Integer(4), new Post(4, "No 4 Content"));
		dataMap.put(new Integer(5), new Post(5, "No 5 Content"));
	}
	
	// If key attribute is omitted, entire parameters becomes a key.
	// So, if same parameters are passed, cached data will be return
	@Cacheable(value = "post-single", key = "#id")
	public Post getPost(int id) {
		
		System.out.println("id:"+id+" is fetched from database");
		
		return dataMap.get(new Integer(id));
	}
	
	@CachePut(value="post-single", key = "#post.id")
	public void addPost(Post post) {
		dataMap.put( new Integer(post.getId()), post);
	}
	
	@CacheEvict(value="post-single")
	public void deletePost(int id)
	{
		System.out.println("id:"+id+" is evicted");
		
		// Only delete it from Cache
//		dataMap.remove(new Integer(id));
	}
}
