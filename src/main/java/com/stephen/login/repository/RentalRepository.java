package com.stephen.login.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.stephen.login.entity.Rental;

@Repository
public interface RentalRepository extends JpaRepository<Rental, Long>{
	
}
