package ru.xaero.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.xaero.rest.entity.Employee;
import ru.xaero.rest.exception_handling.EmployeeIncorectData;
import ru.xaero.rest.exception_handling.NoSuchEmployeeException;
import ru.xaero.rest.service.EmployeeService;

import java.util.List;

@RestController
@RequestMapping("/api")
public class MyController {

    @Autowired
    private EmployeeService employeeService;

    @GetMapping("/employees")
    public List<Employee> showAllEmployees(){

        List<Employee> allEmployees = employeeService.getAllEmployees();

        return allEmployees;
    }

    @GetMapping("/employees/{id}")
    public Employee showEmployee(@PathVariable int id){

        Employee employee = employeeService.getEmployee(id);
        if(employee == null){
            throw new NoSuchEmployeeException("There is no employee with ID = " + id + " in Database");
        }
        else{
            return employee;
        }
    }

    @PostMapping("/employees")
    public Employee addNewEmployee(@RequestBody Employee employee){

        employeeService.saveEmployee(employee);

        return employee;
    }

    @PutMapping("/employees")
    public Employee updateEmployee(@RequestBody Employee employee){

        employeeService.saveEmployee(employee);

        return employee;
    }

    @DeleteMapping("/employees/{id}")
    public void deleteEmployee(@PathVariable int id){

        employeeService.deleteEmployee(id);
    }

    @ExceptionHandler
    public ResponseEntity<EmployeeIncorectData> handlerException(
            NoSuchEmployeeException exception
    ){

        EmployeeIncorectData data = new EmployeeIncorectData();
        data.setInfo(exception.getMessage());

        return new ResponseEntity<>(data, HttpStatus.NOT_FOUND);
    }
}
