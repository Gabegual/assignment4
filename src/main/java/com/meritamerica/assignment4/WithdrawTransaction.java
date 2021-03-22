//public class WithdrawTransaction extends Transaction
//WithdrawTransaction(BankAccount targetAccount, double amount)

package com.meritamerica.assignment4;

public class WithdrawTransaction extends Transaction {

	WithdrawTransaction(BankAccount targetAccount, double amount) {
		this.targetAccount = targetAccount;
		this.amount = amount;

	}

	@Override
	public void process()
			throws NegativeAmountException, ExceedsAvailableBalanceException, ExceedsFraudSuspicionLimitException {
		if (amount < 0) {

			throw new NegativeAmountException("ERROR! AMOUNT IS NEGATIVE CAN NOT TRANSFER !");
		} else if (targetAccount.getBalance() < amount) {

			throw new ExceedsAvailableBalanceException("ERROR ACCOUNT HAS EXCEEDED THE LIMIT");
		} else if (amount > 1000) {

			throw new ExceedsFraudSuspicionLimitException("ERROR TRANSACTION CAN NOT BE COMPLETED ");
		}

		else {

			System.out.println("Transaction amount: ");

			targetAccount.withdraw(amount);
		}

	}

}
