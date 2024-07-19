package com.stephen.login.service;

import com.stephen.login.dto.UserDto;
import com.stephen.login.entity.Customer;

public interface CustomerService {
	
	Customer createCustomer(UserDto userDto);
	
	Customer readCustomer();
	
	Customer updateCustomer(UserDto userDto);
	
	void deleteCustomer();
	
	Customer getLoggedInCustomer();
	
}
