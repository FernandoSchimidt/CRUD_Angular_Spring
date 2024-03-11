package com.schimidt.crudspring;

import com.schimidt.crudspring.enums.Category;
import com.schimidt.crudspring.model.Course;
import com.schimidt.crudspring.model.Lesson;
import com.schimidt.crudspring.repository.CourseRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class CrudSpringApplication {

	public static void main(String[] args) {
		SpringApplication.run(CrudSpringApplication.class, args);


	}
	@Bean
	CommandLineRunner initDatabse(CourseRepository repository){
		return args -> {
			repository.deleteAll();

			Course c = new Course();
			c.setName("Angular com Spring");
			c.setCategory(Category.FRONT_END);

			Lesson l = new Lesson();
			l.setName("Introdução");
			l.setYoutubeUrl("watch?v=11");
			l.setCourse(c);
		c.getLessons().add(l);

			Lesson l1 = new Lesson();
			l1.setName("Angular 17");
			l1.setYoutubeUrl("watch?v=22");
			l1.setCourse(c);
			c.getLessons().add(l1);

			repository.save(c);
		};
	}

}
