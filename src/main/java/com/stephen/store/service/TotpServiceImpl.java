package com.stephen.store.service;

import org.apache.commons.codec.binary.Base32;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.stephen.store.dto.MfaDto;
import com.stephen.store.entity.Customer;
import com.stephen.store.exceptions.QrCodeException;
import com.stephen.store.repository.CustomerRepository;
import com.stephen.store.util.JwtTokenUtil;
import com.stephen.store.util.StringUtil;

import dev.samstevens.totp.code.HashingAlgorithm;
import dev.samstevens.totp.exceptions.QrGenerationException;
import dev.samstevens.totp.qr.QrData;
import dev.samstevens.totp.qr.QrGenerator;
import dev.samstevens.totp.qr.ZxingPngQrGenerator;
import dev.samstevens.totp.time.*;
import dev.samstevens.totp.code.*;
import static dev.samstevens.totp.util.Utils.getDataUriForImage;

import java.util.Optional;

@Service
public class TotpServiceImpl implements TotpService {
	
	@Autowired
	private CustomerService customerService;
	@Autowired
	private CustomerRepository customerRepository;
	@Autowired
	private JwtTokenUtil jwtTokenUtil;	
	@Autowired
	private UserRolesDetailsService userRolesDetailsService;

	public String generateUriForImage() {
		
		String secret = generateSecret();
		
		Customer user = customerService.getLoggedInCustomer();
		
        QrData data = new QrData.Builder()
                .label("My MFA Test")
                .secret(StringUtils.isNoneEmpty(user.getTotp()) ? user.getTotp() : secret)
                .issuer(user.getEmail())
                .algorithm(HashingAlgorithm.SHA1)
                .digits(6)
                .period(30)
                .build();

        QrGenerator generator = new ZxingPngQrGenerator();
        byte[] imageData = new byte[0];

        try {
            imageData = generator.generate(data);
            if(!user.getMfaEnabled()) {
            	user.setMfaEnabled(Boolean.TRUE);
            	user.setTotp(secret);
            	customerRepository.save(user);
            }
        } catch (QrGenerationException e) {
           throw new QrCodeException("Unable to generate QrCode");
        }

        String mimeType = generator.getImageMimeType();

        return getDataUriForImage(imageData, mimeType);
        
    }
	
	private String generateSecret() {
		Base32 base32 = new Base32();
		return base32.encodeAsString(StringUtil.getSaltString().getBytes());
	}
	
	public Object verifyCode(MfaDto mfaDto) {
		Optional<Customer> user = customerRepository.findByEmail(mfaDto.getUsername());
		if(user.isPresent()) {
			TimeProvider timeProvider = new SystemTimeProvider();
	        CodeGenerator codeGenerator = new DefaultCodeGenerator();
	        CodeVerifier verifier = new DefaultCodeVerifier(codeGenerator, timeProvider);
	        boolean result = verifier.isValidCode(user.get().getTotp(), mfaDto.getCode());
	        if(result) {
	        	final UserDetails userDetails = userRolesDetailsService.loadUserByUsername(mfaDto.getUsername());
	        	final String token = jwtTokenUtil.generateToken(userDetails);
	        	user.get().setAccessToken(token);
	        	return user.get();
	        }
	        return result;	
		} else {
			throw new UsernameNotFoundException("User not found for the username: " + mfaDto.getUsername());
		}
    }

	public void disableMfa() {
		Customer user = customerService.getLoggedInCustomer();
    	user.setMfaEnabled(Boolean.FALSE);
    	user.setTotp(null);
    	customerRepository.save(user);
    }
	
}
