package com.h2test.sprngbt.service;

import com.h2test.sprngbt.CacheConfig;
import com.h2test.sprngbt.Student;
import com.h2test.sprngbt.cache.Cache;
import com.h2test.sprngbt.cache.CacheBuilder;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Service
public class StudentServiceImpl implements StudentService{

    private final JdbcTemplate jdbcTemplate;

    private Cache<Long, String> cache;

    public StudentServiceImpl(JdbcTemplate jdbcTemplate,
                              CacheConfig cacheConfig) {
         cache = new CacheBuilder()
                .setMaxInMemorySize(cacheConfig.getInmemory().getSize())
                .setMaxInStorageSize(cacheConfig.getInstorage().getSize())
                 .setPathToFile(cacheConfig.getInstorage().getDir())
                .build();
        this.jdbcTemplate = jdbcTemplate;
    }

    public void put(Student student) {
        cache.put(student.getId(), student.getName());
        jdbcTemplate.update("INSERT INTO student (id, name, passport_number) " + "VALUES(?,  ?, ?)",
                new Object[]{student.getId(), student.getName(), student.getPassportNumber()});
    }
    public Student get(Long key) {
        Student value;
        if (cache.contains(key)) {
            value = jdbcTemplate.queryForObject("SELECT * FROM student WHERE id=?", new Object[]{key},
                    new BeanPropertyRowMapper<>(Student.class));
            return value;
        }
        return null;
    }
    /*
    @Override
    public boolean contains(Object key) {
        return map.containsKey(key);
    }
    /*
    @Override

    }

    public int update(Student student) {
        return jdbcTemplate.update("UPDATE student " + " SET name = ?, passport_number = ? " + " WHERE id = ?",
                new Object[]{student.getName(), student.getPassportNumber(), student.getId()});
    }

    @Override
    public void putIfAbsent(Object key, Object value) {
List<Student> findAll()
    }

    @Override
    public void remove(K key) {
        jdbcTemplate.update("DELETE FROM student WHERE id=?", new Object[]{key});
    }

    @Override
    public void clear() {

    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public int maxSize() {
        return 0;
    }


    /*public Student findById(long id) {
        return jdbcTemplate.queryForObject("select * from student where id=?", new Object[] { id },
                new BeanPropertyRowMapper<Student>(Student.class));
    }*/
    class StudentRowMapper implements RowMapper<Student> {
        @Override
        public Student mapRow(ResultSet rs, int rowNum) throws SQLException {
            Student student = new Student();
            student.setId(rs.getLong("id"));
            student.setName(rs.getString("name"));
            student.setPassportNumber(rs.getString("passport_number"));
            return student;
        }
    }

    public List<Student> findAll() {
        validateScheme();
        return jdbcTemplate.query("select * from student", new StudentRowMapper());
    }

    private void validateScheme(){};
}