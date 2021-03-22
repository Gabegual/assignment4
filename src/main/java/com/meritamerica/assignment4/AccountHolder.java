package com.meritamerica.assignment4;

import java.util.Arrays;

public class AccountHolder implements Comparable<AccountHolder>  {
	
	private String firstName;
	private String middleName;
	private String lastName;
	private String ssn;
	CheckingAccount[] checkingArray = new CheckingAccount[0];
	SavingsAccount[] savingsArray = new SavingsAccount[0];
	CDAccount[] cdAccountArray = new CDAccount[0];
	protected static final  double checkingFee = 250000;
	
	
	public AccountHolder(String first, String middle, String last, String ssn) {
		this.firstName = first;
		this.middleName = middle;
		this.lastName = last;
		this.ssn = ssn;
	}
	
	public CheckingAccount addCheckingAccount(double openingBalance)throws ExceedsCombinedBalanceLimitException, ExceedsFraudSuspicionLimitException, NegativeAmountException{
		if(getCheckingBalance() + getSavingsBalance() + openingBalance >= checkingFee) {
			throw new ExceedsCombinedBalanceLimitException("Combined Balance of your checking and savings acounts exceeds $250,000. ");
		}
		CheckingAccount newCheck = new CheckingAccount(openingBalance);
		DepositTransaction transaction = new DepositTransaction(newCheck, openingBalance);
		
		try {
			MeritBank.processTransaction(transaction);
			
		}
		catch(NegativeAmountException exception) {
			throw new NegativeAmountException("CAN NOT DEPOSIT OR WITHDRAW A NEGATIVE AMOUNT");
		}
		catch(ExceedsFraudSuspicionLimitException exception) {
			throw new ExceedsFraudSuspicionLimitException("TRANSACTION EXCEEDS $1000.00 AND MUST BE REVIEWED ");
		}
		catch(Exception exception) {
			exception.printStackTrace();
		}
		
		CheckingAccount[] chHolding = new CheckingAccount[this.checkingArray.length + 1];
		for(int i = 0; i < this.checkingArray.length; i++) {
			chHolding[i] = this.checkingArray[i];
		}
		 chHolding[chHolding.length - 1] = newCheck;
		 this.checkingArray = chHolding;
		 return newCheck;
	}
	
	public CheckingAccount addCheckingAccount(CheckingAccount checkingAccount)throws ExceedsFraudSuspicionLimitException, ExceedsCombinedBalanceLimitException, NegativeAmountException{
		if(getCheckingBalance() + getSavingsBalance() + checkingAccount.getBalance() >= checkingFee) {
			throw new ExceedsCombinedBalanceLimitException("AGGREGATE BALANCE OF YOUR CHECKING AND SAVINGS EXCEEDS $250,000." );
		}
		DepositTransaction transaction = new DepositTransaction(checkingAccount, checkingAccount.getBalance());
		
		try {
			MeritBank.processTransaction(transaction);
		}
		catch(NegativeAmountException exception) {
			throw new NegativeAmountException("CAN NOT DEPOSIT OR WITHDRAW A NEGATIVE AMOUNT");
		}
		catch(ExceedsFraudSuspicionLimitException exception) {
			throw new ExceedsFraudSuspicionLimitException("TRANSACTION EXCEEDS $1000.00 AND MUST BE REVIEWED");
		}
		catch(Exception exception) {
		}
		
		CheckingAccount[] chHolding = new CheckingAccount[this.checkingArray.length + 1];
		for(int i = 0; i < this.checkingArray.length; i++) {
			chHolding[i] = this.checkingArray[i];
		}
		chHolding[chHolding.length - 1] = checkingAccount;
		this.checkingArray = chHolding;
		return checkingAccount;
				
	}


	public double getCheckingBalance() {
		double total = 0.0;
		int i;
			for(CheckingAccount balance: checkingArray) {
				total += balance.getBalance();
			}
		return total;
	}
	
	public SavingsAccount addSavingsAccount(double openingBalance) throws ExceedsFraudSuspicionLimitException, ExceedsCombinedBalanceLimitException, NegativeAmountException{
		if(getCheckingBalance() + getSavingsBalance() + openingBalance >= checkingFee) {
			throw new ExceedsCombinedBalanceLimitException("AGGREGATE BALANCE OF YOUR CHECKING AND SAVINGS ACCOUNTS EXCEED $250,000");
			
		}
		
		SavingsAccount newSav = new SavingsAccount(openingBalance);
		DepositTransaction transaction = new DepositTransaction(newSav, openingBalance);
		
		try {
			MeritBank.processTransaction(transaction);
			
		}
		catch(NegativeAmountException exception) {
			throw new NegativeAmountException("CAN NOT DEPOSIT OR WITHDRAW A NEGATIVE AMOUNT ");
		}
		catch(ExceedsFraudSuspicionLimitException exception) {
			throw new ExceedsFraudSuspicionLimitException("TRANSACTION EXCEEDS $1000,00 AND MUST BE REVIEWED");
		}
		catch(Exception exception) {
			exception.printStackTrace();
		}
		
		SavingsAccount[] saHolding = new SavingsAccount[savingsArray.length + 1];
		for(int i = 0; i < savingsArray.length; i++) {
			saHolding[i] = savingsArray[i];
		}
		saHolding[saHolding.length - 1] = newSav;
		savingsArray = saHolding;
		return newSav;
	}
	
	public SavingsAccount addSavingsAccount(SavingsAccount savingsAccount) throws ExceedsFraudSuspicionLimitException, ExceedsCombinedBalanceLimitException, NegativeAmountException{
		if(getCheckingBalance() + getSavingsBalance() + savingsAccount.getBalance() >= checkingFee) {
			throw new ExceedsCombinedBalanceLimitException(" AGGREGATE BALANCE OF YOUR CHECKING AND SAVINGS ACCOUNTS EXCEEDS $250,000.");
			
		}
		
		DepositTransaction transaction = new DepositTransaction(savingsAccount, savingsAccount.getBalance());
		try {
			
			MeritBank.processTransaction(transaction);
		}
		catch(NegativeAmountException exception) {
			throw new NegativeAmountException("CAN NOT DEPOSIT OR WITHDRAW A NEGATIVE AMOUNT");
		}
		catch(ExceedsFraudSuspicionLimitException exception) {
			throw new ExceedsFraudSuspicionLimitException("TRANSACTION EXCEEDS $100.00 AND MUST BE REVIEWES");
		}
		catch(Exception exception) {
			
		}
		
		SavingsAccount[] saHolding = new SavingsAccount[this.savingsArray.length + 1];
		for(int i = 0; i < this.savingsArray.length; i++) {
			saHolding[i] = this.savingsArray[i];
		}
		saHolding[saHolding.length -1] = savingsAccount;
		this.savingsArray = saHolding;
		return savingsAccount;
	}
	

	public SavingsAccount[] getSavingsAccounts() {
		return savingsArray;
	}

	public int getNumberOfSavingsAccounts() {
		return savingsArray.length;
	}

	public double getSavingsBalance() {
		double total = 0.0;
			for(SavingsAccount balance : savingsArray) {
				total += balance.getBalance();
			}
		return total;

	}
	
	public CDAccount addCDAccount(CDOffering offering, double openingBalance) throws ExceedsFraudSuspicionLimitException, NegativeAmountException{
		if(offering == null) {
			return null;
		}
		CDAccount newCDA = new CDAccount(offering, openingBalance);
		DepositTransaction transaction = new DepositTransaction(newCDA, openingBalance);
		try {
			MeritBank.processTransaction(transaction);
		}
		catch(NegativeAmountException exception) {
			throw new NegativeAmountException("CAN NOT DEPOSIT OR WITHDRAW A NEGATIVE AMOUNT");
		}
		catch(ExceedsFraudSuspicionLimitException exception) {
			throw new ExceedsFraudSuspicionLimitException("TRANSACTION EXCEEDS $1000.00 AND NEEDS TO BE REVIEWED");
			
		}
		catch(Exception exception) {
			
		}
		
		CDAccount[] cdaHolding = Arrays.copyOf(cdAccountArray, cdAccountArray.length + 1);
		for(int i = 0; i < cdAccountArray.length; i++) {
			cdaHolding[i] = cdAccountArray[i];
		}
		cdaHolding[cdaHolding.length - 1] = newCDA;
		cdAccountArray = cdaHolding;
		return newCDA;
	}
	
	public CDAccount addCDAccount(CDAccount cdAccount) throws ExceedsFraudSuspicionLimitException, NegativeAmountException{
		DepositTransaction transaction = new DepositTransaction(cdAccount, cdAccount.getBalance());
		
		try {
			MeritBank.processTransaction(transaction);
		}
		catch(NegativeAmountException exception) {
			throw new NegativeAmountException("CAN NOT DEPOSIT OR WITHDRAW A NEGATIVE AMOUNT  ");
		}
		catch(ExceedsFraudSuspicionLimitException exception ) {
			throw new ExceedsFraudSuspicionLimitException("TRANSACTION EXCEEDS $1000.00 AND MUST BE REVIEWED ");
		}
		catch(Exception exception) {
			
		}
		CDAccount[] cdaHolding = Arrays.copyOf(this.cdAccountArray, this.cdAccountArray.length + 1);
		for(int i = 0; i < this.cdAccountArray.length; i++) {
			cdaHolding[i] = this.cdAccountArray[i];
		}
		cdaHolding[cdaHolding.length - 1] = cdAccount;
		this.cdAccountArray = cdaHolding;
		return cdAccount;
	}
	

	public CDAccount[] getCDAccounts() {
		return cdAccountArray;
	}

	public int getNumberOfCDAccounts() {
		return cdAccountArray.length;
	}

	public double getCDBalance() {
		double total = 0.0;
			for(CDAccount balance : cdAccountArray) {
				total += balance.getBalance();
			}
		return total;
	}

	public double getCombinedBalance() {
		return getCDBalance() + getSavingsBalance() + getCheckingBalance();
	}
	
	@Override
	public int compareTo(AccountHolder account) {
		if(this.getCombinedBalance() > account.getCombinedBalance()) {
			return 1;
		} else {
			return -1;
		}
	}
	
	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String first) {
		this.firstName = first;
	}
	
	public String getMiddleName() {
		return middleName;
	}
	
	public void setMiddleName(String middle) {
		this.middleName = middle;
	}
	
	public String getLastName() {
		return lastName;
	}
	
	public void setLastName(String last) {
		this.lastName = last;
	}
	
	public String getSSN() {
		return ssn;
	}
	
	public void setSSN(String ssn) {
		this.ssn = ssn;
	}
	
	public CheckingAccount[] getCheckingAccounts() {
		return checkingArray;
	}
	
	public int getNumberOfCheckingAccounts() {
		return checkingArray.length;
	}
	
	public String writeToString() {
    	StringBuilder accountHolderData = new StringBuilder();
    	accountHolderData.append(firstName).append(",");
    	accountHolderData.append(middleName).append(",");
    	accountHolderData.append(lastName).append(",");
    	accountHolderData.append(ssn);
    	return accountHolderData.toString();
    }

	public static AccountHolder readFromString(String accountHolderData) {
	    String[] holding = accountHolderData.split(",");
	    String firstName = holding[0];
	    String middleName = holding[1];
	    String lastName = holding[2];
	    String ssn = holding[3];	
	    return new AccountHolder(firstName, middleName, lastName, ssn);
	}

	public String toString() {
		return  "Combined Balance for Account Holder" + this.getCombinedBalance();	
	}
		
}