package clone_project.stagram;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
public class StagramApplication {

	public static void main(String[] args) {
		SpringApplication.run(StagramApplication.class, args);
	}

}
