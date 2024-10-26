package com.alimert.service.impl;

import com.alimert.dto.DtoDepartment;
import com.alimert.dto.DtoEmployee;
import com.alimert.model.Department;
import com.alimert.model.Employee;
import com.alimert.repository.EmployeeRepository;
import com.alimert.service.IEmployeeService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class EmployeeServiceImpl implements IEmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    public DtoEmployee findEmployeeById(Long id) {
        DtoDepartment dtoDepartment = new DtoDepartment();
        DtoEmployee dtoEmployee = new DtoEmployee();
        Optional<Employee> optional = employeeRepository.findById(id);
        if (optional.isEmpty()) {
            return null;
        }
        Employee employee = optional.get();
        Department department = employee.getDepartment();
        BeanUtils.copyProperties(employee, dtoEmployee);
        BeanUtils.copyProperties(department, dtoDepartment);
        dtoEmployee.setDepartment(dtoDepartment);

        return dtoEmployee;
    }
}
