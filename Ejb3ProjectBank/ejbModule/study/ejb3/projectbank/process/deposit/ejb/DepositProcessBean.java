package study.ejb3.projectbank.process.deposit.ejb;

import java.math.BigDecimal;

import javax.annotation.PreDestroy;
import javax.annotation.Resource;
import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.ejb.MessageDrivenContext;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import study.ejb3.projectbank.account.Account;
import study.ejb3.projectbank.process.deposit.DepositTransaction;

@MessageDriven(
	mappedName = "",
	activationConfig = {
		@ActivationConfigProperty(propertyName = "destinationType ", propertyValue = "javax.jms.Queue"),
		@ActivationConfigProperty(propertyName = "destination", propertyValue = "java:/queue/queueDev"),
		@ActivationConfigProperty(propertyName = "acknowledgeMode", propertyValue = "Auto-acknowledge")
	}
)
public class DepositProcessBean implements MessageListener {
	@Resource
	private MessageDrivenContext ctx;
	
	@PersistenceContext(unitName = "BANK_DB_UNIT")
	private EntityManager em;

	@PreDestroy
	public void releaseResources() {
		this.em = null;
		this.ctx = null;
	}

	/**
	 * This MDB gets the DepositTransaction object from the Message and process
	 * it by follow: - Check if the account still existing in the DB - Does the
	 * deposit of amount in the account - Update the balance of account in the
	 * DB - Inserts the log of deposit in the DB
	 * 
	 * @see javax.jms.MessageListener#onMessage(javax.jms.Message)
	 */
	public void onMessage(Message message) {
		System.out.println("\n\n==================");
		System.out.println("Starting a deposit");

		DepositTransaction depositTrans = null;
		try {
			ObjectMessage objMessage = (ObjectMessage) message;
			depositTrans = (DepositTransaction) objMessage.getObject();
		} catch(ClassCastException ex) {
			System.err.println("Invalid format of message");
			ctx.setRollbackOnly();
			return;
		} catch(JMSException ex) {
			System.err.println("Erro at processing of deposit");
			ctx.setRollbackOnly();
			return;
		}

		// check if not found in the DB return (maybe was deleted)
		System.out.println("Checking if the account exist");
		Query query = em.createNamedQuery(Account.FIND_ACCOUNT);
		query.setParameter(1, depositTrans.getAgencyNumber());
		query.setParameter(2, depositTrans.getAccountNumber());
		Account account = (Account) query.getSingleResult();
		if(account == null) {
			System.out.println("Account does not exist");
			ctx.setRollbackOnly();
			return;
		}

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
		em.persist(account);

		// register log of deposit
		System.out.println("Inserting the log");
		em.persist(depositTrans);
		
		System.out.println("Deposit ended");
		System.out.println("==================\n");
	}
}
