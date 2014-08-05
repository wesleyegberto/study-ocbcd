package study.ejb2.transaction.bmt.stateless;

import javax.ejb.CreateException;
import javax.ejb.EJBLocalHome;

public interface EjbStatelessDiceHome extends EJBLocalHome {
	public EjbStatelessDice create() throws CreateException;
}
