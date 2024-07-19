package com.stephen.login.service;

import org.springframework.http.ResponseEntity;

import com.stephen.login.dto.AuthModel;
import com.stephen.login.entity.Customer;

public interface AuthService {

	ResponseEntity<Customer> loginCustomer(AuthModel authModel) throws Exception;

}
