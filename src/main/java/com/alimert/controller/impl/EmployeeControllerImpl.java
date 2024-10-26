package com.alimert.controller.impl;

import com.alimert.controller.IEmployeeController;
import com.alimert.dto.DtoEmployee;
import com.alimert.service.IEmployeeService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/employee")
public class EmployeeControllerImpl implements IEmployeeController {

    @Autowired
    private IEmployeeService employeeService;

    @GetMapping("/{id}")
    @Override
    public DtoEmployee findEmployeeById(@PathVariable(value = "id") Long id) {
        return employeeService.findEmployeeById(id);
    }
}
