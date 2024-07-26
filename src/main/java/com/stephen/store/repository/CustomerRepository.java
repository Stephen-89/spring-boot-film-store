package com.stephen.store.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.stephen.store.entity.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long>{
	
	Boolean existsByEmail(String email);
	
	Optional<Customer> findByEmail(String email);
	
	Boolean existsByEmailAndActive(String email, Boolean active);
	
}