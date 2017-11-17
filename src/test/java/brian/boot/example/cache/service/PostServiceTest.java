package brian.boot.example.cache.service;

import static org.junit.Assert.assertEquals;
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

import brian.boot.example.cache.model.Post;
import brian.boot.example.cache.repository.PostRepository;
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
	
//	@Test
//	public void testCaching() {
//		
//		service.addPost(new Post(1,"No 1 Content"));
//		
//		System.out.println("Getting Post #1---");
//		service.getPost(1);
//		System.out.println("Getting Post #1--- Fetched");
//		
//		System.out.println("Getting Post #1 again. This should not be fetched from Database");
//		service.getPost(1);
//		System.out.println("Getting Post #1--- Fetched");
//		
//		// Evicting a data from Cache
//		System.out.println("Evict Post #1 from Cache---");
//		service.deletePost(1);
//		System.out.println("Evict Post #1 from Cache--- Evicted");
//		
//		// Getting Post #1 again from the database
//		System.out.println("Getting Post #1--- It is evicted before, so it should be fetched from database again");
//		Post p3 = service.getPost(1);
//		System.out.println("Getting Post #1--- Fetched");
//	}
	
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
	public void test_cachingToFail_throws_IllegalArgumentException() {
		exception.expect(IllegalArgumentException.class);
		service.getPostToFail(1);
	}
}
