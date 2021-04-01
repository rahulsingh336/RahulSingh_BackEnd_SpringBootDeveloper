package com.decathlon.alert.exception;

public class NoDataFoundException extends RuntimeException {

    public NoDataFoundException(String id) {

			super(String.format("Team with Id %s not found", id));
		}
}
