package com.decathlon.alert.service;

import com.decathlon.alert.dto.SMSServiceRequest;
import com.decathlon.alert.dto.SMSServiceResponse;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

@Service
public class InvokeSMSService extends BaseRestClient {

	private static final long serialVersionUID = -5328400409677722360L;

	public SMSServiceResponse sendSMS(String phoneNumber) {
		SMSServiceRequest smsServiceRequest = new SMSServiceRequest();
		smsServiceRequest.setPhoneNumber(phoneNumber);
        return this.invokeService(new ParameterizedTypeReference<SMSServiceResponse>(){}, smsServiceRequest, HttpMethod.POST);
	}
}
