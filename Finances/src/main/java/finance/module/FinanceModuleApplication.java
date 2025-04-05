package finance.module;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "finance.module.repository")
@EntityScan(basePackages = "finance.module.model")
public class FinanceModuleApplication {
    public static void main(String[] args) {
        SpringApplication.run(FinanceModuleApplication.class, args);
    }
}