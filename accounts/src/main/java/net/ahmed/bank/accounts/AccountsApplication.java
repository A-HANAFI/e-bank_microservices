package net.ahmed.bank.accounts;

import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import net.ahmed.bank.accounts.dto.AccountsContactInfoDto;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
@EnableFeignClients
@SpringBootApplication
@EnableJpaAuditing(auditorAwareRef = "auditAwareImpl")
@EnableConfigurationProperties(AccountsContactInfoDto.class)
@OpenAPIDefinition(
		info = @Info(
			title = "Accounts Microservice REST API Documentation",
				description ="EAZYBANK Accounts Microservice REST API Documentation" ,
				version = "V01",
				license = @License(name = "no license required"),
				contact = @Contact(name = "ahmed hanafi",url="")
		),
		externalDocs = @ExternalDocumentation(
				description = "EAZYBANK Accounts Microservice REST API Documentation",
				url = ""
		)
)
public class AccountsApplication {

	public static void main(String[] args) {
		SpringApplication.run(AccountsApplication.class, args);
	}

}
