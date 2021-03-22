//public abstract class Transaction
//public BankAccount getSourceAccount()
//public void setSourceAccount(BankAccount sourceAccount)
//public BankAccount getTargetAccount()
//public void setTargetAccount(BankAccount targetAccount)
//public double getAmount()
//public void setAmount(double amount)
//public java.util.Date getTransactionDate()
//public void setTransactionDate(java.util.Date date)
//public String writeToString()
//public static Transaction readFromString(String transactionDataString)
//public abstract void process() throws NegativeAmountException, ExceedsAvailableBalanceException, ExceedsFraudSuspicionLimitException
//public boolean isProcessedByFraudTeam()
//public void setProcessedByFraudTeam(boolean isProcessed)
//public String getRejectionReason()
//public void setRejectionReason(String reason)
package com.meritamerica.assignment4;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.text.DateFormatter;

public abstract class Transaction {
	
	BankAccount account;
	BankAccount targetAccount;
	BankAccount sourceAccount;
	double amount;
	Date date;
	boolean isProcessed = false;
	String rejectionReason;
	
	//--------------SourceAccount----------\\
	public BankAccount getSourceAccount() {
		return sourceAccount;
		
	}
	
	public void setSourceAccount(BankAccount sourceAccount) {
		this.sourceAccount = sourceAccount;
	}
	//--------------TargetAccount----------\\
	public BankAccount getTargetAccount() {
		return targetAccount;
		
	}
	
	public void setTargetAccount(BankAccount targetAccount) {
		this.targetAccount = targetAccount;
	}
	//--------------AMOUNT----------\\
	public double getAmount() {
		return this.amount;
		
	}
	
	public void setAmount(double amount) {
		this.amount = amount;
	}
	//--------------DATE----------\\
	public java.util.Date getTransactionDate(){
		return date;
	}
	
	public void setTransactionDate(Date date) {
		this.date = date;
	}
	//--------------WRITE TO STRING METHOD----------\\
//	public String writeToString() {
//		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
//		StringBuilder toString = new StringBuilder();
//		
//		if(account == null) {
//			toString.append(-1);
//		}else {
//			toString.append(account.getAccountNumber());
//		}
//		toString.append(",");
//		toString.append(targetAccount.getAccountNumber());
//		toString.append(",");
//		toString.append(amount);
//		toString.append(",");
//		toString.append(dateFormat.format(date));
//		return toString.toString();
//	}	
	
	public String writeToString() {
		System.out.println("TRANSACTION TESTING - WRITE TO STRING - target, source, amount, date");
		
		return "TARGET ACCOUNT MUNBER : " + this.targetAccount + "," + "SOURCE ACCOUNT NUMBER: " + this.sourceAccount + "," + "AMOUNT: " + this.amount + "DATE: " + this.date;
	}
	//--------------READ FROM STRING METHOD----------\\
	public static Transaction readFromString(String transactionDate) {
		String[] trans = transactionDate.split(",");
		SimpleDateFormat dateFormater = new SimpleDateFormat("dd/MM/yyyy");
		
		try {
			long targetAccount = Long.parseLong(trans[0]);
			long sourceAccount = Long.parseLong(trans[1]);
			double amount = Double.parseDouble(trans[2]);
			Date date = dateFormater.parse(trans[3]);
			
			if(targetAccount == -1) {
				if(amount < 0) {
					WithdrawTransaction newTransaction = new WithdrawTransaction(MeritBank.getBankAccount(sourceAccount), amount);
					return newTransaction;
				}
				DepositTransaction newTRansaction = new DepositTransaction(MeritBank.getBankAccount(sourceAccount), amount);
				return newTRansaction;
			}
			TransferTransaction newTrans = new TransferTransaction(MeritBank.getBankAccount(sourceAccount), MeritBank.getBankAccount(targetAccount), amount);
			return newTrans;
		}
		catch(ParseException e) {
			return null;
		}
	}
	

	
	public abstract void process() throws NegativeAmountException, ExceedsAvailableBalanceException, ExceedsFraudSuspicionLimitException;
	
	
	
	public boolean isProcessedByFraudTeam() {
		return isProcessed;
	}
	
	public void setProcessedByFraudTeam(boolean isProcessed) {
		this.isProcessed = isProcessed;
		
	}
	
	public String getRejectionReason() {
		return rejectionReason;
	}
	
	public void setRejectionReason(String rejectionReason) {
		this.rejectionReason = rejectionReason;
	}
	
	

}
