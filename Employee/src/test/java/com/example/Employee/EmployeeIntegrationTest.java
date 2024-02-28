package com.example.Employee;

import com.example.Employee.controller.EmployeeController;
import com.example.Employee.entity.EmployeeDTO;
import com.example.Employee.repository.EmployeeRepository;
import com.example.Employee.service.EmployeeService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.awt.*;
import java.util.Arrays;

import static net.bytebuddy.matcher.ElementMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(classes = Employee.class)
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
public class EmployeeIntegrationTest extends SampleApplicationTests{
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testGetEmployees() throws Exception{

        mockMvc.perform(MockMvcRequestBuilders.get("/Employee"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();
    }


    @Test
    public void testCreateEmployee() throws Exception {
        EmployeeDTO testEmployeeDTO = new EmployeeDTO();
        testEmployeeDTO.setName("John");
        testEmployeeDTO.setRole("Developer");

        mockMvc.perform(MockMvcRequestBuilders.post("/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testEmployeeDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("John"))
                .andExpect(jsonPath("$.role").value("Developer"));
    }

}
