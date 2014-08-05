package study.projectbank.process.deposit;

import java.io.Serializable;

public class DepositTransaction implements Serializable {
	private static final long serialVersionUID = 576795362784291888L;

	private long agencyNumber;
	private long accountNumber;
	private double amount;
	private String name;

	public DepositTransaction(long agencyNumber, long accountNumber, double amount, String name) {
		this.agencyNumber = agencyNumber;
		this.accountNumber = accountNumber;
		this.amount = amount;
		this.name = name;
	}

	public long getAgencyNumber() {
		return agencyNumber;
	}

	public long getAccountNumber() {
		return accountNumber;
	}

	public double getAmount() {
		return amount;
	}

	public String getName() {
		return name;
	}
}
