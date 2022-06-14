package com.udacity.jdnd.course3.critter.entity;

import com.udacity.jdnd.course3.critter.utils.EmployeeSkill;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;


@Entity
@Table(name = "schedule")
@Data
public class ScheduleEntity {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToMany
    @JoinTable(name = "schedule_employee", joinColumns = @JoinColumn(name = "schedule_id"), inverseJoinColumns = @JoinColumn(name = "employee_id"))
    private List<EmployeeEntity> employees;

    @ManyToMany
    @JoinTable(name = "schedule_pet", joinColumns = @JoinColumn(name = "schedule_id"), inverseJoinColumns = @JoinColumn(name = "pet_id"))
    private List<PetEntity> pets;

    private LocalDate date;

    @Column
    @ElementCollection(targetClass = EmployeeSkill.class)
    private Set<EmployeeSkill> activities;
}
