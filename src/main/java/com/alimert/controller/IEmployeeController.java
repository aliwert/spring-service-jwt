package com.alimert.controller;

import com.alimert.dto.DtoEmployee;
import com.alimert.model.Employee;

public interface IEmployeeController {
    public DtoEmployee findEmployeeById(Long id);
}
