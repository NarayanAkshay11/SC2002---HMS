package com.hospital.services;

import com.hospital.enums.AppointmentStatus;
import com.hospital.model.Patient;
import com.hospital.model.Appointment;
import com.hospital.model.MedicalRecord;
import com.hospital.repository.PatientRepository;
import com.hospital.repository.AppointmentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PatientService {

    private final PatientRepository patientRepository;
    private final AppointmentRepository appointmentRepository;

    public PatientService(PatientRepository patientRepository, AppointmentRepository appointmentRepository) {
        this.patientRepository = patientRepository;
        this.appointmentRepository = appointmentRepository;
    }

    // View medical record
    public List<MedicalRecord> getMedicalRecords(Long patientId) {
        Patient patient = patientRepository.findById(patientId)
                .orElseThrow(() -> new RuntimeException("Patient not found"));
        return patient.getMedicalRecords();
    }

    // Update personal information
    @Transactional
    public Patient updatePersonalInfo(Long patientId, String email, String contactNumber) {
        Patient patient = patientRepository.findById(patientId)
                .orElseThrow(() -> new RuntimeException("Patient not found"));
        patient.setEmail(email);
        patient.setContactNumber(contactNumber);
        return patientRepository.save(patient);
    }

    // View available appointment slots
    public List<LocalDateTime> getAvailableSlots() {
        // Implementation to get available slots
        return null;
    }

    // Schedule appointment
    public Appointment scheduleAppointment(Long patientId, Long doctorId, LocalDateTime dateTime) {
        // Implementation to schedule appointment
        return null;
    }

    // View scheduled appointments
    public List<Appointment> getScheduledAppointments(Long patientId) {
        return appointmentRepository.findByPatientIdAndStatusOrderByAppointmentDateTime(
                patientId, "SCHEDULED");
    }

    // View past appointments
    public List<Appointment> getPastAppointments(Long patientId) {
        return appointmentRepository.findByPatientIdAndStatusOrderByAppointmentDateTimeDesc(
                patientId, "COMPLETED");
    }

    // Cancel appointment
    @Transactional
    public void cancelAppointment(Long appointmentId, Long patientId) {
        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new RuntimeException("Appointment not found"));

        if (!appointment.getPatient().getId().equals(patientId)) {
            throw new RuntimeException("Unauthorized to cancel this appointment");
        }

        appointment.setStatus(AppointmentStatus.CANCELLED);
        appointmentRepository.save(appointment);
    }

    // Reschedule appointment
    @Transactional
    public Appointment rescheduleAppointment(Long appointmentId, Long patientId, LocalDateTime newDateTime) {
        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new RuntimeException("Appointment not found"));

        if (!appointment.getPatient().getId().equals(patientId)) {
            throw new RuntimeException("Unauthorized to reschedule this appointment");
        }

        appointment.setAppointmentDateTime(newDateTime);
        return appointmentRepository.save(appointment);
    }
}