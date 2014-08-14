package study.ejb3.projectbank.process.deposit;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "DEPOSIT")
@Access(AccessType.FIELD)
public class DepositTransaction implements Serializable {
	private static final long serialVersionUID = 576795362784291899L;

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY) @Column(name = "ID")
	private int id;
	@Column(name = "AGENCY_NUMBER", nullable = false)
	private long agencyNumber;
	@Column(name = "ACCOUNT_NUMBER", nullable = false)
	private long accountNumber;
	@Column(name = "AMOUNT", nullable = false)
	private double amount;
	@Column(name = "NAME", nullable = false)
	private String name;
	@Column(name = "DATE_DEPOSIT", nullable = false)
	private Date dateDeposit;


	public DepositTransaction() {
	}
	
	public DepositTransaction(long agencyNumber, long accountNumber, double amount, String name) {
		this.agencyNumber = agencyNumber;
		this.accountNumber = accountNumber;
		this.amount = amount;
		this.name = name;
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

	public String getName() {
		return name;
	}

	public Date getDateDeposit() {
		return dateDeposit;
	}
	
	
}
