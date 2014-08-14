package study.ejb3.projectbank.account;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "WITHDRAW")
public class Withdraw {
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY) @Column(name = "ID")
	private int id;
	@Column(name = "AGENCY_NUMBER", nullable = false)
	private long agencyNumber;
	@Column(name = "ACCOUNT_NUMBER", nullable = false)
	private long accountNumber;
	@Column(name = "AMOUNT", nullable = false)
	private double amount;
	@Column(name = "DATE_DEPOSIT", nullable = false)
	private Date dateDeposit;


	public Withdraw() {
	}
	
	public Withdraw(long agencyNumber, long accountNumber, double amount) {
		this.agencyNumber = agencyNumber;
		this.accountNumber = accountNumber;
		this.amount = amount;
		this.dateDeposit = new Date();
	}
	
	public int getId() {
		return id;
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
