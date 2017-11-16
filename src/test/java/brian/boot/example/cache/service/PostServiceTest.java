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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import brian.boot.example.cache.model.Post;
import brian.boot.example.cache.repository.PostRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
//@ContextConfiguration( classes = {CacheConfig.class} ) 
public class PostServiceTest {
	
	@Rule
	public final ExpectedException exception = ExpectedException.none();

	@Autowired
	private PostService service;
	
	@MockBean
	private PostRepository repo;
	
	@Before
	public void setup(){
	}
	
	@Test
	public void getPost(){
		when(repo.getPostData(1)).thenReturn(new Post(1, "No 1 content 111"));
		
		Post p1 = service.getPost(1);	// Fetch this from Repo (Mock)
		Post p2 = service.getPost(1);	// from Cache
		Post p3 = service.getPost(1);	// from Cache
		
		System.out.println(p1.getContent());
		assertEquals( p1.getContent(), p2.getContent());
		assertEquals( p2.getContent(), p3.getContent());
		
		verify(repo, times(1)).getPostData(1);
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
//	
//	@Test
//	public void testCachingToFail() {
//		exception.expect(IllegalArgumentException.class);
//		Post p1 = service.getPostToFail(1);
//	}
}
