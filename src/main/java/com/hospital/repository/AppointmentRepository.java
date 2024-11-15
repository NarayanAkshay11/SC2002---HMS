package com.hospital.repository;

import com.hospital.model.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    List<Appointment> findByPatientIdAndStatusOrderByAppointmentDateTime(Long patientId, String status);
    List<Appointment> findByPatientIdAndStatusOrderByAppointmentDateTimeDesc(Long patientId, String status);
}