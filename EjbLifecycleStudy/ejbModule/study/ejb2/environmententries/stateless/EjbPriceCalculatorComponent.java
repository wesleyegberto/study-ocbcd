package study.ejb2.environmententries.stateless;

import javax.ejb.EJBLocalObject;

public interface EjbPriceCalculatorComponent extends EJBLocalObject {
	public double calculateDiscount(int productId, double price);
	
	public void notifyMeWhenProductArrives(String email);
}
