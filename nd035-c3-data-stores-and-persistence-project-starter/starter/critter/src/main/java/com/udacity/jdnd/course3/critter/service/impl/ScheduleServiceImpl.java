package com.udacity.jdnd.course3.critter.service.impl;

import com.udacity.jdnd.course3.critter.dto.ScheduleDTO;
import com.udacity.jdnd.course3.critter.entity.CustomerEntity;
import com.udacity.jdnd.course3.critter.entity.EmployeeEntity;
import com.udacity.jdnd.course3.critter.entity.PetEntity;
import com.udacity.jdnd.course3.critter.entity.ScheduleEntity;
import com.udacity.jdnd.course3.critter.repository.CustomerRepository;
import com.udacity.jdnd.course3.critter.repository.EmployeeRepository;
import com.udacity.jdnd.course3.critter.repository.PetRepository;
import com.udacity.jdnd.course3.critter.repository.ScheduleRepository;
import com.udacity.jdnd.course3.critter.service.ScheduleService;
import com.udacity.jdnd.course3.critter.utils.CommonUtil;
import com.udacity.jdnd.course3.critter.utils.MapperUtil;
import com.udacity.jdnd.course3.critter.utils.Message;
import com.udacity.jdnd.course3.critter.utils.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ScheduleServiceImpl implements ScheduleService {

    @Autowired
    private ScheduleRepository scheduleRepository;

    @Autowired
    private PetRepository petRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private MapperUtil mapperUtil;

    @Override
    @Transactional
    public ScheduleDTO saveSchedule(ScheduleDTO scheduleDTO) {
        try {
            ScheduleEntity scheduleEntity = mapperUtil.map(scheduleDTO, ScheduleEntity.class);
            scheduleEntity.setId(null);
            List<EmployeeEntity> employeeEntities = (List<EmployeeEntity>) employeeRepository.findAllById(scheduleDTO.getEmployeeIds());
            scheduleEntity.setEmployees(employeeEntities);
            List<PetEntity> petEntities = (List<PetEntity>) petRepository.findAllById(scheduleDTO.getPetIds());
            scheduleEntity.setPets(petEntities);
            scheduleEntity = scheduleRepository.save(scheduleEntity);
            scheduleDTO.setId(scheduleEntity.getId());

            return scheduleDTO;
        } catch (Exception e) {
            throw new ResourceNotFoundException(e.getMessage());
        }
    }

    @Override
    public List<ScheduleDTO> getSchedules() {
        try {
            List<ScheduleEntity> schedules = scheduleRepository.findAll();
            return getInfoForScheduleDTO(schedules);
        } catch (Exception e) {
            throw new ResourceNotFoundException(e.getMessage());
        }
    }

    @Override
    public List<ScheduleDTO> getSchedulesByPet(long petId) {
        try {
            if (CommonUtil.isEmpty(petId)) {
                throw new ResourceNotFoundException(Message.PET_ID_REQUIRED);
            }
            PetEntity pet = petRepository.findById(petId).orElse(null);
            if (CommonUtil.isEmpty(pet)) {
                throw new ResourceNotFoundException(Message.PET_NOT_FOUND);
            }
            List<ScheduleEntity> schedules = scheduleRepository.findByPets(pet);
            return getInfoForScheduleDTO(schedules);
        } catch (Exception e) {
            throw new ResourceNotFoundException(e.getMessage());
        }
    }

    @Override
    public List<ScheduleDTO> getSchedulesByEmployee(long employeeId) {
        try {
            if (CommonUtil.isEmpty(employeeId)) {
                throw new ResourceNotFoundException(Message.EMPLOYEE_ID_REQUIRED);
            }

            EmployeeEntity employee = employeeRepository.findById(employeeId).orElse(null);
            if (CommonUtil.isEmpty(employee)) {
                throw new ResourceNotFoundException(Message.EMPLOYEE_NOT_FOUND);
            }
            List<ScheduleEntity> schedules = scheduleRepository.findAllByEmployees(employee);

            return getInfoForScheduleDTO(schedules);
        } catch (Exception e) {
            throw new ResourceNotFoundException(e.getMessage());
        }
    }

    @Override
    public List<ScheduleDTO> getSchedulesByCustomer(long customerId) {
        try {
            if (CommonUtil.isEmpty(customerId)) {
                throw new ResourceNotFoundException(Message.CUSTOMER_ID_REQUIRED);
            }
            CustomerEntity customer = customerRepository.findById(customerId).orElse(null);
            if(CommonUtil.isEmpty(customer)){
                throw new ResourceNotFoundException(Message.CUSTOMER_NOT_FOUND);
            }
            List<PetEntity> petEntities = petRepository.findByCustomer(customer);
            List<ScheduleEntity> schedules = scheduleRepository.findByPetsIn(petEntities);
            return getInfoForScheduleDTO(schedules);

        } catch (Exception e) {
            throw new ResourceNotFoundException(e.getMessage());
        }
    }

    private List<ScheduleDTO> getInfoForScheduleDTO (List<ScheduleEntity> schedules){
        List<ScheduleDTO> scheduleDTOS = schedules.stream().map(s -> {
            ScheduleDTO scheduleDTO = mapperUtil.map(s, ScheduleDTO.class);
            scheduleDTO.setPetIds(petRepository.findBySchedules(s).stream().map(p -> p.getId()).collect(Collectors.toList()));
            scheduleDTO.setEmployeeIds(employeeRepository.findBySchedules(s).stream().map(e -> e.getId()).collect(Collectors.toList()));
            return scheduleDTO;
        }).collect(Collectors.toList());
        return scheduleDTOS;
    }



}
