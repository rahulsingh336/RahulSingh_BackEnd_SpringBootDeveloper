package com.decathlon.alert.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class SMSServiceResponse implements Serializable {

	private static final long serialVersionUID = 7752352338881048655L;

	private String success;
}
