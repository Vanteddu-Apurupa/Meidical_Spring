package com.example.demo;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface AppointmentInterface extends JpaRepository<Appointment, Long> {
    
    // Custom method to find appointments by patient email
    List<Appointment> findByPatientEmail(String patientEmail);

    // Custom method to find appointments by doctor email
    List<Appointment> findByDoctorEmail(String doctorEmail);

	Appointment findByPatientEmailAndDoctorEmail(String patientEmail, String doctorEmail);
}
