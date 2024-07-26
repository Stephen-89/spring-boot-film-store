package com.stephen.store.service;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.stephen.store.entity.Rental;
import com.stephen.store.repository.RentalRepository;

@Service
public class RentalServiceImpl implements RentalService {
	
	@Autowired
	private RentalRepository rentalRepository;

	@Override
	public Set<Rental> getRentals() {
		return new HashSet<>(rentalRepository.findAll());
	}

}

























