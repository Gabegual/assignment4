package com.meritamerica.assignment4;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class CDAccount extends BankAccount {
	CDOffering offering;
	private Date date;
	private double balance;
	private long accountNumber;
	private int term=0;
	
		
	public CDAccount(CDOffering offering, double openBalance) {
		super(openBalance,offering.getInterestRate());
		this.offering = offering;
		this.term = offering.getTerm();
	}
	
	public CDAccount(long accountNumber, double balance, double interestRate, Date accountOpenedOn, int term) {
		super(accountNumber, balance,interestRate,accountOpenedOn);
		this.term = term;
	}
	
	public int getTerm() {
		return this.term;
	}
	
	public Date getStartDate(){
		return date;
	}
	
	public double futureValue() {
		return super.futureValue(term);
	}
	
	public double getBalance() {
		return super.getBalance();
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}
	
	
	public boolean withdraw(double amount) {
		Date setDate = new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(getOpenedOn());
		cal.add(Calendar.YEAR,getTerm());
		Date nextYear = cal.getTime();
		if(nextYear.after(setDate)) {
			return false;
		}else {
			if(amount < 0) {
				return false;
			}
			double newBalance = this.getBalance() + amount;
			this.setBalance(newBalance);
			return true;
		}
        
    }
    
  

	public boolean deposit(double amount) {
    	Date setDate = new Date();
    	Calendar cal = Calendar.getInstance();
    	cal.setTime(getOpenedOn());
    	cal.add(Calendar.YEAR, getTerm());
    	Date nextYear = cal.getTime();
    	if(nextYear.after(setDate)) {
    		return false;
    	} else {
    		if(amount < 0) {
    			System.out.println("WARNING ! DEPOSIT AMOUNT CAN NOT BE NEGATIVE");
    			return false;
    		}else if (amount > 1000) {
    			FraudQueue farQ = new FraudQueue();
    			farQ.addTransaction(new DepositTransaction(MeritBank.getBankAccount(getAccountNumber()), amount));
    			return false;
    		}
    		double newBalance = this.getBalance() + amount;
    		this.setBalance(newBalance);
    		return true;
    	}
    }
    
    public static CDAccount readFromString(String accountData)throws ParseException, NumberFormatException {
    	String [] holding = accountData.split(",");
    	SimpleDateFormat date = new SimpleDateFormat("dd/MM/yyyy");
    	long accountNumber = Long.parseLong(holding[0]);
    	double balance = Double.parseDouble(holding[1]);
    	double interestRate = Double.parseDouble(holding[2]);
    	Date accountOpenedOn = date.parse(holding[3]);
    	int term = Integer.parseInt(holding[4]);
    	CDAccount newCDAccount = new CDAccount(accountNumber,balance,interestRate,accountOpenedOn,term);
    	return newCDAccount;
    }
    
    public String writeToString() {
    	StringBuilder override = new StringBuilder();
    	override.append(writeToString()).append(",");
    	override.append(term);
    	return override.toString();
    }


}