package com.example.demo.exceptions;

@SuppressWarnings("serial")
public class CustomerNotExistException extends RuntimeException {

	public CustomerNotExistException (String message) {
		super(message);
	}
	
}
