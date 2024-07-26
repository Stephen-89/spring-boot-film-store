package com.stephen.store.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.stephen.store.entity.Customer;
import com.stephen.store.entity.Role;
import com.stephen.store.repository.CustomerRepository;

@Service
public class UserRolesDetailsService implements UserDetailsService {

	@Autowired
	private CustomerRepository customerRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<Customer> user = customerRepository.findByEmail(username);
		if (!user.isPresent()) {
			throw new UsernameNotFoundException("User not found"); 
		} else {
			return new org.springframework.security.core.userdetails.User(user.get().getEmail(),
					user.get().getPassword(), user.get().getActive(), true, true, true,
					getAuthorities(new ArrayList<>()));
		}
	}

    private Collection<? extends GrantedAuthority> getAuthorities(List<Role> roles) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        for (Role role : roles) {
            authorities.add(new SimpleGrantedAuthority(role.getName()));
        }
        return authorities;
    }
    
}