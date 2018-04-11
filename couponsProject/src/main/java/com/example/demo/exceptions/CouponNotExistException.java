package com.example.demo.exceptions;

@SuppressWarnings("serial")
public class CouponNotExistException extends RuntimeException {

	public CouponNotExistException (String message) {
		super(message);
	}
}
