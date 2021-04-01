package com.decathlon.alert.exception;

public class TeamAlreadyExistsException extends RuntimeException {

	private String message;

    public TeamAlreadyExistsException(String name) {
			super(String.format("Team with name %s already exists", name));
			this.message = String.format("Team with name %s already exists", name);
		}


	public String getMessage() {
		return message;
	}
}
