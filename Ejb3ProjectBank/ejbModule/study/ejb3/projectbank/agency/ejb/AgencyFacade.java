package study.ejb3.projectbank.agency.ejb;

import javax.ejb.Remote;

import study.ejb3.projectbank.agency.AgencyException;

@Remote
public interface AgencyFacade {
	public void initiateSession(long agencyNumber) throws AgencyException;
	
	public void createAccount(String name, double balancy) throws AgencyException;
	
	public String getAgencyInformation();
}
