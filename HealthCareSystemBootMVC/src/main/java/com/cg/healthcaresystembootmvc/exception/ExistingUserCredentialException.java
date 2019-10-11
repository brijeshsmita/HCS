package com.cg.healthcaresystembootmvc.exception;
/*
 * Author: 		Jayesh Gaur
 * Description: This exception is thrown when user enters a value like email	
 * 					or phone number which already exists in the database and it cannot
 * 					be unique
 * Created on:	 October 11, 2019
 */
public class ExistingUserCredentialException extends Exception {

	private static final long serialVersionUID = 1L;

	//Default constructor
	public ExistingUserCredentialException() {
	}
	
	public ExistingUserCredentialException(String exception) {
		super(exception);
	}
	
}
