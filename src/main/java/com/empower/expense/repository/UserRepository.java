package com.empower.expense.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.empower.expense.model.Users;



@Repository
public interface UserRepository extends JpaRepository<Users, Long> {

    // Custom query method to find a user by email
    Optional<Users> findByEmailId(String emailId);

	Users findByName(String name);

}
