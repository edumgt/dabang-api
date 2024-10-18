package com.station3.dabang.config;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.security.SecuritySchemes;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.media.DateSchema;
import io.swagger.v3.oas.models.media.DateTimeSchema;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.servers.Server;

@Configuration
@SecuritySchemes({
		@SecurityScheme(name = "bearerAuth", type = SecuritySchemeType.HTTP, bearerFormat = "JWT", scheme = "bearer")
})
public class OpenAPIConfiguration {



	@Bean
	public OpenAPI springShopOpenAPI() {

		String serverUrl =  "http://127.0.0.1:8888";

		return new OpenAPI().addSecurityItem(
						new SecurityRequirement().addList("bearerAuth"))
						.info(new Info().title("Open Hospital API Documentation")
										.description("Open Hospital API Documentation")
										.version("0.1.0").contact(new Contact().name("ApiInfo.DEFAULT_CONTACT"))
										.license(new License().name("GPL-3.0 license")
														.url("https://github.com/informatici/openhospital-api?tab=GPL-3.0-1-ov-file#readme")))
						.servers(List.of(new Server().url(serverUrl)))
						.components(new Components().schemas(Map.of(
										"LocalDate", new DateSchema().name("LocalDate").type("string").format(null),
										"LocalDateTime", new DateTimeSchema().name("LocalDateTime").type("string").format(null))));
	}
}
