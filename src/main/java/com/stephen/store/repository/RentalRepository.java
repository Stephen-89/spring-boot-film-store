package com.stephen.store.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.stephen.store.entity.Rental;

@Repository
public interface RentalRepository extends JpaRepository<Rental, Long>{
	
}
