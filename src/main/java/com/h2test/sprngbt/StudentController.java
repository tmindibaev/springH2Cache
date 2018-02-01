package com.h2test.sprngbt;

import com.h2test.sprngbt.service.StudentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class StudentController {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    StudentService validateScheme;

    @RequestMapping(method = RequestMethod.GET, produces = "application/json")
    public Student getUser(@RequestParam(value = "userId") Long userId) {
        if (userId == null) {
            logger.info("error 1 -> {}");
        }
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