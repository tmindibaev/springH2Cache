package com.h2test.sprngbt;

import com.h2test.sprngbt.service.StudentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;


@RestController
@RequestMapping("/user")
public class StudentController {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    StudentService validateScheme;

    @ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "No such Order")  // 404
    public class OrderNotFoundException extends RuntimeException {
        public OrderNotFoundException(String userId) {
        }
    }

    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR, reason = "Server error") //500
    public class InternalServerErrorException extends RuntimeException {
        public InternalServerErrorException() {
        }
    }

    @RequestMapping(method = RequestMethod.GET)
    public Student getUser(@RequestParam(value = "userId") Long userId) {
        if (userId == null) throw new OrderNotFoundException("empty user");

        Student student = validateScheme.get(userId);
        if (student == null) throw new OrderNotFoundException(userId.toString());
        return student;
    }


    @RequestMapping(method = RequestMethod.POST)
    public void updateUser(@RequestParam(value = "userId") Long userId,
                           @RequestParam(value = "userName") String userName,
                           @RequestParam(value = "passportNumber") String passportNumber) {
        Student student = new Student(userId,
                userName,
                passportNumber);

        //validateScheme.update(student);
    }

    @RequestMapping(method = RequestMethod.PUT)
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