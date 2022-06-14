package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.dto.CustomerDTO;
import com.udacity.jdnd.course3.critter.dto.EmployeeDTO;
import com.udacity.jdnd.course3.critter.dto.EmployeeRequestDTO;

import java.time.DayOfWeek;
import java.util.List;
import java.util.Set;

public interface UserService {
    CustomerDTO saveCustomer(CustomerDTO customerDTO);
    CustomerDTO getCustomerByPet(long petId);
    List<CustomerDTO> getCustomers ();

    EmployeeDTO saveEmployee(EmployeeDTO employeeDTO);

    EmployeeDTO setAvailable(Set<DayOfWeek> dayOfWeeks, long employeeId);

    List<EmployeeDTO> findEmployeesForService(EmployeeRequestDTO employeeRequestDTO);
    EmployeeDTO getEmployee(long employeeId);
}
