package com.udacity.jdnd.course3.critter.service.impl;

import com.udacity.jdnd.course3.critter.dto.CustomerDTO;
import com.udacity.jdnd.course3.critter.dto.EmployeeDTO;
import com.udacity.jdnd.course3.critter.dto.EmployeeRequestDTO;
import com.udacity.jdnd.course3.critter.entity.CustomerEntity;
import com.udacity.jdnd.course3.critter.entity.EmployeeEntity;
import com.udacity.jdnd.course3.critter.entity.PetEntity;
import com.udacity.jdnd.course3.critter.repository.CustomerRepository;
import com.udacity.jdnd.course3.critter.repository.EmployeeRepository;
import com.udacity.jdnd.course3.critter.repository.PetRepository;
import com.udacity.jdnd.course3.critter.service.UserService;
import com.udacity.jdnd.course3.critter.utils.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private ResponseUtil responseUtil;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private PetRepository petRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private MapperUtil mapperUtil;


    public CustomerDTO saveCustomer(CustomerDTO customerDTO) {
        try {
            CustomerEntity customer = mapperUtil.map(customerDTO, CustomerEntity.class);
            customer.setId(null);
            customer = customerRepository.save(customer);
            return mapperUtil.map(customer, CustomerDTO.class);
        } catch (Exception e) {
            throw new ResourceNotFoundException(e.getMessage());
        }
    }

    @Override
    public CustomerDTO getCustomerByPet(long petId) {
        try {
            if (CommonUtil.isEmpty(petId)) {
                throw new ResourceNotFoundException(Message.PET_ID_REQUIRED);
            }
            PetEntity pet = petRepository.findById(petId).orElse(null);
            if (CommonUtil.isEmpty(pet)) {
                throw new ResourceNotFoundException(Message.PET_NOT_FOUND);
            }

            CustomerEntity customer = customerRepository.findByPets(pet);
            if (CommonUtil.isEmpty(customer)) {
                throw new ResourceNotFoundException(Message.CUSTOMER_NOT_FOUND);
            }
            CustomerDTO customerDTO = mapperUtil.map(customer, CustomerDTO.class);
            List<PetEntity> ownerPet = petRepository.findByCustomer(customer);
            customerDTO.setPetIds(ownerPet.stream().map( p -> p.getId()).collect(Collectors.toList()));
            return customerDTO;
        } catch (Exception e) {
            throw new ResourceNotFoundException(e.getMessage());
        }
    }

    @Override
    public List<CustomerDTO> getCustomers() {
        try {
            List<CustomerEntity> customers = customerRepository.findAll();
            List<CustomerDTO> customerDTOS = customers.stream().map(c -> {
                List<PetEntity> petEntities = petRepository.findByCustomer(c);
                CustomerDTO customerDTO = mapperUtil.map(c,CustomerDTO.class);
                customerDTO.setPetIds(petEntities.stream().map(p -> p.getId()).collect(Collectors.toList()));
                return customerDTO;
            }).collect(Collectors.toList());
            return customerDTOS;
        } catch (Exception e) {
            throw new ResourceNotFoundException(e.getMessage());
        }
    }

    @Override
    public EmployeeDTO saveEmployee(EmployeeDTO employeeDTO) {
        try {
            EmployeeEntity employeeEntity = mapperUtil.map(employeeDTO, EmployeeEntity.class);
            employeeEntity.setId(null);
            employeeEntity = employeeRepository.save(employeeEntity);
            return mapperUtil.map(employeeEntity, EmployeeDTO.class);
        } catch (Exception e) {
            throw new ResourceNotFoundException(e.getMessage());
        }
    }

    @Override
    public EmployeeDTO setAvailable(Set<DayOfWeek> dayOfWeeks, long employeeId) {
        try {
            EmployeeEntity employeeEntity = employeeRepository.findById(employeeId).orElse(null);
            if (CommonUtil.isEmpty(employeeEntity)) {
                throw new ResourceNotFoundException(Message.EMPLOYEE_NOT_FOUND);
            }
            employeeEntity.setDaysAvailable(dayOfWeeks);
            employeeEntity = employeeRepository.save(employeeEntity);
            return mapperUtil.map(employeeEntity, EmployeeDTO.class);
        } catch (Exception e) {
            throw new ResourceNotFoundException(e.getMessage());
        }
    }

    @Override
    public List<EmployeeDTO> findEmployeesForService(EmployeeRequestDTO employeeRequestDTO) {
        try {
            DayOfWeek day = employeeRequestDTO.getDate().getDayOfWeek();
            Set<EmployeeSkill> skills = employeeRequestDTO.getSkills();
            List<EmployeeEntity> employeeEntities = employeeRepository.findBySkillsInAndDaysAvailable(skills, day);
            return mapperUtil.mapToList(employeeEntities, EmployeeDTO.class);
        } catch (Exception e) {
            throw new ResourceNotFoundException(e.getMessage());
        }
    }

    @Override
    public EmployeeDTO getEmployee(long employeeId) {
        try {
            if (CommonUtil.isEmpty(employeeId)) {
                throw new ResourceNotFoundException(Message.EMPLOYEE_ID_REQUIRED);
            }

            EmployeeEntity employee = employeeRepository.findById(employeeId).orElse(null);
            if (CommonUtil.isEmpty(employee)) {
                throw new ResourceNotFoundException(Message.EMPLOYEE_NOT_FOUND);
            }
            return mapperUtil.map(employee, EmployeeDTO.class);
        } catch (Exception e) {
            throw new ResourceNotFoundException(e.getMessage());
        }
    }
}
