package com.stephen.store.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.stephen.store.dto.AuthModel;
import com.stephen.store.entity.Customer;
import com.stephen.store.exceptions.TotpRequiredException;
import com.stephen.store.repository.CustomerRepository;
import com.stephen.store.util.JwtTokenUtil;

@Service
public class AuthServiceImpl implements AuthService {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private UserRolesDetailsService userRolesDetailsService;

	@Autowired
	private CustomerRepository customerRepository;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Override
	public ResponseEntity<Customer> loginCustomer(AuthModel authModel) throws Exception {

		authenticate(authModel.getUsername(), authModel.getPassword());

		final UserDetails userDetails = userRolesDetailsService.loadUserByUsername(authModel.getUsername());

		Optional<Customer> user = customerRepository.findByEmail(userDetails.getUsername());

		if (Boolean.TRUE.equals(user.get().getMfaEnabled())) {
			throw new TotpRequiredException("Multi-Factor Authentication Required");
		} else {

			final String token = jwtTokenUtil.generateToken(userDetails);
			user.get().setAccessToken(token);
			return new ResponseEntity<>(user.get(), HttpStatus.OK);

		}

	}

	private void authenticate(String username, String password) throws Exception {
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
		} catch (DisabledException e) {
			throw new Exception("User disabled");
		} catch (BadCredentialsException e) {
			throw new Exception("Bad Credentials");
		}
	}

}
