package brian.boot.example.cache;

import static org.junit.Assert.fail;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import brian.boot.example.cache.model.Post;
import brian.boot.example.cache.service.PostService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BootExampleCacheApplicationTests {
	
	@Rule
	public final ExpectedException exception = ExpectedException.none();

	@Autowired
	private PostService service;
	
	@Test
	public void testCaching() {
		
		System.out.println("Getting Post #1---");
		Post p1 = service.getPost(1);
		System.out.println("Getting Post #1--- Fetched");
		
		System.out.println("Getting Post #1 again. This should not be fetched from Database");
		Post p2 = service.getPost(1);
		System.out.println("Getting Post #1--- Fetched");
		
		// Evicting a data from Cache
		System.out.println("Evict Post #1 from Cache---");
		service.deletePost(1);
		System.out.println("Evict Post #1 from Cache--- Evicted");
		
		// Getting Post #1 again from the database
		System.out.println("Getting Post #1--- It is evicted before, so it should be fetched from database again");
		Post p3 = service.getPost(1);
		System.out.println("Getting Post #1--- Fetched");
	}
	
	@Test
	public void testCachingToFail() {
		exception.expect(IllegalArgumentException.class);
		Post p1 = service.getPostToFail(1);
	}
}
