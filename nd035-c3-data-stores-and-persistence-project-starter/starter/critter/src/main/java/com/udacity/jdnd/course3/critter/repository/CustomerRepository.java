package com.udacity.jdnd.course3.critter.repository;

import com.udacity.jdnd.course3.critter.entity.CustomerEntity;
import com.udacity.jdnd.course3.critter.entity.PetEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerRepository extends CrudRepository<CustomerEntity,Long> {

    CustomerEntity findByPets (PetEntity petEntity);
    List<CustomerEntity> findAll();

}
