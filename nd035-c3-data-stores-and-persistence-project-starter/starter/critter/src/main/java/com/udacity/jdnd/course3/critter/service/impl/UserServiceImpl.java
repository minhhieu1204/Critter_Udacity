package com.udacity.jdnd.course3.critter.service.impl;

import com.udacity.jdnd.course3.critter.dto.CustomerDTO;
import com.udacity.jdnd.course3.critter.entity.CustomerEntity;
import com.udacity.jdnd.course3.critter.repository.CustomerRepository;
import com.udacity.jdnd.course3.critter.service.UserService;
import com.udacity.jdnd.course3.critter.utils.MapperUtil;
import com.udacity.jdnd.course3.critter.utils.ResourceNotFoundException;
import com.udacity.jdnd.course3.critter.utils.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private ResponseUtil responseUtil;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private MapperUtil mapperUtil;


    public CustomerDTO saveCustomer(CustomerDTO customerDTO) {
        try{
            CustomerEntity customer = mapperUtil.map(customerDTO, CustomerEntity.class);
            customer.setId(null);
            customer = customerRepository.save(customer);
            return mapperUtil.map(customer, CustomerDTO.class);
        } catch (Exception e){
            throw new ResourceNotFoundException(e.getMessage());
        }
    }
}
