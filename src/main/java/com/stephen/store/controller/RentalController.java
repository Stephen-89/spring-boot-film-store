package com.stephen.store.controller;

import java.util.Set;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.stephen.store.entity.Rental;
import com.stephen.store.service.RentalService;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class RentalController {

	private RentalService rentalService;

	public RentalController(RentalService rentalService) {
		this.rentalService = rentalService;
	}

	@GetMapping("/rentals")
	public ResponseEntity<Set<Rental>> getRentals() {
		return new ResponseEntity<>(rentalService.getRentals(), HttpStatus.OK);
	}

}
