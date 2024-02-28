package com.example.Employee;

import com.example.Employee.entity.Employee;
import com.example.Employee.entity.EmployeeDTO;
import com.example.Employee.repository.EmployeeRepository;
import com.example.Employee.service.EmployeeService;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class EmployeeServiceTest extends SampleApplicationTests{
    @Mock
    private EmployeeRepository employeeRepository;

    @InjectMocks
    private EmployeeService employeeService;

    @BeforeEach
    public void setup(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void getAllEmployees(){
        when(employeeRepository.findAll()).thenReturn(Arrays.asList(createEmployee(1L, "John", "Developer", Arrays.asList("Java", "Spring")),
                                             createEmployee(2L, "Smith", "Tester", Arrays.asList("Java", "Automation"))));
        List<EmployeeDTO> result = employeeService.getAllEmployees();


        assertEquals("John", result.get(0).getName());
        assertEquals("Smith", result.get(1).getName());
    }

    @Test
    public void getEmployeeById(){
        Employee mockEmployee = createEmployee(1L, "John", "Developer", Arrays.asList("Java", "Spring"));
        when(employeeRepository.findById(anyLong())).thenReturn(Optional.of(mockEmployee));

        EmployeeDTO result = employeeService.getEmployeeById(1L);

        assertEquals("John", result.getName());
    }

    @Test
    public void deleteEmployeeById(){
        employeeService.deleteEmployeeById(1L);
        verify(employeeRepository, times(1)).deleteById(eq(1L));
    }
    @Test
    public void updateEmployee(){
        EmployeeDTO updatedEmployeeDTO = new EmployeeDTO();
        updatedEmployeeDTO.setId(1L);
        updatedEmployeeDTO.setName("Updated Employee");
        updatedEmployeeDTO.setRole("Updated Role");

        when(employeeRepository.findById(anyLong())).thenReturn(Optional.of(new Employee()));
        when(employeeRepository.save(any(Employee.class))).thenReturn(createEmployee(1L, "Updated Employee","Updated Role", null));
        EmployeeDTO result = employeeService.updateEmployee(1L, updatedEmployeeDTO);

        assertEquals("Updated Employee", result.getName());
        assertEquals("Updated Role", result.getRole());
    }

    private Employee createEmployee(Long id, String name, String role, List<String> skills){
        Employee employee = new Employee();
        employee.setId(id);
        employee.setName(name);
        employee.setRole(role);
        employee.setSkills(skills);
        return employee;
    }
}

