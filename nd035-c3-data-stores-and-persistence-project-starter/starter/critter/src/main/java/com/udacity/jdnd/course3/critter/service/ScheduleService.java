package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.dto.ScheduleDTO;

import java.util.List;

public interface ScheduleService {
    ScheduleDTO saveSchedule (ScheduleDTO scheduleDTO);
    List<ScheduleDTO> getSchedules ();
    List<ScheduleDTO> getSchedulesByPet (long petId);
    List<ScheduleDTO> getSchedulesByEmployee (long employeeId);
    List<ScheduleDTO> getSchedulesByCustomer (long customerId);

}
