package study.projectbank.process.deposit.ejb;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.ejb.EJBException;
import javax.ejb.MessageDrivenBean;
import javax.ejb.MessageDrivenContext;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import study.projectbank.account.Account;
import study.projectbank.process.deposit.DepositTransaction;
import study.projectbank.util.SQLUtils;

public class DepositProcessBean implements MessageDrivenBean, MessageListener {
	private static final long serialVersionUID = 576795362784291888L;
	
	private MessageDrivenContext ctx;
	private Context homeCtx;
	private DataSource dataSource;
	
	private static final String ACCOUNT_QUERY = "SELECT AGENCY_NUMBER,ACCOUNT_NUMBER,BALANCE FROM ACCOUNT WHERE AGENCY_NUMBER=? AND ACCOUNT_NUMBER=?";
	private static final String ACCOUNT_UPDATE_BALANCE = "UPDATE ACCOUNT SET BALANCE=? WHERE AGENCY_NUMBER=? AND ACCOUNT_NUMBER=?";
	private static final String DEPOSIT_INSERT_LOG = "INSERT INTO DEPOSIT (AGENCY_NUMBER,ACCOUNT_NUMBER,AMOUNT,NAME,DATE_DEPOSIT) VALUES (?,?,?,?,?)";
	
	public void setMessageDrivenContext(MessageDrivenContext ctx) throws EJBException {
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
	
	public void ejbCreate() {
		prepareResources();
	}

	public void ejbRemove() throws EJBException {
		releaseResources();
	}

	/**
	 * This MDB gets the DepositTransaction object from the Message
	 * and process it by follow:
	 * - Check if the account still existing in the DB
	 * - Does the deposit of amount in the account
	 * - Update the balance of account in the DB
	 * - Inserts the log of deposit in the DB
	 * 
	 * @see javax.jms.MessageListener#onMessage(javax.jms.Message)
	 */
	public void onMessage(Message message) {
		System.out.println("\n\n==================");
		System.out.println("Starting a deposit");
		
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rst = null;
		
		DepositTransaction depositTrans = null;
		try {
			ObjectMessage objMessage = (ObjectMessage) message;
			depositTrans = (DepositTransaction) objMessage.getObject();
		} catch(ClassCastException ex) {
			System.err.println("Invalid format of message");
			ctx.setRollbackOnly();
		} catch(JMSException e1) {
			System.err.println("Erro at processing of ");
			ctx.setRollbackOnly();
			return;
		}
		
		try {
			// check if not found in the DB return (maybe was deleted)
			System.out.println("Checking if the account exist");
			conn = dataSource.getConnection();
			stmt = conn.prepareStatement(ACCOUNT_QUERY);
			stmt.setLong(1, depositTrans.getAgencyNumber());
			stmt.setLong(2, depositTrans.getAccountNumber());
			rst = stmt.executeQuery();
			if(!rst.next()) { 
				System.out.println("Account does not exist");
				return;
			}
			Account account = new Account();
			account.setAgencyNumber(rst.getLong("AGENCY_NUMBER"));
			account.setAccountNumber(rst.getLong("ACCOUNT_NUMBER"));
			account.setBalance(rst.getDouble("BALANCE"));
			SQLUtils.close(stmt);
			
			// does the deposit
			System.out.println("Depositing " + depositTrans.getAmount());
			BigDecimal bdBalance = new BigDecimal(String.valueOf(account.getBalance()));
			bdBalance.setScale(2, BigDecimal.ROUND_HALF_EVEN);
			BigDecimal amountWithdraw = new BigDecimal(String.valueOf(depositTrans.getAmount()));
			amountWithdraw.setScale(2, BigDecimal.ROUND_HALF_EVEN);
			BigDecimal newBalance = bdBalance.add(amountWithdraw);
			newBalance.setScale(2, BigDecimal.ROUND_HALF_EVEN);
			
			account.setBalance(newBalance.doubleValue());
			
			// update the balance
			System.out.println("Updating the account");
			stmt = conn.prepareStatement(ACCOUNT_UPDATE_BALANCE);
			stmt.setDouble(1, account.getBalance());
			stmt.setLong(2, account.getAgencyNumber());
			stmt.setLong(3, account.getAccountNumber());
			if(stmt.executeUpdate() != 1) { // must update only one account
				ctx.setRollbackOnly();
				return;
			}
			SQLUtils.close(stmt);
			
			// register log of deposit
			System.out.println("Inserting the log");
			stmt = conn.prepareStatement(DEPOSIT_INSERT_LOG);
			stmt.setLong(1, depositTrans.getAgencyNumber());
			stmt.setLong(2, depositTrans.getAccountNumber());
			stmt.setDouble(3, depositTrans.getAmount());
			stmt.setString(4, depositTrans.getName());
			stmt.setDate(5, new Date(System.currentTimeMillis()));
			if(stmt.executeUpdate() != 1) {
				ctx.setRollbackOnly();
				return;
			}
		} catch(SQLException e) {
			System.out.println("Error during the deposit: " + e.getMessage());
			e.printStackTrace();
			ctx.setRollbackOnly();
		} finally {
			SQLUtils.close(rst);
			SQLUtils.close(stmt);
			SQLUtils.close(conn);
		}
		System.out.println("Deposit ended");
		System.out.println("==================\n");
	}
}
