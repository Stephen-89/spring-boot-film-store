package com.stephen.login.service;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.stephen.login.entity.Rental;
import com.stephen.login.repository.RentalRepository;

@Service
public class RentalServiceImpl implements RentalService {
	
	@Autowired
	private RentalRepository rentalRepository;

	@Override
	public Set<Rental> getRentals() {
		return new HashSet<>(rentalRepository.findAll());
	}

}

























