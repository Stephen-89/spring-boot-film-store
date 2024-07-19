package com.stephen.login.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.stephen.login.dto.UserDto;
import com.stephen.login.entity.Customer;
import com.stephen.login.exceptions.UserExistsException;
import com.stephen.login.repository.CustomerRepository;

@Service
public class CustomerServiceImpl implements CustomerService {
	
	@Autowired
	private PasswordEncoder bcryptEncoder;
	
	@Autowired
	private CustomerRepository customerRepository;
	
	@Override
	public Customer createCustomer(UserDto user) {
		if (customerRepository.existsByEmail(user.getUsername())) {
			throw new UserExistsException("User is already registered with username: " + user.getUsername());
		}
		Customer newUser = new Customer();
		BeanUtils.copyProperties(user, newUser);
		newUser.setPassword(bcryptEncoder.encode(newUser.getPassword()));
		newUser.setActive(Boolean.TRUE);
		newUser.setMfaEnabled(Boolean.FALSE);
		//newUser.setRoles(Arrays.asList(new Role(3l, "ROLE_USER")));
		return customerRepository.save(newUser);
	}

	@Override
	public Customer updateCustomer(UserDto user) {
		Customer existingUser = readCustomer();
		existingUser.setFirstName(user.getFirstName() != null ? user.getFirstName() : existingUser.getFirstName());
		existingUser.setLastName(user.getSecondName() != null ? user.getSecondName() : existingUser.getLastName());
		existingUser.setEmail(user.getUsername() != null ? user.getUsername() : existingUser.getEmail());
		existingUser.setPassword(user.getPassword() != null ? bcryptEncoder.encode(user.getPassword()) : existingUser.getPassword());
		return customerRepository.save(existingUser);
	}

	@Override
	public void deleteCustomer() {
		Customer existingUser = readCustomer();
		customerRepository.delete(existingUser);
	}

	@Override
	public Customer readCustomer() {
		Long userId = getLoggedInCustomer().getCustomerId();
		return customerRepository.findById(userId).orElseThrow(() -> new UsernameNotFoundException("User not found for the id: " +userId));
	}

	@Override
	public Customer getLoggedInCustomer() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String username = authentication.getName();
		return customerRepository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException("User not found for the username: " + username));
	}

}

























