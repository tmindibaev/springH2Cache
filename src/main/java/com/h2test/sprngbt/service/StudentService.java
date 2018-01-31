package com.h2test.sprngbt.service;

import com.h2test.sprngbt.Student;

import java.util.List;

public interface StudentService {
    void put(Student student);
    Student get(Long key);
    List<Student> findAll();
}
