// PrescriptionRepository.java
package com.example.demo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PrescriptionInterface extends JpaRepository<Prescription, Long> {

    @Query("SELECT p FROM Prescription p WHERE p.appointment.patientEmail = :patientEmail AND p.appointment.doctorEmail = :doctorEmail")
    Prescription findByPatientAndDoctor(@Param("patientEmail") String patientEmail, @Param("doctorEmail") String doctorEmail);
}
