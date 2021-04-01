package com.decathlon.alert.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class SMSServiceRequest implements Serializable {

	private static final long serialVersionUID = 7250347184124490408L;

	@JsonProperty("phone_number")
	private String phoneNumber;
}
