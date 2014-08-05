package study.ejb2.transaction.cmt.stateless;

import javax.ejb.EJBLocalObject;

public interface EjbStatelessDice extends EJBLocalObject {
	public int rollDiceInAcid();
}
