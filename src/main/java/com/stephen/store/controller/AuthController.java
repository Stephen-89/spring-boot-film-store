package com.stephen.store.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.stephen.store.dto.AuthModel;
import com.stephen.store.dto.ImageDto;
import com.stephen.store.dto.MfaDto;
import com.stephen.store.entity.Customer;
import com.stephen.store.service.AuthService;
import com.stephen.store.service.TotpService;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class AuthController {
	
	@Autowired
	private AuthService authService;
	
	@Autowired
	private TotpService totpService;
	
	@PostMapping("/login")
	public ResponseEntity<Customer> login(@RequestBody AuthModel authModel) throws Exception {
		return authService.loginCustomer(authModel);
	}

	@GetMapping("/generate-qr-image")
	public ResponseEntity<ImageDto> generateUriForImage() {
		return new ResponseEntity<ImageDto>(new ImageDto(totpService.generateUriForImage()), HttpStatus.CREATED);
	}

	@PostMapping("/disable-mfa")
	public void disableMfa() {
		totpService.disableMfa();
	}

	@PostMapping("/verfiy-code")
	public ResponseEntity<Object> verifyCode(@Valid @RequestBody MfaDto mfaDto) {
		return new ResponseEntity<>(totpService.verifyCode(mfaDto), HttpStatus.OK);
	}
	
}


















