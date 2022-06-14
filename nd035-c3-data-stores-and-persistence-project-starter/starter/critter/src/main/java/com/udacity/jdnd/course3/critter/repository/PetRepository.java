package com.udacity.jdnd.course3.critter.repository;

import com.udacity.jdnd.course3.critter.entity.CustomerEntity;
import com.udacity.jdnd.course3.critter.entity.PetEntity;
import com.udacity.jdnd.course3.critter.entity.ScheduleEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PetRepository extends CrudRepository<PetEntity, Long> {

    List<PetEntity> findAll();

    List<PetEntity> findByCustomer(CustomerEntity customer);

    List<PetEntity> findBySchedules(ScheduleEntity schedule);
}
