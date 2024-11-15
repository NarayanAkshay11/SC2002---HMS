package com.hospital.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;
import com.hospital.enums.Gender;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Doctor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long DocID;

    private String name;
    private LocalDate dateOfBirth;
    private Gender gender;
    private String contactNumber;
    private String email;
    private String bloodType;

    @OneToMany(mappedBy = "DocID", cascade = CascadeType.ALL)
    private List<Patient> patient;

    @OneToMany(mappedBy = "patient", cascade = CascadeType.ALL)
    private List<MedicalRecord> medicalRecords;

    @OneToMany(mappedBy = "patient", cascade = CascadeType.ALL)
    private List<Appointment> appointments;
}