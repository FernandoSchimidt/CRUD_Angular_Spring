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
    CommandLineRunner initDatabse(CourseRepository repository) {
        return args -> {
            repository.deleteAll();

            for (int i = 0; i <= 23; i++) {

                Course c = new Course();
                c.setName("Angular com Spring" + i);
                c.setCategory(Category.FRONT_END);

                Lesson l = new Lesson();
                l.setName("Introdução");
                l.setYoutubeUrl("watch?v=1");
                l.setCourse(c);
                c.getLessons().add(l);

                repository.save(c);
            }
        };
    }

}
