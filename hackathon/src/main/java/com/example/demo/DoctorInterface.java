package com.example.demo;

import org.springframework.data.jpa.repository.JpaRepository;

public interface DoctorInterface  extends JpaRepository<Doctor, String> {
	  Doctor findByNameAndPassword(String name, String password);
		public Doctor findByEmail(String email);


}
