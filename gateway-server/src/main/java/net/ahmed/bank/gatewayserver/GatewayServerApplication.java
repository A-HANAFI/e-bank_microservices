package net.ahmed.bank.gatewayserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

import java.time.LocalDateTime;

@SpringBootApplication
public class GatewayServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(GatewayServerApplication.class, args);
	}

//	@Bean
//	public RouteLocator eazyBankRouteConfig(RouteLocatorBuilder routeLocatorBuilder) {
//		return routeLocatorBuilder.routes()
//				.route(p -> p
//						.path("/ahmedbank/accounts/**")
//						.filters( f -> f.rewritePath("/ahmedbank/accounts/(?<segment>.*)","/${segment}")
//								.addResponseHeader("X-Response-Time", LocalDateTime.now().toString()))
//						.uri("lb://ACCOUNTS"))
//				.route(p -> p
//						.path("/eazybank/loans/**")
//						.filters( f -> f.rewritePath("/eazybank/loans/(?<segment>.*)","/${segment}")
//								.addResponseHeader("X-Response-Time", LocalDateTime.now().toString()))
//						.uri("lb://LOANS"))
//				.route(p -> p
//						.path("/eazybank/cards/**")
//						.filters( f -> f.rewritePath("/eazybank/cards/(?<segment>.*)","/${segment}")
//								.addResponseHeader("X-Response-Time", LocalDateTime.now().toString()))
//						.uri("lb://CARDS")).build();


//	}
	@Bean
	public RouteLocator AhmedBankRouteLocator(RouteLocatorBuilder routeLocatorBuilder){
		return routeLocatorBuilder.routes()
				.route(p ->  p
						.path("/ahmedbank/accounts/**")
						.filters(f -> f.rewritePath("/ahmedbank/accounts/(?<segment>.*)","/${segment}")
										.addResponseHeader("X-time", LocalDateTime.now().toString())
						)
						.uri("lb://ACCOUNTS"))
				.route(p ->  p
						.path("/ahmedbank/cards/**")
						.filters(f -> f.rewritePath("/ahmedbank/cards/(?<segment>.*)","/${segment}")
								.addResponseHeader("X-time", LocalDateTime.now().toString())
						)
						.uri("lb://CARDS"))
				.route(p ->  p
						.path("/ahmedbank/loans/**")
						.filters(f -> f.rewritePath("/ahmedbank/loans/(?<segment>.*)","/${segment}")
								.addResponseHeader("X-time", LocalDateTime.now().toString())
						)
						.uri("lb://LOANS")).build();
	}

}
