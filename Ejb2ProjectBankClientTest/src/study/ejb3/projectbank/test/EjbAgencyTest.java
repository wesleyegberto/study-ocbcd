package study.ejb3.projectbank.test;

import javax.naming.Context;
import javax.naming.InitialContext;

import study.ejb3.projectbank.agency.ejb.AgencyFacade;

public class EjbAgencyTest {
	public static void main(String[] args) {
		try {
			// gets the entry point into the JNDI naming service
			Context ctx = new InitialContext(Util.getJndiJbossProperties());

			AgencyFacade agency = (AgencyFacade) ctx.lookup("java:Ejb3ProjectBank/AgencyFacadeBean!study.ejb3.projectbank.agency.ejb.AgencyFacade");
			agency.initiateSession(299012);
			System.out.println(agency.getAgencyInformation());
			
			System.out.println("Creating account to Michael Jordan");
			agency.createAccount("Michael Jordan", 360069);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
