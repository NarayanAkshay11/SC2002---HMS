package com.hospital.controller;

import com.hospital.model.Patient;
import com.hospital.model.Appointment;
import com.hospital.model.MedicalRecord;
import com.hospital.services.PatientService;
import com.hospital.services.PatientDataImportService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/patients")
public class PatientController {

    private final PatientService patientService;
    private final PatientDataImportService importService;

    public PatientController(PatientService patientService, PatientDataImportService importService) {
        this.patientService = patientService;
        this.importService = importService;
    }

    @PostMapping("/import")
    public ResponseEntity<List<Patient>> importPatients(@RequestParam("file") MultipartFile file) throws Exception {
        return ResponseEntity.ok(importService.importPatientsFromCsv(file));
    }

    @GetMapping("/{id}/medical-records")
    public ResponseEntity<List<MedicalRecord>> getMedicalRecords(@PathVariable Long id) {
        return ResponseEntity.ok(patientService.getMedicalRecords(id));
    }

    @GetMapping("/appointments/available")
    public ResponseEntity<List<LocalDateTime>> getAvailableSlots() {
        return ResponseEntity.ok(patientService.getAvailableSlots());
    }

    @GetMapping("/{id}/appointments")
    public ResponseEntity<List<Appointment>> getScheduledAppointments(@PathVariable Long id) {
        return ResponseEntity.ok(patientService.getScheduledAppointments(id));
    }

    @GetMapping("/{id}/appointments/past")
    public ResponseEntity<List<Appointment>> getPastAppointments(@PathVariable Long id) {
        return ResponseEntity.ok(patientService.getPastAppointments(id));
    }

    @PostMapping("/{id}/appointments")
    public ResponseEntity<Appointment> scheduleAppointment(
            @PathVariable Long id,
            @RequestParam Long doctorId,
            @RequestParam LocalDateTime dateTime) {
        return ResponseEntity.ok(patientService.scheduleAppointment(id, doctorId, dateTime));
    }

    @PutMapping("/appointments/{appointmentId}/reschedule")
    public ResponseEntity<Appointment> rescheduleAppointment(
            @PathVariable Long appointmentId,
            @RequestParam Long patientId,
            @RequestParam LocalDateTime newDateTime) {
        return ResponseEntity.ok(patientService.rescheduleAppointment(appointmentId, patientId, newDateTime));
    }

    @DeleteMapping("/appointments/{appointmentId}")
    public ResponseEntity<Void> cancelAppointment(
            @PathVariable Long appointmentId,
            @RequestParam Long patientId) {
        patientService.cancelAppointment(appointmentId, patientId);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{id}/personal-info")
    public ResponseEntity<Patient> updatePersonalInfo(
            @PathVariable Long id,
            @RequestParam String email,
            @RequestParam String contactNumber) {
        return ResponseEntity.ok(patientService.updatePersonalInfo(id, email, contactNumber));
    }
}