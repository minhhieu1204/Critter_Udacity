package com.udacity.jdnd.course3.critter.service.impl;

import com.udacity.jdnd.course3.critter.dto.CustomerDTO;
import com.udacity.jdnd.course3.critter.dto.PetDTO;
import com.udacity.jdnd.course3.critter.entity.CustomerEntity;
import com.udacity.jdnd.course3.critter.entity.PetEntity;
import com.udacity.jdnd.course3.critter.repository.CustomerRepository;
import com.udacity.jdnd.course3.critter.repository.PetRepository;
import com.udacity.jdnd.course3.critter.service.PetService;
import com.udacity.jdnd.course3.critter.utils.CommonUtil;
import com.udacity.jdnd.course3.critter.utils.MapperUtil;
import com.udacity.jdnd.course3.critter.utils.Message;
import com.udacity.jdnd.course3.critter.utils.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PetServiceImpl implements PetService {

    @Autowired
    private PetRepository petRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private MapperUtil mapperUtil;

    @Override
    public PetDTO savePet(PetDTO petDTO) {
        try {

            Optional<CustomerEntity> owner = customerRepository.findById(petDTO.getOwnerId());
            if (!owner.isPresent()) {
                throw new ResourceNotFoundException(Message.CUSTOMER_NOT_FOUND);
            }

            PetEntity petEntity = mapperUtil.map(petDTO, PetEntity.class);
            petEntity.setId(null);
            petEntity.setCustomer(owner.get());
            petEntity = petRepository.save(petEntity);
            petDTO = mapperUtil.map(petEntity, PetDTO.class);
            petDTO.setOwnerId(owner.get().getId());
            return petDTO;

        } catch (Exception e) {
            throw new ResourceNotFoundException(e.getMessage());
        }
    }

    @Override
    public PetDTO getPet(long petId) {
        try {
            if (CommonUtil.isEmpty(petId)) {
                throw new ResourceNotFoundException(Message.PET_ID_REQUIRED);
            }
            PetEntity pet = petRepository.findById(petId).orElse(null);
            if (CommonUtil.isEmpty(pet)) {
                throw new ResourceNotFoundException(Message.PET_NOT_FOUND);
            }
            PetDTO petDTO = mapperUtil.map(pet, PetDTO.class);
            petDTO.setOwnerId(pet.getCustomer().getId());
            return petDTO;
        } catch (Exception e) {
            throw new ResourceNotFoundException(e.getMessage());
        }
    }

    @Override
    public List<PetDTO> getPets() {
        try {
            List<PetEntity> pets = petRepository.findAll();
            return mapperUtil.mapToList(pets, PetDTO.class);
        } catch (Exception e) {
            throw new ResourceNotFoundException(e.getMessage());
        }
    }

    @Override
    public List<PetDTO> getPetsByOwner(long customerId) {
        try {
            if (CommonUtil.isEmpty(customerId)) {
                throw new ResourceNotFoundException(Message.CUSTOMER_ID_REQUIRED);
            }
            CustomerEntity customer = customerRepository.findById(customerId).orElse(null);
            if (CommonUtil.isEmpty(customer)) {
                throw new ResourceNotFoundException(Message.CUSTOMER_NOT_FOUND);
            }

            List<PetEntity> pets = petRepository.findByCustomer(customer);
            List<PetDTO> petDTOS = pets.stream().map(p -> {
                PetDTO petDTO =  mapperUtil.map(p, PetDTO.class);
                petDTO.setOwnerId(p.getCustomer().getId());
                return petDTO;
            }).collect(Collectors.toList());
            return petDTOS;
        } catch (Exception e) {
            throw new ResourceNotFoundException(e.getMessage());
        }
    }
}
