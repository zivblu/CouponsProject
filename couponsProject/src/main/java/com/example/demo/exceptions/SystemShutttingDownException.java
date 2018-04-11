package com.example.demo.exceptions;

@SuppressWarnings("serial")
public class SystemShutttingDownException extends RuntimeException {
	
	public SystemShutttingDownException (String message) {
			super(message);
	}
}
