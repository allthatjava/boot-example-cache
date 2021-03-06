package brian.example.boot.cache;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
public class BootExampleCacheApplication {

	public static void main(String[] args) {
		SpringApplication.run(BootExampleCacheApplication.class, args);
	}
}
