package com.example.demo;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@Bean
	CommandLineRunner runner(StudentRepository studentRepository, MongoTemplate mongoTemplate){
		return args -> {
			Address address = new Address(
				"England",
				"London",
				"NE9"
			);
			String email = "jamed@gmail.com";
			Student student = new Student(
				"jamila",
				"Ahmed",
				email,
				Gender.FEMALE,
				address,
				List.of("Computer Science", "Maths"),
				BigDecimal.TEN,
				LocalDateTime.now()

			);

			// usingMongoTemplateAndQuery(studentRepository, mongoTemplate, student, email);

			studentRepository.findStudentByEmail(email)
			.ifPresentOrElse(s -> {
				System.out.println(s + " already exists");
			}, () -> {
				System.out.println("Inserting student " + student);
				studentRepository.insert(student);
			});
			
		};
	}

	public void usingMongoTemplateAndQuery(StudentRepository studentRepository, MongoTemplate mongoTemplate, Student student, String email){
		Query query = new Query();
			query.addCriteria(Criteria.where("email").is(email));

			List<Student> students = mongoTemplate.find(query, Student.class);
	
			if (students.size() > 1) {
				throw new IllegalStateException("found many student with email " + email);
			}

			if (students.isEmpty()){
				System.out.println("Inserting student " + student);
				studentRepository.insert(student);
			} else {
				System.out.println(student + " already exists");
			}
	}

}
