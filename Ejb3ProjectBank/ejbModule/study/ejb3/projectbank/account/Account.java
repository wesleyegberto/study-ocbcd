package study.ejb3.projectbank.account;

import java.io.Serializable;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "ACCOUNT")
@Access(AccessType.FIELD)
// resultClass to fix the "Pure native scalar queries are not yet supported" error
@NamedQueries({
	@NamedQuery(name = Account.GET_ALL, query = "select ac from Account ac"),
	@NamedQuery(name = Account.FIND_ACCOUNT, query = "select ac from Account ac where ac.agencyNumber = ?1 and ac.accountNumber = ?2")
})
public class Account implements Serializable {
	private static final long serialVersionUID = -8848403439586665662L;
	
	@Id
	@Column(name = "AGENCY_NUMBER", nullable = false)
	private long agencyNumber;
	@Id
	@Column(name = "ACCOUNT_NUMBER", nullable = false)
	private long accountNumber;
	@Column(name = "NAME", nullable = false)
	private String name;
	@Column(name = "BALANCE", nullable = false)
	private double balance;
	
	public static final String GET_ALL = "Account.getAll";
	public static final String FIND_ACCOUNT = "Account.findAccount";

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
