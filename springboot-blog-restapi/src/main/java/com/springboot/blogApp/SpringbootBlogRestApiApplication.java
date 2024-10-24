package com.springboot.blogApp;

import com.springboot.blogApp.customExceptions.ResourceNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SpringbootBlogRestApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootBlogRestApiApplication.class, args);
		System.out.println("SpringBootApplication Ran Successfully ");

	}

//	let's define the ModelMapper bean in out Spring Configuration (since @SpringBootApplication annotated so internally uses bean)
	// https://www.baeldung.com/java-modelmapper
	// https://www.geeksforgeeks.org/how-to-use-modelmapper-in-spring-boot-with-example-project/
	// https://www.youtube.com/watch?v=OhzSEhXgi4o
	@Bean
	public ModelMapper modelMapper(){ // 86. Map Post Entity to Post DTO using ModelMapper
		return new ModelMapper();
	} // now we need to inject and use the ModelMapper (wherever we use we just annotate autowired it will create the ModelMapper object and let us use them )
/*
IMP:
It will  show how to map our data between differently structured objects in ModelMapper. It basically does the mapToEntity and mapTODto work. here we just need to pass the what need to convert and in which we want to convert But make ure fields are same as model(Entity in the Dtos)
 */

}
