//Public class FraudQueue
//FraudQueue()
//public void addTransaction(Transaction transaction)
//public Transaction getTransaction()

package com.meritamerica.assignment4;

import java.util.ArrayList;
import java.util.List;

public class FraudQueue {
	private ArrayList<Transaction> fraudTrans = new ArrayList<Transaction>();
	protected FraudQueue(){
		
		
	}
	
	public void addTransaction(Transaction transaction) {
		fraudTrans.add(transaction);
	}
	
	List<Transaction> getTransaction(){
		return fraudTrans;
	}

}
