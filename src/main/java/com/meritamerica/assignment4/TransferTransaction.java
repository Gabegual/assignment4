//public class TransferTransaction extends Transaction
//TransferTransaction(BankAccount sourceAccount, BankAccount targetAccount, double amount)

package com.meritamerica.assignment4;

public class TransferTransaction extends Transaction {
	
	TransferTransaction(BankAccount sourceAccount, BankAccount targetAccount, double amount){
		this.targetAccount = targetAccount;
		this.amount = amount;
		this.sourceAccount = sourceAccount;
		
	}

	@Override
	public void process()
			throws NegativeAmountException, ExceedsAvailableBalanceException, ExceedsFraudSuspicionLimitException {
			
		if(amount < 0) {
			
			throw new NegativeAmountException("ERROR! AMOUNT IS NEGATIVE CAN NOT TRANSFER !");
		}
		else if (sourceAccount.getBalance() < amount) {
			
			throw new NegativeAmountException("ERROR ACCOUNT HAS EXCEEDED THE LIMIT");
		}
		else if (amount > 1000) {
			
			throw new ExceedsFraudSuspicionLimitException("ERROR TRANSACTION CAN NOT BE COMPLETED ");
		}
		
		else {
			
			System.out.println("Transaction amount: ");
			
			sourceAccount.withdraw(amount);
			targetAccount.deposit(amount);
		}
		
	}
}
