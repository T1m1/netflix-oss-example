package com.seitenbau.microservices.support;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.stereotype.Controller;

@SpringBootApplication
@Controller
@EnableZuulProxy
public class EdgeServerApplication {

	public static void main(String[] args) {
		new SpringApplicationBuilder(EdgeServerApplication.class).web(true)
				.run(args);
		// SpringApplication.run(EdgeServerApplication.class, args);
	}
}
