package com.stephen.store.service;

import com.stephen.store.dto.UserDto;
import com.stephen.store.entity.Customer;

public interface CustomerService {
	
	Customer createCustomer(UserDto userDto);
	
	Customer readCustomer();
	
	Customer updateCustomer(UserDto userDto);
	
	void deleteCustomer();
	
	Customer getLoggedInCustomer();
	
}
