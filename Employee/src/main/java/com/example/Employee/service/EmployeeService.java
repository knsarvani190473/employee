package com.example.Employee.service;

import com.example.Employee.entity.Employee;
import com.example.Employee.entity.EmployeeDTO;
import com.example.Employee.exception.EmployeeNotFoundException;
import com.example.Employee.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.stereotype.Service;
import springfox.documentation.annotations.Cacheable;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmployeeService {

    @Autowired
    EmployeeRepository employeeRepository;

    //indicates that result of method is cached,
    //value "cars" is name of the cache
    @Cacheable("employees")
    public List<EmployeeDTO> getAllEmployees(){
        List<Employee> employees = employeeRepository.findAll();
        return employees.stream()
            .map(this::convertToDTO)
            .collect(Collectors.toList());
    }
    @Cacheable("employeeById")
    public EmployeeDTO getEmployeeById(Long id){
        Employee employee = employeeRepository.findById(id).orElseThrow(()->new EmployeeNotFoundException(id));
        return convertToDTO(employee);
    }
    @CachePut(value = "employeeById")
    public EmployeeDTO createEmployee(EmployeeDTO newEmployeeDTO){
        Employee employee = convertToEntity(newEmployeeDTO);
        Employee savedEmployee = employeeRepository.save(employee);
        return convertToDTO(savedEmployee);
    }
    @CacheEvict(cacheNames = {"employees" , "employeeById"})
    public void deleteEmployeeById(Long id){

        employeeRepository.deleteById(id);
    }
    @CachePut(value = "employeeById")
    public EmployeeDTO updateEmployee(Long id, EmployeeDTO updatedEmployeeDTO){
        Employee existingEmployee = employeeRepository.findById(id).orElseThrow(()-> new EmployeeNotFoundException(id));
        existingEmployee.setId(updatedEmployeeDTO.getId());
        existingEmployee.setName(updatedEmployeeDTO.getName());
        existingEmployee.setRole(updatedEmployeeDTO.getRole());
        Employee updatedEmployee = employeeRepository.save(existingEmployee);
        return convertToDTO(updatedEmployee);
    }
    private EmployeeDTO convertToDTO(Employee employee){
        EmployeeDTO employeeDTO = new EmployeeDTO();
        employeeDTO.setId(employee.getId());
        employeeDTO.setName(employee.getName());
        employeeDTO.setRole(employee.getRole());
        employeeDTO.setSkills(employee.getSkills());
        return employeeDTO;
    }
    private Employee convertToEntity(EmployeeDTO employeeDTO){
        Employee employee = new Employee();
        employee.setId(employeeDTO.getId());
        employee.setName(employeeDTO.getName());
        employee.setRole(employeeDTO.getRole());
        employee.setSkills(employeeDTO.getSkills());
        return employee;
    }

}
