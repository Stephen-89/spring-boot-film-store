package com.stephen.store.service;

import org.springframework.http.ResponseEntity;

import com.stephen.store.dto.AuthModel;
import com.stephen.store.entity.Customer;

public interface AuthService {

	ResponseEntity<Customer> loginCustomer(AuthModel authModel) throws Exception;

}
