package com.hospital.services;

import com.hospital.model.Patient;
import com.hospital.repository.PatientRepository;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
public class PatientDataImportService {

    @Autowired
    private PatientRepository patientRepository;

    public List<Patient> importPatientsFromCsv(MultipartFile file) throws Exception {
        List<Patient> patients = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            CSVParser csvParser = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(reader);

            for (CSVRecord record : csvParser) {
                Patient patient = new Patient();
                patient.setName(record.get("name"));
                patient.setDateOfBirth(LocalDate.parse(record.get("dateOfBirth")));
                patient.setGender(Gender.valueOf(record.get("gender")));
                patient.setContactNumber(record.get("contactNumber"));
                patient.setEmail(record.get("email"));
                patient.setBloodType(record.get("bloodType"));

                patients.add(patient);
            }
        }

        return patientRepository.saveAll(patients);
    }
}
