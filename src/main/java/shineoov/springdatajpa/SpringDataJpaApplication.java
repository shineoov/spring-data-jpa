package shineoov.springdatajpa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import shineoov.springdatajpa.domain.CommonJpaRepository;
import shineoov.springdatajpa.domain.CommonJpaRepositoryImpl;

@SpringBootApplication
@EnableJpaRepositories(repositoryBaseClass = CommonJpaRepositoryImpl.class)
public class SpringDataJpaApplication {
	public static void main(String[] args) {
		SpringApplication.run(SpringDataJpaApplication.class, args);
	}
}
