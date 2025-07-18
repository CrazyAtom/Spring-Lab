package lab.bookshop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.fasterxml.jackson.datatype.hibernate5.jakarta.Hibernate5JakartaModule;

@SpringBootApplication
public class BookshopApplication {

	public static void main(String[] args) {
		SpringApplication.run(BookshopApplication.class, args);
	}

	@Bean
	Hibernate5JakartaModule hibernate5Module() {
		Hibernate5JakartaModule hibernate5JakartaModule = new Hibernate5JakartaModule();
		// hibernate5JakartaModule.configure(Hibernate5JakartaModule.Feature.FORCE_LAZY_LOADING, true);
		return hibernate5JakartaModule;
	}
}
