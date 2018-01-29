package com.h2test.sprngbt;

import com.h2test.sprngbt.cache.Cache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class StudentJdbcRepository<K, V>
        implements Cache<K, V> {
    @Autowired
    JdbcTemplate jdbcTemplate;

    private final ConcurrentHashMap<K, String> map;
    private final static int MAX_INMEMORY_SIZE = 100;

    public StudentJdbcRepository() {
        this.map = new ConcurrentHashMap<K, String>();
    }

    @Override
    public V get(K key) {
        Student value;
        if (contains(key)) {
            value = jdbcTemplate.queryForObject("SELECT * FROM student WHERE id=?", new Object[]{key},
                    new BeanPropertyRowMapper<>(Student.class));
            return (V) value;
        }
        return null;
    }

    @Override
    public boolean contains(Object key) {
        return map.containsKey(key);
    }

    @Override
    public void put(K key, V value) {
        Student student = new Student((Student) value); // явное приведение???

        map.put(key, student.getName());
        jdbcTemplate.update("INSERT INTO student (id, name, passport_number) " + "VALUES(?,  ?, ?)",
                new Object[]{student.getId(), student.getName(), student.getPassportNumber()});
    }
    //public int insert(Student student) {

    public int update(Student student) {
        return jdbcTemplate.update("UPDATE student " + " SET name = ?, passport_number = ? " + " WHERE id = ?",
                new Object[]{student.getName(), student.getPassportNumber(), student.getId()});
    }


    @Override
    public void putIfAbsent(Object key, Object value) {

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

    public List<Student> findAll() {
        return jdbcTemplate.query("select * from student", new StudentRowMapper());
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
}