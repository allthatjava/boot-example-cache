package brian.example.boot.cache.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import brian.example.boot.cache.model.Post;
import brian.example.boot.cache.repository.PostRepository;
import brian.example.boot.cache.service.PostService;
import lombok.extern.slf4j.Slf4j;

@RunWith(SpringRunner.class)
@SpringBootTest
//@ContextConfiguration( classes = {CacheConfig.class} )
@Slf4j
public class PostServiceTest {
	
	@Rule
	public final ExpectedException exception = ExpectedException.none();

	@Autowired
	private PostService service;
	
	@MockBean
	private PostRepository repo;
	
	@Before
	public void setup(){
//		MockitoAnnotations.initMocks(service);
//		MockitoAnnotations.initMocks(repo);
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void test_getPost_shouldReturn_cachedData(){
		
		// Given
		final int TEST_NO = 3;
		Post p = new Post(TEST_NO, "No 3 content - 333");

		// When
		when(repo.getPostData(TEST_NO)).thenReturn(p);
		
		// Test
		Post p1 = service.getPost(TEST_NO);	// Fetch this from Repo (Mock)
		Post p2 = service.getPost(TEST_NO);	// from Cache
		Post p3 = service.getPost(TEST_NO);	// from Cache
		
		// Assert
		log.debug("---------"+p1.getContent());
		assertEquals( p1.getContent(), p2.getContent());
		assertEquals( p2.getContent(), p3.getContent());
		
		verify(repo, times(1)).getPostData(TEST_NO);
	}
	
	@Test
	public void test_addPost_withOnePost_shouldAddedAndCached() {
		
		// Given 
		final int TEST_NO = 1;
		final Post p1 = new Post(TEST_NO, "No "+TEST_NO+" content - Updated");
		
		// When
		when(repo.getPostData(TEST_NO)).thenReturn(p1);
		
		// Test
		service.addPost(p1);		// Add to database and also add in Cache
		
		Post c = service.getPost(TEST_NO);
		log.debug(TEST_NO+" content:"+c.getContent());
		
		// Assert
		verify(repo, times(1)).addPostData(TEST_NO, p1);		// Must be called
	}
	
	@Test
	public void test_deletePost_withPost2_shouldDeleteAndEvict() {
		// Given 
		final int TEST_NO = 2;
		final Post p = new Post(TEST_NO, "NO "+TEST_NO+" contect - Added"); 
		
		// When
		when(repo.getPostData(TEST_NO)).thenReturn(p);
		
		// Test
		Post c = service.getPost(TEST_NO);		// Cache it first (called once)
		service.deletePost(p.getId());			// then remove it from the Cache
		
		service.addPost(p);						// Add it back and cache will be added
		Post d = service.getPost(TEST_NO);		// This will be pulled from Cache.	
		
		// Assert
		verify(repo, times(1)).removePostData(TEST_NO);		// Must be called
		verify(repo, times(1)).addPostData(TEST_NO, p);		// Must be called
		verify(repo, times(1)).getPostData(TEST_NO);		// Must be called only once, not twice
	}
	
	@Test
	public void test_cachingToFail_throws_IllegalArgumentException() {
		exception.expect(IllegalArgumentException.class);
		service.getPostToFail(1);
	}
}
