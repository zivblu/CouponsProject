package com.example.demo.exceptions;

@SuppressWarnings("serial")
public class CouponExistException extends RuntimeException {
	
	public CouponExistException (String message) {
			super(message);
	}
}
