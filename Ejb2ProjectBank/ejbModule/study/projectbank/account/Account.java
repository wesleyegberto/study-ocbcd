package study.projectbank.account;

import java.io.Serializable;

public class Account implements Serializable {
	private static final long serialVersionUID = -7353210995795428699L;
	
	private long agencyNumber;
	private long accountNumber;
	private String name;
	private double balance;

	public Account() {
	}

	public Account(long agencyNumber, String name, double balance) {
		this.agencyNumber = agencyNumber;
		this.name = name;
		this.balance = balance;
	}

	public Account(long agency, long accountNumber) {
		this.agencyNumber = agency;
		this.accountNumber = accountNumber;
	}

	public long getAgencyNumber() {
		return agencyNumber;
	}

	public void setAgencyNumber(long agencyNumber) {
		this.agencyNumber = agencyNumber;
	}

	public long getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(long accountNumber) {
		this.accountNumber = accountNumber;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}

}
