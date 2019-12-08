package com.ibm.userauthenticationservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/*import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;*/


@SpringBootApplication
@EnableSwagger2
@EnableEurekaClient
public class UserAuthenticationServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(UserAuthenticationServiceApplication.class, args);
	}

	@Bean
	public Docket productApi() {
		return new Docket(DocumentationType.SWAGGER_2).select()
				.apis(RequestHandlerSelectors.basePackage("com.ibm.userauthenticationservice")).build();
	}
	
	
	 /* public static final Contact DEFAULT_CONTACT = new Contact("Ravikumar",
			  "https://w3.ibm.com/bluepages/profile.html?uid=04142O744", 
			  "rapothan@in.ibm.com");
	  
	  private static final ApiInfo DEFAULT_API_INFO = new ApiInfo
			  ("USE CASE Api Documentation", "Api Documentation for learning...",
					  "1.0", "urn:tos",
	          DEFAULT_CONTACT, "Apache 2.0", "http://www.apache.org/licenses/LICENSE-2.0");

	@Bean
	public Docket api() {
		Set<String> producesNConsumes= new HashSet<String>
				(Arrays.asList
						("application/json"
								,"application/xml"));
		return new Docket(DocumentationType.SWAGGER_2)
				.apiInfo(DEFAULT_API_INFO)
				.produces(producesNConsumes)
				.consumes(producesNConsumes);
	}*/
}
