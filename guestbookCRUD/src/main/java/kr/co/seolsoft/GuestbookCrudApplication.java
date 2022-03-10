package kr.co.seolsoft;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class GuestbookCrudApplication {

	public static void main(String[] args) {
		SpringApplication.run(GuestbookCrudApplication.class, args);
	}

}
