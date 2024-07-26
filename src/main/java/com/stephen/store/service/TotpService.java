package com.stephen.store.service;

import com.stephen.store.dto.MfaDto;

public interface TotpService {
	
	String generateUriForImage();
	
	Object verifyCode(MfaDto mfaDto);

	void disableMfa();
	
}
