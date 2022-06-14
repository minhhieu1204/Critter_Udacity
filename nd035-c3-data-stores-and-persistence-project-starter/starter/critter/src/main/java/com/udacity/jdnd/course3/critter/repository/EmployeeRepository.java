package com.udacity.jdnd.course3.critter.repository;

import com.udacity.jdnd.course3.critter.entity.EmployeeEntity;
import com.udacity.jdnd.course3.critter.entity.ScheduleEntity;
import com.udacity.jdnd.course3.critter.utils.EmployeeSkill;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.DayOfWeek;
import java.util.List;
import java.util.Set;

@Repository
public interface EmployeeRepository extends CrudRepository<EmployeeEntity,Long> {

    List<EmployeeEntity> findBySkillsInAndDaysAvailable (Set<EmployeeSkill> skills, DayOfWeek dayOfWeek);
    List<EmployeeEntity> findBySchedules (ScheduleEntity schedule);
}
