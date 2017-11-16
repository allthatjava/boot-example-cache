package brian.boot.example.cache.config;

import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableCaching
public class CacheConfig {
	
// TODO : This was not needed since applicatoin.yml has spring.cache.type as 'simple'.
//			When you have multiple cache configuration for different environments,
//				CacheManager bean must be added for each different environments(Profile).
//    @Bean
//    public CacheManager cacheManager() {
//        SimpleCacheManager cacheManager = new SimpleCacheManager();
//        cacheManager.setCaches(
//        		Arrays.asList(
//        			new ConcurrentMapCache("post-single") 
//        		)
//        );
//        return cacheManager;
//    }
}
