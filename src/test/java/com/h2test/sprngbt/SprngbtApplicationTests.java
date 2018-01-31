package com.h2test.sprngbt;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.CoreMatchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class SprngbtApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testGetStatusOk() throws Exception {
        String userId = "10001";

        this.mockMvc.perform(get("/user").param("userId", userId))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void testGetStatusBad() throws Exception {
        String userId = "99999d";

        this.mockMvc.perform(get("/user").param("userId", userId))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void testPutAndGetStatusOk() throws Exception {
        Student student = new Student();
        student.setId(10003L);
        student.setName("Ivan");
        student.setPassportNumber("123qeqe");
    }
}
