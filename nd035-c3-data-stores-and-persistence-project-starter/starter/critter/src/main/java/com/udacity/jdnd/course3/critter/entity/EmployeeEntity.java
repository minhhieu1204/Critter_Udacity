package com.udacity.jdnd.course3.critter.entity;

import com.udacity.jdnd.course3.critter.utils.EmployeeSkill;
import lombok.Data;

import javax.persistence.*;
import java.time.DayOfWeek;
import java.util.List;


@Entity
@Table(name = "employee")
@Data
public class EmployeeEntity extends UserEntity{

    @ElementCollection(targetClass = EmployeeSkill.class)
    @CollectionTable(name = "employee_skills")
    private List<EmployeeSkill> skills;

    @ElementCollection(targetClass = DayOfWeek.class)
    @CollectionTable(name = "days_of_week")
    private List<DayOfWeek> daysAvailable;

    @ManyToOne
    @JoinColumn(name = "schedule_id")
    private ScheduleEntity schedule;
}
