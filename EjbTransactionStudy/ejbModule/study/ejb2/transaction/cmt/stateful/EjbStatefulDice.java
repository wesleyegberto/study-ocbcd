package study.ejb2.transaction.cmt.stateful;

import javax.ejb.EJBLocalObject;

public interface EjbStatefulDice extends EJBLocalObject {
	public int rollDiceInAcid();
}
