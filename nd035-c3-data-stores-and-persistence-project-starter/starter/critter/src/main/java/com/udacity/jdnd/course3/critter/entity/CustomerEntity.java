package com.udacity.jdnd.course3.critter.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;


@Entity
@Table(name = "customer")
@Data
public class CustomerEntity extends UserEntity{

    @Column(length = 10)
    private String phoneNumber;

    @OneToMany(mappedBy = "customer")
    private List<PetEntity> pets;

    private String notes;

}
