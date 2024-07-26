package com.stephen.store.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.stephen.store.dto.UserDto;
import com.stephen.store.entity.Customer;
import com.stephen.store.service.CustomerService;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class CustomerController {

	@Autowired
	private CustomerService customerService;

	@PostMapping("/register")
	public ResponseEntity<Customer> save(@Valid @RequestBody UserDto userDto) {
		return new ResponseEntity<>(customerService.createCustomer(userDto), HttpStatus.CREATED);
	}

	@GetMapping("/profile")
	public ResponseEntity<Customer> readCustomer() {
		return new ResponseEntity<>(customerService.readCustomer(), HttpStatus.OK);
	}

	@PutMapping("/profile")
	public ResponseEntity<Customer> updateCustomer(@RequestBody UserDto userDto) {
		return new ResponseEntity<>(customerService.updateCustomer(userDto), HttpStatus.OK);
	}

	@DeleteMapping("/deactivate")
	public ResponseEntity<HttpStatus> deleteCustomer() {
		customerService.deleteCustomer();
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

}
