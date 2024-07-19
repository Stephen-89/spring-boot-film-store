package com.stephen.login.controller;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.stephen.login.entity.Film;
import com.stephen.login.repository.InventoryRepository;
import com.stephen.login.service.CustomerService;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class FilmController {

	private InventoryRepository inventoryRepository;
	private CustomerService customerService;

	public FilmController(InventoryRepository inventoryRepository, CustomerService customerService) {
		this.inventoryRepository = inventoryRepository;
		this.customerService = customerService;
	}

	@GetMapping("/films")
	public ResponseEntity<List<Film>> getFilms() {
		List<Film> films = inventoryRepository
				.findByStoreId(customerService.getLoggedInCustomer().getStore());
		return new ResponseEntity<>(films, HttpStatus.OK);
	}

}
