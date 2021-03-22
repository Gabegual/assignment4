package com.meritamerica.assignment4;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;
import java.io.BufferedReader;

class MeritBank {

	private static long nextAccountNumber = 0;
	private static int accountHolderNumb = 1;
	private static AccountHolder AccountHoldersArray[] = new AccountHolder[0];
	private static CDOffering CDOfferingsArray[] = new CDOffering[0];
	private static double totalvalue = 0;
	protected static long accountNumber;
	protected static FraudQueue fraudQueue = new FraudQueue();
	
	public static void addAccountHolder(AccountHolder accountHolder) {
		AccountHolder[] newAccountHolderArray = new AccountHolder[AccountHoldersArray.length + 1];
		for (int i = 0; i < AccountHoldersArray.length; i++) {
			newAccountHolderArray[i] = AccountHoldersArray[i];
		}
		newAccountHolderArray[accountHolderNumb] = accountHolder;
		AccountHoldersArray = newAccountHolderArray;
		accountHolderNumb++;
	}

	public static AccountHolder[] getAccountHolders() {
		return AccountHoldersArray;
	}

	public static CDOffering[] getCDOfferings() {
		return CDOfferingsArray;
	}

	public static CDOffering getBestCDOffering(double depositAmount) {
		double best = 0.0;
		CDOffering bestOffering = null;
		if (CDOfferingsArray == null) {
			return null;
		}
		for (CDOffering offering : CDOfferingsArray) {
			if (futureValue(depositAmount, offering.getInterestRate(), offering.getTerm()) > best) {
				bestOffering = offering;
				best = futureValue(depositAmount, offering.getInterestRate(), offering.getTerm());
			}
		}
		return bestOffering;
	}

	public static CDOffering getSecondBestCDOffering(double depositAmount) {
		if (CDOfferingsArray == null) {
			return null;
		}
		CDOffering bestOffering = null;
		double best = 0.0;
		CDOffering secondBestOffering = null;

		for (CDOffering offering : CDOfferingsArray) {
			if (futureValue(depositAmount, offering.getInterestRate(), offering.getTerm()) > best) {
				secondBestOffering = bestOffering;
				bestOffering = offering;
				best = futureValue(depositAmount, offering.getInterestRate(), offering.getTerm());
			}
		}
		return secondBestOffering;
	}

	public static void clearCDOfferings() {
		CDOfferingsArray = null;
	}

	public static void setCDOfferings(CDOffering[] offerings) {
		CDOfferingsArray = offerings;
	}

	public static long getNextAccountNumber() {
		return nextAccountNumber;
	}

	public static double totalBalances() {
		double total = 0.0;
		for (AccountHolder accounts : AccountHoldersArray) {
			total += accounts.getCombinedBalance();
		}
		System.out.println("Total aggregate account balance: $" + total);
		return total;

	}

	public static double futureValue(double presentValue, double interestRate, int term) {
	return recursiveFutureValue(presentValue, term, interestRate);
	}
	
	public static double recursiveFutureValue(double amount, int years, double interestRate) {
		if(years > 0) {
			double newAmount = amount + (amount * interestRate);
			return recursiveFutureValue(newAmount, years - 1, interestRate);
		}
		return amount;
	}

	static boolean readFromFile(String fileName) {
		CDOffering cdofering[] = new CDOffering[0];
		AccountHolder[] accountHolders = new AccountHolder[0];
		setNextAccountNumber((long)0);
		FraudQueue fraudQueue = new FraudQueue();
		Set<String> transactions = new HashSet<String>();
		try {
			FileReader reader = new FileReader(fileName);
			BufferedReader bufferedReader = new BufferedReader(reader);
			Long nextAccountNumber = Long.valueOf(bufferedReader.readLine());
			setNextAccountNumber(nextAccountNumber);
			int numberOfCDOfferings = Integer.valueOf(bufferedReader.readLine());
	
			for(int i = 0; i < numberOfCDOfferings; i++) {
				cdofering = Arrays.copyOf(cdofering, cdofering.length + 1);
				cdofering[cdofering.length - 1] = CDOffering.readFromString(bufferedReader.readLine());
				CDOfferingsArray = cdofering;
			}
			/*>---------------------AccountHolders----------------------------<*/
			int numOfAccountHolders = Integer.valueOf(bufferedReader.readLine());
			for (int nah = 0; nah < numOfAccountHolders; nah++) {
				AccountHolder acctH = AccountHolder.readFromString(bufferedReader.readLine());
				accountHolders = Arrays.copyOf(accountHolders, accountHolders.length + 1);
				accountHolders[accountHolders.length - 1] = acctH;
				AccountHoldersArray = accountHolders;
			
				/*>---------------------CheckingAccounts----------------------------<*/
				int numOfChecking = Integer.valueOf(bufferedReader.readLine());
				for (int j = 0; j < numOfChecking; j++) {
					acctH.addCheckingAccount(CheckingAccount.readFromString(bufferedReader.readLine()));
					int numberOfCheckingTransactions = Integer.valueOf(bufferedReader.readLine());
					
					for(int c = 0; c < numberOfCheckingTransactions; c++) {
						transactions.add(bufferedReader.readLine());
					}
				}
				/*>---------------------SavingsAccounts----------------------------<*/
				int numOfSavings = Integer.valueOf(bufferedReader.readLine());
				
				for (int k = 0; k < numOfSavings; k++) {
					acctH.addSavingsAccount(SavingsAccount.readFromString(bufferedReader.readLine()));
					int numberOfSavingsTransactions = Integer.valueOf(bufferedReader.readLine());
					for(int sa = 0; sa < numberOfSavingsTransactions; sa ++) {
						transactions.add(bufferedReader.readLine());
					}
				}
				/*>---------------------CDAccounts----------------------------<*/
				int numOfCD = Integer.valueOf(bufferedReader.readLine());
				for (int m = 0; m < numOfCD; m++) {
					acctH.addCDAccount(CDAccount.readFromString(bufferedReader.readLine()));
					int numberOfCDATransactions = Integer.valueOf(bufferedReader.readLine());
					for(int cd = 0; cd < numberOfCDATransactions; cd++) {
						transactions.add(bufferedReader.readLine());
					}
				}
			
				System.out.println(transactions.size());
				for(String transaction : transactions) {
					Transaction newTrans = Transaction.readFromString(transaction);
					if(newTrans.getSourceAccount() == null) {
						newTrans.getTargetAccount().addTransaction(newTrans);
					}
					else {
						newTrans.getTargetAccount().addTransaction(newTrans);
						newTrans.getSourceAccount().addTransaction(newTrans);
					}
				}
			}
			int numberOfFraudQueueTransactions = Integer.valueOf(bufferedReader.readLine());
			for(int farq = 0; farq < numberOfFraudQueueTransactions; farq++) {
				fraudQueue.addTransaction(Transaction.readFromString(bufferedReader.readLine()));
			}
			return true;
		}
		

			catch(Exception exception) {
				System.out.println(exception);
				
			}
		return false;
	}


	static boolean writeToFile(String fileName) {
		try {
			FileWriter writer = new FileWriter(fileName);
			BufferedWriter bufferedWriter = new BufferedWriter(writer);
			bufferedWriter.write(String.valueOf(nextAccountNumber));
			bufferedWriter.newLine();
			bufferedWriter.write(String.valueOf(CDOfferingsArray.length));
			bufferedWriter.newLine();
			for (int i = 0; i < CDOfferingsArray.length; i++) {
				bufferedWriter.write(CDOfferingsArray[i].writeToString());
				bufferedWriter.newLine();
			}

			bufferedWriter.write(String.valueOf(AccountHoldersArray.length));
			bufferedWriter.newLine();
			for (int j = 0; j < AccountHoldersArray.length; j++) {
				bufferedWriter.write(AccountHoldersArray[j].writeToString());
				bufferedWriter.newLine();
				bufferedWriter.write(String.valueOf(AccountHoldersArray[j].getCheckingAccounts().length));
				bufferedWriter.newLine();
				for (int k = 0; k < AccountHoldersArray[j].getCheckingAccounts().length; k++) {
					bufferedWriter.write(AccountHoldersArray[j].getCheckingAccounts()[k].writeToString());
					bufferedWriter.newLine();
					bufferedWriter.write(String.valueOf(AccountHoldersArray[j].getCheckingAccounts()[k].getTransactions().size()));
					bufferedWriter.newLine();
					int chT = AccountHoldersArray[j].getCheckingAccounts()[k].getTransactions().size();
					for(int ct = 0; ct < chT; ct++) {
						bufferedWriter.write(AccountHoldersArray[j].getCheckingAccounts()[k].getTransactions().get(ct).writeToString());
						bufferedWriter.newLine();
					}
				}
				bufferedWriter.write(String.valueOf(AccountHoldersArray[j].getSavingsAccounts().length));
				bufferedWriter.newLine();
				for (int m = 0; m < AccountHoldersArray[j].getSavingsAccounts().length; m++) {
					bufferedWriter.write(AccountHoldersArray[j].getSavingsAccounts()[m].writeToString());
					bufferedWriter.newLine();
					bufferedWriter.write(String.valueOf(AccountHoldersArray[j].getSavingsAccounts()[m].getTransactions().size()));
					bufferedWriter.newLine();
					int st1 = AccountHoldersArray[j].getSavingsAccounts()[m].getTransactions().size();
					for(int sas = 0 ; sas < st1; sas++) {
						bufferedWriter.write(AccountHoldersArray[j].getSavingsAccounts()[m].getTransactions().get(st1).writeToString());
						bufferedWriter.newLine();
					}
				}
				bufferedWriter.write(String.valueOf(fraudQueue.getTransaction().size()));
				bufferedWriter.newLine();
				
				for(int far = 0; far < fraudQueue.getTransaction().size(); far++) {
					bufferedWriter.write(fraudQueue.getTransaction().get(far).writeToString());
					bufferedWriter.newLine();
				}
			}
			writer.close();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

	}
	/*
	   i. If transaction does not violate any constraints, deposit/withdraw values from the relevant BankAccounts 
	  	  and add the transaction to the relevant BankAccounts
	   ii. If the transaction violates any of the basic constraints (negative amount, exceeds available balance) 
	      the relevant exception should be thrown and the processing should terminate
	   iii. If the transaction violates the $1,000 suspicion limit, it should simply be added to the FraudQueue for future processing
	 */
	public static boolean processTransaction(Transaction transaction) throws NegativeAmountException,ExceedsAvailableBalanceException,ExceedsFraudSuspicionLimitException{
		transaction.process();
		return false;
	}
	
	public static FraudQueue getFraudQueue() {
		return fraudQueue;
	}
	
	public static BankAccount getBankAccount(long accountId) {
		BankAccount accountMatched = null;
		for(AccountHolder ah : getAccountHolders()) {
			for(CDAccount cda : ah.getCDAccounts()) {
				if(cda.getAccountNumber() == accountId) {
					accountMatched = cda;
				}
			}
			for(CheckingAccount cha : ah.getCheckingAccounts()) {
				if(cha.getAccountNumber() == accountId) {
					accountMatched = cha;
				}
			}
			for (SavingsAccount sva : ah.getSavingsAccounts()) {
				if(sva.getAccountNumber() == accountId) {
					accountMatched = sva;
				}
			}
		}
		return accountMatched;
	}

	static AccountHolder[] sortAccountHolders() {
		Arrays.sort(AccountHoldersArray);
		for (AccountHolder a : AccountHoldersArray) {
			System.out.println(a.getCombinedBalance());
		}
		return AccountHoldersArray;
	}

	static void setNextAccountNumber(long accountNumber) {
		nextAccountNumber = accountNumber;

	}

}