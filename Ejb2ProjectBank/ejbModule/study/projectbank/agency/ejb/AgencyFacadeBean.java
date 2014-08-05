package study.projectbank.agency.ejb;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.ejb.CreateException;
import javax.ejb.EJBException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import study.projectbank.account.Account;
import study.projectbank.agency.Agency;
import study.projectbank.agency.AgencyException;
import study.projectbank.util.SQLUtils;

public class AgencyFacadeBean implements SessionBean {
	private static final long serialVersionUID = 576795362784291888L;
	private SessionContext ctx;
	
	private Context homeCtx;
	private DataSource dataSource;
	
	private Agency agency;
	
	private static final String AGENCY_QUERY = "SELECT AGENCY_NUMBER, ADDRESS FROM AGENCY WHERE AGENCY_NUMBER=?";
	private static final String ACCOUNT_INSERT = "INSERT INTO ACCOUNT (AGENCY_NUMBER,ACCOUNT_NUMBER,NAME,BALANCE) VALUES (?,?,?,?)";
	

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

	private int getNewAccountNumber() {
		return (int) System.currentTimeMillis();
	}
	
	public void setSessionContext(SessionContext ctx) throws EJBException {
		this.ctx = ctx;
		try {
			Context initialCtx = new InitialContext();
			homeCtx = (Context) initialCtx.lookup("java:comp/env");
		} catch(NamingException e) {
			e.printStackTrace();
			throw new EJBException(e);
		}
	}
	
	public void ejbCreate(long agencyNumber) throws CreateException {
		prepareResources();
		
		// search the account in the DB
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rst = null;
		try {
			conn = dataSource.getConnection();
			stmt = conn.prepareStatement(AGENCY_QUERY);
			stmt.setLong(1, agencyNumber);
			rst = stmt.executeQuery();
			if(rst != null && rst.next()) {
				agency = new Agency(rst.getLong("AGENCY_NUMBER"), rst.getString("ADDRESS"));
			} else {
				throw new CreateException("Agency not found, check the agency number.");
			}
		} catch(SQLException e) {
			e.printStackTrace();
			throw new CreateException("Error at searching the agency");
		} finally {
			SQLUtils.close(rst);
			SQLUtils.close(stmt);
			SQLUtils.close(conn);
		}
	}
	
	public void ejbActivate() throws EJBException {
		prepareResources();
	}

	public void ejbPassivate() throws EJBException {
		releaseResources();
	}

	public void ejbRemove() throws EJBException {
		releaseResources();
	}
	

	public void createAccount(String name, double balance) throws AgencyException {
		if(name == null || name.length() < 5) {
			throw new AgencyException("Invalid name.");
		} else if(balance < 0) {
			throw new AgencyException("Invalid balance, must be greater or equals 0.");
		}
		
		Account account = new Account(agency.getAgencyNumber(), name, balance);
		account.setAccountNumber(getNewAccountNumber());
		
		// creates the account in DB
		Connection conn = null;
		PreparedStatement stmt = null;
		try {
			conn = dataSource.getConnection();
			stmt = conn.prepareStatement(ACCOUNT_INSERT);
			stmt.setLong(1, account.getAgencyNumber());
			stmt.setLong(2, account.getAccountNumber());
			stmt.setString(3, account.getName());
			stmt.setDouble(4, account.getBalance());
			stmt.executeUpdate();
		} catch(SQLException e) {
			e.printStackTrace();
			ctx.setRollbackOnly();
			throw new AgencyException("Error at creation of a new account");
		} finally {
			SQLUtils.close(stmt);
			SQLUtils.close(conn);
		}
	}
	
	public String getAgencyInformation() {
		return agency.getAgencyNumber() + " - " + agency.getAddress();
	}
	

}
