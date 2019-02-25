package brian.boot.example.cache.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import brian.boot.example.cache.model.Post;
import brian.boot.example.cache.repository.PostRepository;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class PostService {
	
	private final PostRepository repo;
	
	@Autowired
	public PostService(final PostRepository repo) {
		this.repo = repo;
	}
	
	/** 
	 * If key attribute is omitted, entire parameters becomes a key.
	 * So, if same parameters are passed, cached data will be return
	 * 
	 * @param id
	 * @return
	 */
	@Cacheable(value = "post-single")
	public Post getPost(int id) {
		
		log.debug("id:"+id+" is fetched from database");
		
		return repo.getPostData(id);
	}
	
	/**
	 * The annotation @CachePut if the data exists in cache, it will update it. Otherwise add the data to cache.
	 * Then still execute the method
	 * @CachePut(value="post-single", key = "#post.id", condition="#post.id==1")	// <-- Condition can be added
	 * 
	 * @param post
	 */
	@CachePut(value="post-single", key="#post.id")
	public void addPost(Post post) {
		log.debug("id:"+post.getId()+" is added(or updated) to database");
		
		repo.addPostData( post.getId(), post);
	}
	
	/**
	 * The Annotation @CacheEvict will delete the data from cache
	 * If it is update, instead of Evict, use @CachePut
	 * 
	 * @param id
	 */
	@CacheEvict(value="post-single")
	public void deletePost(int id)
	{
		log.debug("id:"+id+" is evicted");
		
		// Only delete it from Cache
		repo.removePostData(id);
	}
	
	/**
	 * Not registered cache name. This should throw an error
	 * @param id
	 * @return
	 */
	@Cacheable(value = "post-multiple")
	public Post getPostToFail(int id) {
		
		log.debug("id:"+id+" is fetched from database");
		
		return repo.getPostData(id);
	}
}
