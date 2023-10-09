package ecommerce.mobile;

import java.util.concurrent.TimeUnit;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.CacheManager;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;

import com.github.benmanes.caffeine.cache.Caffeine;

import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "Mobile Bill App Apis Documentation", description = "Spring boot Mobile Bill App Documentation", version = "v1.0", contact = @Contact(name = "Thaipeiidev", email = "thaipeiidev@gmail.com", url = "https://www.facebook.com/profile.php?id=100020256826562"), license = @License(name = "Apache 2.0", url = "https://www.facebook.com/profile.php?id=100020256826562")), externalDocs = @ExternalDocumentation(description = "Mobile Bill App Documentation"))
public class NluApplication {
	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}

	@Bean
	public Caffeine caffeineConfig() {
		return Caffeine.newBuilder().expireAfterWrite(5, TimeUnit.MINUTES);
	}

	@Bean
	public CacheManager cacheManager(Caffeine caffeine) {
		CaffeineCacheManager caffeineCacheManager = new CaffeineCacheManager();
		caffeineCacheManager.setCaffeine(caffeine);
		return caffeineCacheManager;
	}

	public static void main(String[] args) {
		SpringApplication.run(NluApplication.class, args);
	}

}
