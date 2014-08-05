package study.ejb3.transaction.cmt.stateless;

import javax.annotation.Resource;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

/**
 * When managing transactions declaratively, you delegate the demarcation policy to
 * the Container.
 * By default all methods has the transaction attribute Required.
 * With EJB 3.x we can set the transaction attribute through @TransactionAttribute
 * annotation too.
 * The transaction won't be propagated with asynchronous method invocation.
 * 
 * The transaction attribute defined in the ejb-jar.xml will override the annotation.
 * 
 * In EJB 3.1, we can make a application exception (checked exception) force the container
 * roll back the transaction. To do that we need annotate the declared class which extends
 * Exception (or its checked subclasses) with @ApplicationException(rollback=true). Thus,
 * when we throw that exception the Container will roll back the transaction. If we annotate
 * a system exception (unchecked exception) with @ApplicationException(rollback=false) the
 * Container won't rolls back.
 */
@Stateless
//@TransactionManagement(TransactionManagementType.CONTAINER) default
//all methods will use this transaction attribute
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class EjbStatelessDiceBean {
	
	@Resource
	private SessionContext ctx;
	
	/**
	 * This method uses the transaction attribute defined in
	 * the level-class.
	 */
	public int rollDDDice() {
		int dice = (int) (Math.random() * 12) + 1;
		
		// do some move
		if(dice == 7) { // you lost
			ctx.setRollbackOnly(); // rolls back the transaction
		}
		
		return dice;
	}
	
	/**
	 * This method uses this transaction attribute defined (REQUIRES_NEW).
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public int rollDiceInAcid() {
		return (int) (Math.random() * 6) + 1;
	}
	
	public void aDangerousMethod() throws MyApplicationException {
		// do dangerous things
		throw new MyApplicationException(); // the Container will rolls back
	}
}
