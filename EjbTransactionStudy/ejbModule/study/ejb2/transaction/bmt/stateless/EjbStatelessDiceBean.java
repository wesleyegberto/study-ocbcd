package study.ejb2.transaction.bmt.stateless;

import javax.ejb.SessionBean;
import javax.ejb.SessionContext;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;

/**
 * Only Session Beans and Messsage-Driven Beans can be BMT. When using BMT we
 * need save our context to ask for UserTransaction from JTA (Java Transaction
 * Api).
 * 
 * With BMT demarcation, the application requests the transaction, and the EJB
 * container creates the physical transaction and takes care of a few low-level
 * details. Also, it does not propagate transactions from one BMT to another.
 * 
 * 
 * Stateless and Message-Driven bean must complete a transaction before the end
 * of the method. Only Stateful can start a transaction in one method and finish
 * in another.
 * 
 * > Transaction propagation When either a BMT/CMT bean calls a method in
 * another bean with CMT transaction, the Container will reuse the previous
 * transaction. But when either a BMT/CMT bean calls a method in another bean
 * with BMT transaction, the Container will suspend the previous and will start
 * another one.
 * 
 * The methods from UserTransaction only the BMT beans can call, and
 * getRollbackOnly() and setRollbackOnly() from EJBContext (and its
 * subinterfaces) only CMT beans can call.
 */
public class EjbStatelessDiceBean implements SessionBean {
	private static final long serialVersionUID = -625509719560931198L;
	private SessionContext ctx;

	public void setSessionContext(SessionContext ctx) {
		System.out.println("setSessionContext");
		this.ctx = ctx;
	}

	public void ejbCreate() {
		System.out.println("ejbCreate");
	}

	public void ejbActivate() {
	}

	public void ejbPassivate() {
	}

	public void ejbRemove() {
		System.out.println("ejbRemove");
	}

	/**
	 * This method runs in a transactional state. If this beans will be deployed
	 * as a Stateless Bean so it MUST start and finish the transaction in the
	 * same method (only Entity Bean can start in one method and finish in
	 * another).
	 */
	public int rollDiceInAcid() {
		// gets an UserTransaction
		UserTransaction ut = ctx.getUserTransaction();
		try {
			// start the transaction
			ut.begin();

			// do the things which need ACID
			System.out.println("Doing somethin in ACID");

			// commits the transaction
			ut.commit();
		} catch (Exception e) {
			System.out.println("Erro in transaction!");
			try {
				// if anything goes wrong we call rollback
				ut.rollback();
			} catch (SystemException e1) {
				System.out.println("Erro in rollback!");
				e1.printStackTrace();
			}
			e.printStackTrace();
		}

		return (int) (Math.random() * 6) + 1;
	}
}
