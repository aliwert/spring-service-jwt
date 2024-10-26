package com.alimert.service;

import com.alimert.dto.DtoEmployee;

public interface IEmployeeService {

    public DtoEmployee findEmployeeById(Long id);
}
