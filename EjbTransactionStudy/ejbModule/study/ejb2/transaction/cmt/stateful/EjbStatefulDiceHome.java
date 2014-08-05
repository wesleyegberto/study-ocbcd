package study.ejb2.transaction.cmt.stateful;

import javax.ejb.CreateException;
import javax.ejb.EJBLocalHome;

public interface EjbStatefulDiceHome extends EJBLocalHome {
	public EjbStatefulDice create() throws CreateException;
}
