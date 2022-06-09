package com.udacity.jdnd.course3.critter.entity;

import com.udacity.jdnd.course3.critter.utils.EmployeeSkill;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;


@Entity
@Table(name = "schedule")
@Data
public class ScheduleEntity {

    @Id
    @GeneratedValue
    private long id;

    @OneToMany(mappedBy = "schedule")
    private List<EmployeeEntity> employees;

    @OneToMany(mappedBy = "schedule")
    private List<PetEntity> pets;

    private LocalDate date;

    @ElementCollection(targetClass = EmployeeSkill.class)
    @CollectionTable(name = "employee_skills")
    private List<EmployeeSkill> activities;
}
