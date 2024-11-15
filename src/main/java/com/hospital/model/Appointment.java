package com.hospital.model;

import lombok.Data;
import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import com.hospital.enums.AppointmentStatus;

@Entity
@Data
public class Appointment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "patient_id")
    private Patient patient;

    @ManyToOne
    @JoinColumn(name = "doctor_id")
    private Doctor doctor;

    private LocalDateTime appointmentDateTime;
    private AppointmentStatus status; // REQUESTED, CONFIRMED, DECLINED, CANCELLED, COMPLETED
    private AppointmentOutcomes outcomes;

    /** Methods under Appointment */

    @setter
    public void setStatus(AppointmentStatus status) {
        this.status = status;
    }
    @setter
    public void setAppointmentOutcomes(AppointmentOutcomes outcome) {
        this.outcomes = outcome;
    }
    @setter
    public void setAppointmentDateTime(LocalDateTime appointmentDateTime) {
        this.appointmentDateTime = appointmentDateTime;
    }
    @setter
    public void setId(Long id) {
        this.id = id;
    }
    @setter
    public void setPatient(Patient patient) {
        this.patient = patient;
    }
    @setter
    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }


    @getter
    public AppointmentStatus getStatus() {
        return status;
    }
    @getter
    public AppointmentOutcomes getAppointmentOutcomes() {
        return outcomes;
    }
    @getter
    public LocalDateTime getAppointmentDateTime() {
        return appointmentDateTime;
    }
    @getter
    public long getId() {
        return id;
    }
    @getter
    public Patient getPatient() {
        return patient;
    }
    @getter
    public Doctor getDoctor() {
        return doctor;
    }



}
