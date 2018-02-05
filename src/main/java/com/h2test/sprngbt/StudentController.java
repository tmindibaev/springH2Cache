package com.h2test.sprngbt;

import com.h2test.sprngbt.service.StudentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@RestController
@RequestMapping("/user")
@Validated
public class StudentController {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    StudentService validateScheme;

    @RequestMapping(method = RequestMethod.GET, produces = "application/json")
    public Student getUser( @RequestParam(value = "userId") @Valid @NotNull  Long userId) {

        logger.info("user id  -> {}", userId);
        Student student = validateScheme.get(userId);

        return student;
    }


    @RequestMapping(method = RequestMethod.POST, produces = "application/json")
    public void updateUser(@RequestParam(value = "userId") Long userId,
                           @RequestParam(value = "userName") String userName,
                           @RequestParam(value = "passportNumber") String passportNumber) {
        Student student = new Student(userId,
                userName,
                passportNumber);

        //validateScheme.update(student);
    }

    @RequestMapping(method = RequestMethod.PUT, produces = "application/json")
    public void insertUser(@RequestParam(value = "userId") Long userId,
                           @RequestParam(value = "userName") String userName,
                           @RequestParam(value = "passportNumber") String passportNumber) {
        Student student = new Student(userId,
                userName,
                passportNumber);

        validateScheme.put(student);
        logger.info("All users 1 -> {}", validateScheme.findAll());
    }
}