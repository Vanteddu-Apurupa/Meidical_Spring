package com.example.demo;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;
@RestController
@CrossOrigin
public class service {
	private static final String UPLOAD_DIR = "uploads/";
    @Autowired
    DAO dao;

    @PostMapping("/register")
    public String registerUser(@RequestBody User user) {
        dao.insert(user);
        return "done";
    }

    @PostMapping("/check")
    public String checkUser(@RequestBody User user) {
        User existingUser = dao.findUserByNameAndPassword(user.getName(), user.getPassword());

        if (existingUser != null) {
            String username = existingUser.getName();
            if (username.equalsIgnoreCase("admin")) {
                return "ADMIN";
            } else if (username.equalsIgnoreCase("pharmacist")) {
                return "PHARMACIST";
            } else {
                return "SUCCESS";
            }
        } else {
            return "User not found";
        }
    }

    @PostMapping("/registerdoc")
    public String registerDoctor(@RequestBody Doctor doc) {
        dao.insert1(doc);
        return "Doctor registered successfully";
    }

    @PostMapping("/check1")
    public String checkDoctor(@RequestBody Doctor doctor) {
        Doctor existingDoctor = dao.findDoctorByNameAndPassword(doctor.getName(), doctor.getPassword());

        if (existingDoctor != null) {
            return "Success";
        } else {
            return "Doctor not found";
        }
    }

    @GetMapping("/doctors")
    public List<Doctor> getDoctors() {
        return dao.find();
    }

    @GetMapping("/users")
    public List<User> getUsers() {
        return dao.findUser();
    }

    @DeleteMapping("/deleteDoctor")
    public String deleteDoctor(@RequestParam("email") String email) {
        return dao.deleteDoctor(email);
    }

    @DeleteMapping("/deleteUser")
    public String deleteUser(@RequestParam("email") String email) {
        return dao.deleteUser(email);
    }

    // New endpoint to create an appointment
    @PostMapping("/appointments")
    public String createAppointment(@RequestBody Appointment appointment) {
        // Validate that patientEmail is not null or empty
        if (appointment.getPatientEmail() == null || appointment.getPatientEmail().isEmpty()) {
            return "Patient email is required";  // Respond with an error message
        }

        // Insert appointment if patientEmail is valid
        dao.insertAppointment(appointment);
        return "Appointment created successfully";
    }


    // New endpoint to retrieve appointments by patient email
    @GetMapping("/appointments")
    public List<Appointment> getAppointmentsByPatientEmail(@RequestParam("email") String email) {
        return dao.findAppointmentsByPatientEmail(email);
    }
    @GetMapping("/appointments1")
    public List<Appointment> getAppointmentsByDoctorEmail(@RequestParam("doctorEmail") String doctorEmail) {
    	System.out.println(doctorEmail);
        return dao.findAppointmentsByDoctorEmail(doctorEmail);
    }
    
    
    @PostMapping("/uploadPrescription")
    public String uploadPrescription(
            @RequestParam("file") MultipartFile file,
            @RequestParam("patientEmail") String patientEmail,
            @RequestParam("doctorEmail") String doctorEmail) {
        try {
            return dao.savePrescription(file, patientEmail, doctorEmail);
        } catch (Exception e) {
            return "Failed to upload prescription: " + e.getMessage();
        }
    }

    // Prescription retrieval endpoint
    @GetMapping("/viewPrescription")
    public ResponseEntity<?> getPrescription(
            @RequestParam("patientEmail") String patientEmail,
            @RequestParam("doctorEmail") String doctorEmail) {
        Prescription prescription = dao.findPrescriptionByPatientAndDoctor(patientEmail, doctorEmail);
        
        if (prescription == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Prescription not found");
        }
        return ResponseEntity.ok(prescription); // Return prescription as JSON
    }

}
