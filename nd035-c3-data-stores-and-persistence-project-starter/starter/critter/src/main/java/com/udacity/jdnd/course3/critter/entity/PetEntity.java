package com.udacity.jdnd.course3.critter.entity;

import lombok.Data;
import org.hibernate.annotations.Nationalized;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;


@Entity
@Table(name = "pet")
@Data
public class PetEntity {
    @Id
    @GeneratedValue
    private Long id;

    private String type;

    @Nationalized
    private String name;
    private LocalDate birthDate;
    private String notes;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private CustomerEntity customer;

    @ManyToMany(mappedBy = "pets")
    private List<ScheduleEntity> schedules;

}
