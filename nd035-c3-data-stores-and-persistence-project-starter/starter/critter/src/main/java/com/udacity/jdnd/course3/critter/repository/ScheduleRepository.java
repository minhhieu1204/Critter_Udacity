package com.udacity.jdnd.course3.critter.repository;

import com.udacity.jdnd.course3.critter.entity.EmployeeEntity;
import com.udacity.jdnd.course3.critter.entity.PetEntity;
import com.udacity.jdnd.course3.critter.entity.ScheduleEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ScheduleRepository extends CrudRepository<ScheduleEntity,Long> {

    List<ScheduleEntity> findAll();
    List<ScheduleEntity> findByPets(PetEntity pet);
    List<ScheduleEntity> findByPetsIn(List<PetEntity> pets);
    List<ScheduleEntity> findAllByEmployees(EmployeeEntity employee);
}
