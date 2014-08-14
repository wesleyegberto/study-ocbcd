package study.ejb3.projectbank.account.ejb;

import java.math.BigDecimal;

import javax.annotation.PreDestroy;
import javax.annotation.Resource;
import javax.ejb.PrePassivate;
import javax.ejb.Remove;
import javax.ejb.SessionContext;
import javax.ejb.Stateful;
import javax.jms.Queue;
import javax.jms.QueueConnectionFactory;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import study.ejb3.projectbank.account.Account;
import study.ejb3.projectbank.account.AccountException;
import study.ejb3.projectbank.account.Withdraw;
import study.ejb3.projectbank.process.deposit.DepositTransaction;
import study.ejb3.projectbank.util.MessageSender;
import study.ejb3.projectbank.util.QueueMessageSender;

@Stateful
public class AccountFacadeBean implements AccountFacade {
	
	@Resource
	private SessionContext ctx;
	@PersistenceContext(unitName = "BANK_DB_UNIT")
	private EntityManager em;
	
	@Resource(lookup = "java:/ConnectionFactory")
	private QueueConnectionFactory queueFactory;
	@Resource(lookup = "java:/queue/queueDev")
	private Queue queue;
	
	// we could use Entity Bean but is obsolete, so we can make this bean a
	// session facade
	private Account account;

	public void initiateSession(long agencyNumber, long accountNumber) throws AccountException {
		if(agencyNumber <= 0) {
			throw new AccountException("Invalid agency.");
		} else if(accountNumber <= 0) {
			throw new AccountException("Invalid account number.");
		}
		Query query = em.createNamedQuery(Account.FIND_ACCOUNT);
		query.setParameter(1, agencyNumber);
		query.setParameter(2, accountNumber);
		this.account = (Account) query.getSingleResult();
		if(account == null) {
			throw new AccountException("Account not found");
		}
	}

	@PreDestroy @Remove @PrePassivate
	public void remove() {
		this.account = null;
		this.em = null;
		this.queue = null;
		this.queueFactory = null;
		this.ctx  = null;
	}

	public double getBalance() {
		if(account == null) {
			throw new IllegalStateException("Account does not initialized");
		}
		return account.getBalance();
	}

	public boolean withdraw(double amount) throws AccountException {
		if(account == null) {
			throw new IllegalStateException("Account does not initialized");
		} else if(amount <= 0) {
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

		// update the balance
		System.out.println("Updating the balance");
		em.merge(account);
		
		// create and register log of withdraw
		Withdraw wd = new Withdraw(account.getAgencyNumber(), account.getAccountNumber(), amount);
		System.out.println("Inserting the log");
		em.persist(wd);
		System.out.println("Withdraw ended");
		System.out.println("==================\n");
		
		return true;
	}

	public void deposit(double amount, String name) throws AccountException {
		if(account == null) {
			throw new IllegalStateException("Account does not initialized");
		} else if(amount <= 0) {
			throw new AccountException("Invalid amount");
		}
		if(name == null) {
			name = "";
		}
		
		DepositTransaction deposit = new DepositTransaction(account.getAgencyNumber(), account.getAccountNumber(), amount, name);

		// gets JMS sender to send the deposit to Deposit Proccess' JMS
		MessageSender sender = null;
		try {
			sender = new QueueMessageSender(this.queueFactory, this.queue);
			sender.sendObject(deposit);
		} catch(Exception e) {
			e.printStackTrace();
			ctx.setRollbackOnly();
			throw new AccountException("Error at deposit");
		} finally {
			if(sender != null) {
				sender.close();
			}
		}
	}

}
