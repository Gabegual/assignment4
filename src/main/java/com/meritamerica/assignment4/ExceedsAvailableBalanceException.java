package com.meritamerica.assignment4;

public class ExceedsAvailableBalanceException extends Exception {
	
	ExceedsAvailableBalanceException(String ERROR){
		super(ERROR);
	}
}
