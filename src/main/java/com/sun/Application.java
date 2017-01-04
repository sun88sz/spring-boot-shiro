package com.sun;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 *
 */
@SpringBootApplication(scanBasePackages = { ".", "com" })
public class Application extends WebMvcConfigurerAdapter {

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
		registry.addResourceHandler("/templates/**").addResourceLocations("classpath:/templates/");
	}

	public static void main(String[] args) throws Exception {
		ApplicationContext ctx = SpringApplication.run(Application.class, args);
	}

//	@Bean
//	public EmbeddedServletContainerCustomizer containerCustomizer() {
//		return (container -> {
//			ErrorPage error401Page = new ErrorPage(HttpStatus.UNAUTHORIZED, "/401.html");
//			ErrorPage error403Page = new ErrorPage(HttpStatus.FORBIDDEN, "/403.html");
//			ErrorPage error404Page = new ErrorPage(HttpStatus.NOT_FOUND, "/404.html");
//			ErrorPage error500Page = new ErrorPage(HttpStatus.INTERNAL_SERVER_ERROR, "/500.html");
//
//			container.addErrorPages(error401Page, error403Page, error404Page, error500Page);
//		});
//	}
}
