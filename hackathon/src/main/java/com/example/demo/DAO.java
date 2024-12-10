package com.example.demo;

import java.util.List;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class DAO {

	private static final String UPLOAD_DIR = "uploads/";

	@Autowired
    UserInterface repo;

    @Autowired
    DoctorInterface repo1;
    
    @Autowired
    AppointmentInterface appointmentRepo;

    @Autowired
    PrescriptionInterface prescriptionRepo ;
    public void insert(User u1) {
        repo.save(u1);
    }

    public void insert1(Doctor doc) {
        repo1.save(doc);
    }

    public void insertAppointment(Appointment appointment) {
        appointmentRepo.save(appointment);
    }
    public List<Appointment> findAppointmentsByDoctorEmail(String doctorEmail) {
        return appointmentRepo.findByDoctorEmail(doctorEmail);
    }
    
    
    
  
    public List<Appointment> findAppointmentsByPatientEmail(String email) {
        return appointmentRepo.findByPatientEmail(email);
    }

    public User findUserByNameAndPassword(String name, String password) {
        return repo.findByNameAndPassword(name, password);
    }

    public Doctor findDoctorByNameAndPassword(String name, String password) {
        return repo1.findByNameAndPassword(name, password);
    }

    public List<Doctor> find() {
        return repo1.findAll();
    }

    public Doctor findDoctor(String email) {
        return repo1.findByEmail(email);
    }

    public String deleteDoctor(String email) {
        repo1.delete(repo1.findByEmail(email));
        return "deleted " + email;
    }

    public User findUser(String email) {
        return repo.findByEmail(email);
    }

    public String deleteUser(String email) {
        repo.delete(repo.findByEmail(email));
        return "deleted " + email;
    }

    public List<User> findUser() {
        return repo.findAll();
    }
    
    
    
    public String savePrescription(MultipartFile file, String patientEmail, String doctorEmail) throws IOException {
        Appointment appointment = (appointmentRepo.findByPatientEmailAndDoctorEmail(patientEmail, doctorEmail));

        // Generate a unique file name
        String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
        Path destinationPath = Paths.get(UPLOAD_DIR + fileName);

        // Ensure the directory exists
        Files.createDirectories(destinationPath.getParent());

        // Save the file
        Files.copy(file.getInputStream(), destinationPath);

        // Save prescription in the database
        Prescription prescription = new Prescription(destinationPath.toString(), appointment);
        prescriptionRepo.save(prescription);

        return "Prescription uploaded successfully!";
    }

    // Method to find a prescription by patient and doctor email
    public Prescription findPrescriptionByPatientAndDoctor(String patientEmail, String doctorEmail) {
        return prescriptionRepo.findByPatientAndDoctor(patientEmail, doctorEmail);
    }
}
