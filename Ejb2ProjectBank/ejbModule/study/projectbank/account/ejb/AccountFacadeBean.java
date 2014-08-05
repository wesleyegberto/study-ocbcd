package study.projectbank.account.ejb;

import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.ejb.CreateException;
import javax.ejb.EJBException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;
import javax.ejb.SessionSynchronization;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import study.projectbank.account.Account;
import study.projectbank.account.AccountException;
import study.projectbank.process.deposit.DepositTransaction;
import study.projectbank.util.MessageSender;
import study.projectbank.util.QueueMessageSender;
import study.projectbank.util.SQLUtils;

public class AccountFacadeBean implements SessionBean, SessionSynchronization {
	private static final long serialVersionUID = 576795362784291888L;
	private static final String ACCOUNT_QUERY = "SELECT AGENCY_NUMBER,ACCOUNT_NUMBER,NAME,BALANCE FROM ACCOUNT WHERE AGENCY_NUMBER=? AND ACCOUNT_NUMBER=?";
	private static final String ACCOUNT_REFRESH_QUERY = "SELECT NAME,BALANCE FROM ACCOUNT WHERE AGENCY_NUMBER=? AND ACCOUNT_NUMBER=?";
	private static final String ACCOUNT_UPDATE = "UPDATE ACCOUNT SET NAME=?,BALANCE=? WHERE AGENCY_NUMBER=? AND ACCOUNT_NUMBER=?";
	private static final String ACCOUNT_UPDATE_BALANCE = "UPDATE ACCOUNT SET BALANCE=? WHERE AGENCY_NUMBER=? AND ACCOUNT_NUMBER=?";
	private static final String ACCOUNT_WITHDRAW_INSERT_LOG = "INSERT INTO WITHDRAW (AGENCY_NUMBER,ACCOUNT_NUMBER,AMOUNT,DATE_WITHDRAW) VALUES (?,?,?,?)";
	
	private SessionContext ctx;

	private Context homeCtx;
	private static final String JNDI_QC_FACTORY = "java:comp/env/jms/QueueConnectionFactory";
	private static final String JNDI_QUEUE_DEPOSIT_PROCESS = "java:comp/env/jms/depositProccess";
	private DataSource dataSource;
	
	// we could use Entity Bean but is obsolete, so we can make this bean a
	// session facade
	private Account account;

	public void setSessionContext(SessionContext ctx) throws EJBException, RemoteException {
		this.ctx = ctx;
		try {
			Context initialCtx = new InitialContext();
			homeCtx = (Context) initialCtx.lookup("java:comp/env");
		} catch(NamingException e) {
			e.printStackTrace();
			throw new EJBException(e);
		}
	}

	public void prepareResources() {
		try {
			this.dataSource = (DataSource) homeCtx.lookup("jdbc/BankDB");
		} catch(NamingException e) {
			e.printStackTrace();
			throw new EJBException(e);
		}
	}

	public void releaseResources() {
		this.dataSource = null;
		this.homeCtx = null;
		this.ctx = null;
	}

	public void ejbCreate(long agencyNumber, long accountNumber) throws CreateException {
		if(agencyNumber <= 0) {
			throw new CreateException("Invalid agency.");
		} else if(accountNumber <= 0) {
			throw new CreateException("Invalid account number.");
		}

		prepareResources();

		// search the account in the DB
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rst = null;
		try {
			conn = dataSource.getConnection();
			stmt = conn.prepareStatement(ACCOUNT_QUERY);
			stmt.setLong(1, agencyNumber);
			stmt.setLong(2, accountNumber);
			rst = stmt.executeQuery();
			if(rst != null && rst.next()) {
				this.account = new Account();
				this.account.setAgencyNumber(rst.getLong("AGENCY_NUMBER"));
				this.account.setAccountNumber(rst.getLong("ACCOUNT_NUMBER"));
				this.account.setName(rst.getString("NAME"));
				this.account.setBalance(rst.getDouble("BALANCE"));
			} else {
				throw new CreateException("Account not found, check the agency and account number.");
			}
		} catch(SQLException e) {
			e.printStackTrace();
			throw new CreateException("Error at creation of new account");
		} finally {
			SQLUtils.close(rst);
			SQLUtils.close(stmt);
			SQLUtils.close(conn);
		}

	}

	public void ejbActivate() throws EJBException, RemoteException {
		prepareResources();
	}

	public void ejbPassivate() throws EJBException, RemoteException {
		releaseResources();
	}

	public void ejbRemove() throws EJBException, RemoteException {
		releaseResources();
	}

	public void afterBegin() throws EJBException, RemoteException {
		// search the account in the DB
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rst = null;
		try {
			conn = dataSource.getConnection();
			stmt = conn.prepareStatement(ACCOUNT_REFRESH_QUERY);
			stmt.setLong(1, account.getAgencyNumber());
			stmt.setLong(2, account.getAccountNumber());
			rst = stmt.executeQuery();
			if(rst != null && rst.next()) {
				this.account.setName(rst.getString("NAME"));
				this.account.setBalance(rst.getDouble("BALANCE"));
			} else {
				throw new EJBException("Account not found!");
			}
		} catch(SQLException e) {
			e.printStackTrace();
			throw new EJBException("Error at refresh data", e);
		} finally {
			SQLUtils.close(rst);
			SQLUtils.close(stmt);
			SQLUtils.close(conn);
		}
	}

	public void beforeCompletion() throws EJBException, RemoteException {
		// update the account in the DB
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rst = null;
		try {
			conn = dataSource.getConnection();
			stmt = conn.prepareStatement(ACCOUNT_UPDATE);
			stmt.setString(1, account.getName());
			stmt.setDouble(2, account.getBalance());
			stmt.setLong(3, account.getAgencyNumber());
			stmt.setLong(4, account.getAccountNumber());
			if(stmt.executeUpdate() != 1) {
				ctx.setRollbackOnly();
			}
		} catch(SQLException e) {
			e.printStackTrace();
			throw new EJBException("Error at reload of the balance");
		} finally {
			SQLUtils.close(rst);
			SQLUtils.close(stmt);
			SQLUtils.close(conn);
		}
	}

	public void afterCompletion(boolean committed) throws EJBException, RemoteException {
	}

	public double getBalance() {
		return account.getBalance();
	}

	public boolean withdraw(double amount) throws AccountException {
		if(amount <= 0) {
			throw new AccountException("Invalid amount");
		} else if(account.getBalance() - amount < 0) {
			throw new AccountException("Insufficiency balance");
		}
		
		System.out.println("\n\n==================");
		System.out.println("Starting a withdraw of " + amount);
		
		// does the withdraw
		BigDecimal bdBalance = new BigDecimal(String.valueOf(account.getBalance()));
		bdBalance.setScale(2, BigDecimal.ROUND_HALF_EVEN);
		BigDecimal amountWithdraw = new BigDecimal(String.valueOf(amount));
		amountWithdraw.setScale(2, BigDecimal.ROUND_HALF_EVEN);
		BigDecimal newBalance = bdBalance.subtract(amountWithdraw);
		newBalance.setScale(2, BigDecimal.ROUND_HALF_EVEN);
		
		account.setBalance(newBalance.doubleValue());
		
		// search the account in the DB and refresh the data
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rst = null;
		try {
			conn = dataSource.getConnection();
			// update the balance
			System.out.println("Updating the balance");
			stmt = conn.prepareStatement(ACCOUNT_UPDATE_BALANCE);
			stmt.setDouble(1, account.getBalance());
			stmt.setLong(2,account.getAgencyNumber());
			stmt.setLong(3, account.getAccountNumber());
			if(stmt.executeUpdate() != 1) { // must update only one account
				ctx.setRollbackOnly();
				return false;
			}
			SQLUtils.close(stmt);
			
			// register log of withdraw
			System.out.println("Inserting the log");
			stmt = conn.prepareStatement(ACCOUNT_WITHDRAW_INSERT_LOG);
			stmt.setLong(1, account.getAgencyNumber());
			stmt.setLong(2, account.getAccountNumber());
			stmt.setDouble(3, amount);
			stmt.setDate(4, new Date(System.currentTimeMillis()));
			if(stmt.executeUpdate() != 1) {
				ctx.setRollbackOnly();
				return false;
			}
			
			return true;
		} catch(SQLException e) {
			e.printStackTrace();
			throw new EJBException("Error at reload of the balance");
		} finally {
			System.out.println("Withdraw ended");
			System.out.println("==================\n");
			
			SQLUtils.close(rst);
			SQLUtils.close(stmt);
			SQLUtils.close(conn);
		}
	}

	public void deposit(double amount, String name) throws AccountException {
		if(amount <= 0) {
			throw new AccountException("Invalid amount");
		}
		if(name == null) {
			name = "";
		}
		
		DepositTransaction deposit = new DepositTransaction(account.getAgencyNumber(), account.getAccountNumber(), amount, name);

		// gets JMS sender to send the deposit to Deposit Proccess' JMS
		MessageSender sender = null;
		try {
			// I'm getting IllegalStateException because J2EE by default use only 1 non-XA Resource
			// (J2EE 1.3 release note section "New Treatment of Non-XA Data Transactions")
			// to correct use need change de datasource to XA in the resource.properties.
			sender = new QueueMessageSender(JNDI_QC_FACTORY, JNDI_QUEUE_DEPOSIT_PROCESS);
			sender.sendObject(deposit);
		} catch(Exception e) {
			e.printStackTrace();
			//ctx.setRollbackOnly();
			throw new AccountException("Error at deposit");
		} finally {
			if(sender != null) {
				sender.close();
			}
		}
		
	}

}
