package com.udacity.jdnd.course3.critter.entity;

import com.udacity.jdnd.course3.critter.utils.EmployeeSkill;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.DayOfWeek;
import java.util.List;
import java.util.Set;


@Entity
@Table(name = "employee")
@Data
public class EmployeeEntity extends UserEntity{

    @Column
    @ElementCollection(targetClass = EmployeeSkill.class)
    private Set<EmployeeSkill> skills;

    @Column
    @ElementCollection(targetClass = DayOfWeek.class)
    private Set<DayOfWeek> daysAvailable;

    @ManyToMany(mappedBy = "employees")
    private List<ScheduleEntity> schedules;
}
