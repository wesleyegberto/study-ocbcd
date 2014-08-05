package study.ejb2.transaction.bmt.stateful;

import javax.ejb.CreateException;
import javax.ejb.EJBLocalHome;

public interface EjbStatefulOrderProcessHome extends EJBLocalHome {
	public EjbStatefulOrderProcess create(String orderID) throws CreateException;
}
