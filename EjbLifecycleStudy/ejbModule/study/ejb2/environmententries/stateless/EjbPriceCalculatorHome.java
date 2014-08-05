package study.ejb2.environmententries.stateless;

import javax.ejb.CreateException;
import javax.ejb.EJBLocalHome;

public interface EjbPriceCalculatorHome extends EJBLocalHome {
	public EjbPriceCalculatorComponent create() throws CreateException;
}
