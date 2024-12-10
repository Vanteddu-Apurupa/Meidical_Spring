package com.example.demo;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserInterface extends JpaRepository<User, String> {
	  User findByNameAndPassword(String name, String password) ;
		public User findByEmail(String email);

}
