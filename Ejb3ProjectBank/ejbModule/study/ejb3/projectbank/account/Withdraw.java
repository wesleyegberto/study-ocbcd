package study.ejb3.projectbank.account;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "WITHDRAW")
public class Withdraw {

	@Column(name = "AGENCY_NUMBER", nullable = false)
	private long agencyNumber;
	@Column(name = "ACCOUNT_NUMBER", nullable = false)
	private long accountNumber;
	@Column(name = "AMOUNT", nullable = false)
	private double amount;
	@Column(name = "DATE_DEPOSIT", nullable = false)
	private Date dateDeposit;

	public Withdraw(long agencyNumber, long accountNumber, double amount) {
		this.agencyNumber = agencyNumber;
		this.accountNumber = accountNumber;
		this.amount = amount;
		this.dateDeposit = new Date();
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

	public Date getDateDeposit() {
		return dateDeposit;
	}

}
