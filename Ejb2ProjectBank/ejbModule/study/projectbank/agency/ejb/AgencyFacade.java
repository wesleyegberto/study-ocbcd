package study.projectbank.agency.ejb;

import java.rmi.RemoteException;

import javax.ejb.EJBObject;

import study.projectbank.agency.AgencyException;

public interface AgencyFacade extends EJBObject {
	public void createAccount(String name, double balancy) throws AgencyException, RemoteException;
	
	public String getAgencyInformation() throws RemoteException;
}
