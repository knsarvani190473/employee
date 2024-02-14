package com.example.Employee.controller;

import com.example.Employee.entity.EmployeeDTO;
import com.example.Employee.service.EmployeeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class EmployeeController {

    @Autowired
    EmployeeService employeeService;

    @GetMapping("/Employee")
    public List<EmployeeDTO> getEmployees(){

        return employeeService.getAllEmployees();
    }

    @GetMapping("/Employee/{id}")
    public EmployeeDTO getEmployeeById(@PathVariable Long id){

        return employeeService.getEmployeeById(id);
    }

    @PostMapping("/create")
    public EmployeeDTO createEmployee(@Valid @RequestBody EmployeeDTO newEmployeeDTO){
        return employeeService.createEmployee(newEmployeeDTO);
    }

    @PutMapping("/update/{id}")
    public EmployeeDTO updateEmployee(@PathVariable Long id, @RequestBody EmployeeDTO updatedEmployeeDTO)
    {
        return employeeService.updateEmployee(id,updatedEmployeeDTO);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteEmployee(@PathVariable Long id){
        employeeService.deleteEmployeeById(id);
    }

}
