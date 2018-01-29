package com.h2test.sprngbt;

import com.h2test.sprngbt.cache.Cache;
import com.h2test.sprngbt.cache.CacheBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SprngbtApplication  implements CommandLineRunner{
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    StudentJdbcRepository repository;
	public static void main(String[] args) {
	    SpringApplication.run(SprngbtApplication.class, args);
	}
	@Override
    public void run(String... args) throws Exception {
        //logger.info("Student id 10001 -> {}", repository.findById(10001L));

        /*logger.info("All users 1 -> {}", repository.findAll());
        logger.info("Insert user", repository.insert(new Student (10010L,
                "John",
                "A1234657")));
        logger.info("All users 2 -> {}", repository.findAll());*/
        logger.info("All users 1 -> {}", repository.findAll());

        Student student = new Student (10010L,
                "John",
                "A1234657");

        Cache<String, Student> cache = new CacheBuilder()
                .setMaxInMemorySize(10)
                .setMaxInStorageSize(100)
                .build();

        cache.put("1", student);

    }
}
