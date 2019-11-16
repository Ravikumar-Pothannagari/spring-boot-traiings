package com.ibm.eurekadiscoverserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;
/**
 * @author RavikumarPothannagar
 *
 */
@SpringBootApplication
@EnableEurekaServer
@RefreshScope
public class EurekaDiscoverServerApplication {
	public static void main(String[] args) {
		SpringApplication.run(EurekaDiscoverServerApplication.class, args);
	}

}
